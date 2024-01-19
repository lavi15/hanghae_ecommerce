package v1.controller.order;

import java.util.List;
import lombok.Getter;
import v1.domain.order.Order;

@Getter
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private List<OrderProductResponce> products;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUserId();
        this.products = order.getOrderProducts().stream().map(orderProduct -> new OrderProductResponce(orderProduct)).toList();
    }
}
