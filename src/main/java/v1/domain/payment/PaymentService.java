package v1.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import v1.domain.order.Order;
import v1.domain.order.event.OrderCancelEvent;
import v1.domain.payment.event.ComplatePaymentEvent;
import v1.domain.product.ProductManager;
import v1.domain.user.Balance;
import v1.domain.user.UserBalanceManager;
import v1.entity.order.repository.OrderRepository;
import v1.entity.payment.repository.PaymentRepository;
import v1.entity.user.repository.BalanceRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserBalanceManager userBalanceManager;
    private final ProductManager productManager;
    private final PaymentManager paymentManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void executePayment(Order order) {
        int totalprice = order.getOrderProducts().stream()
            .mapToInt(orderProduct -> productManager.getProductPrice(orderProduct.getProductId()))
            .sum();

        Payment payment = Payment.builder()
                            .orderId(order.getOrderId())
                            .totalPrice(totalprice)
                            .build();

        try{
            userBalanceManager.executePayment(payment.getOrderId(), payment.getTotalPrice());
            paymentManager.save(payment);
            applicationEventPublisher.publishEvent(new ComplatePaymentEvent(payment));
        } catch (Exception e) {
            applicationEventPublisher.publishEvent(new OrderCancelEvent(order));
        }
    }
}
