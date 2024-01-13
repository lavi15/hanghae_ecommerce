package v1.controller.order;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import v1.domain.order.Order;

@Getter
@Builder
public class OrderRequest {
    private Long userId;
    private List<OrderProductRequest> products;

    public Order toOrder() {
        return Order.builder()
                .userId(userId)
                .orderProducts(products.stream().map(OrderProductRequest::toOrderProduct).toList())
                .build();
    }
}