package v1.domain.order.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import v1.commons.advice.KafkaProducer;
import v1.domain.product.ProductManager;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final ProductManager productManager;
    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishCreatedOrder(OrderCreatedEvent event) {
        kafkaProducer.publish(event.getOrder());
    }

    @Async
    @EventListener
    public void cancelOrder(OrderCancelEvent event) {
        productManager.addProduct(event.getOrder().getOrderProducts());
    }
}
