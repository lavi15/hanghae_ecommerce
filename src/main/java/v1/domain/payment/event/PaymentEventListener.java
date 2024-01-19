package v1.domain.payment.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import v1.domain.order.OrderManager;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private final OrderManager orderManager;

    @Async
    @EventListener
    public void markPaymentCompleted(ComplatePaymentEvent event) {
        orderManager.markPaymentCompleted(event.getPayment().getOrderId());
    }
}
