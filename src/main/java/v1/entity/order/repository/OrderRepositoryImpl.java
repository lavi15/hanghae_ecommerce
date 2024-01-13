package v1.entity.order.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import v1.domain.order.Order;
import v1.entity.order.OrderEntity;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderEntityRepository orderEntityRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductEntityRepository orderProductEntityRepository;

    @Override
    public Order save(Order order) {
        orderProductRepository.saveAll(order.getOrderProducts());
        OrderEntity orderEntity = OrderEntity.fromOrder(order, order.getOrderProducts().stream()
                .map(orderProduct -> orderProductEntityRepository.findByProductId(orderProduct.getProductId())).toList());
        orderEntityRepository.save(orderEntity);
        return orderEntity.toOrder();
    }

    @Override
    public void completePayment(Order order) {
        OrderEntity orderEntity = orderEntityRepository.findById(order.getOrderId()).get();
    }
}
