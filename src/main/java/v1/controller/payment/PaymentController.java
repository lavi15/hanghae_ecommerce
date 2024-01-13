package v1.controller.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;
import v1.commons.advice.ConsumerMapper;
import v1.commons.advice.KafkaTopics;
import v1.domain.order.Order;
import v1.domain.payment.PaymentService;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final ConsumerMapper consumerMapper;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED_TOPIC, groupId = "payment")
    public void executePayment(String orderToString){
        Order order = consumerMapper.deserializeOrderFromJson(orderToString);
        paymentService.executePayment(order);
    }
}
