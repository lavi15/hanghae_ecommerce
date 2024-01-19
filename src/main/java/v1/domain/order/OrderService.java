package v1.domain.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import v1.commons.advice.LockHandler;
import v1.commons.advice.RedisKeyFactory;
import v1.commons.advice.TransactionHandler;
import v1.domain.order.event.OrderCreatedEvent;
import v1.domain.product.ProductManager;
import v1.entity.order.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final LockHandler lockHandler;
    private final ProductManager productManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RedisKeyFactory redisKeyFactory;
    private final TransactionHandler transactionHandler;

    public Order createOrder(Order order) {
        order.getOrderProducts().forEach(orderProduct -> productManager.validate(orderProduct.getProductId()));
        productManager.deductProduct(order.getOrderProducts());

        try{
            return lockHandler.runOnLock(
                redisKeyFactory.createUserKey(order.getUserId()),
                2000L,
                1000L,
                () -> transactionHandler.runOnWriteTransaction(() -> {
                    Order complateOrder = orderRepository.save(order);
                    applicationEventPublisher.publishEvent(new OrderCreatedEvent(order));
                    return complateOrder;
                })
            );
        }catch (Exception e) {
            log.error("Failed Create Order");
            productManager.addProduct(order.getOrderProducts());
            throw new RuntimeException("Failed Create Order");
        }
    }
}