package v1.controller.order;

import lombok.Builder;
import lombok.Getter;
import v1.domain.order.OrderProduct;

@Getter
public class OrderProductResponce {
    private Long productId;
    private int quantity;

    public OrderProductResponce(OrderProduct orderProduct) {
        this.productId = orderProduct.getProductId();
        this.quantity = orderProduct.getQuantity();
    }
}
