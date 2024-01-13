package v1.commons.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public <T> void publish(T body) {
        try {
            String content = objectMapper.writeValueAsString(body);
            kafkaTemplate.send(KafkaTopics.ORDER_CREATED_TOPIC, content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessing Failed");
        }
    }
}
