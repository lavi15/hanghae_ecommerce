package v1.domain.order.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import v1.domain.order.Order;

@Getter
@RequiredArgsConstructor
public class OrderCreatedEvent {
    private final Order order;
}