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

    public OrderRequest(Long userId, List<OrderProductRequest> products) {
        this.userId = userId;
        this.products = products;
    }

    public Order toOrder() {
        return Order.builder()
                .userId(userId)
                .orderProducts(products.stream().map(OrderProductRequest::toOrderProduct).toList())
                .build();
    }
}