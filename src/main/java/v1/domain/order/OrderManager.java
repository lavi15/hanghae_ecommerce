package v1.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import v1.entity.order.repository.OrderRepository;

@Component
@RequiredArgsConstructor
public class OrderManager {
    private final OrderRepository orderRepository;

    @Transactional
    public void markPaymentCompleted(Long orderId){
        orderRepository.markPaymentCompleted(orderId);
    }
}
