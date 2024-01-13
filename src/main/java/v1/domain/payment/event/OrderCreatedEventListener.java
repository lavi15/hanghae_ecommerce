package v1.domain.payment.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import v1.commons.advice.KafkaProducer;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventListener {

    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishCreatedOrder(OrderCreatedEvent event) {
        kafkaProducer.publish(
            event.getOrder()
        );
    }
}
