package v1.commons.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import v1.domain.order.Order;

@Component
@RequiredArgsConstructor
public class ConsumerMapper {
    private final ObjectMapper objectMapper;

    public Order deserializeOrderFromJson(String orderToString) {
        Order order = null;
        try {
            order = objectMapper.readValue(orderToString, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessing Failed");
        }
        return order;
    }
}
