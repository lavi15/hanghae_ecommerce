package v1.domain.order.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import v1.commons.advice.KafkaProducer;
import v1.commons.advice.KafkaTopics;

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
