package v1.controller.order;

import lombok.Builder;
import lombok.Getter;
import v1.domain.order.OrderProduct;

@Getter
@Builder
public class OrderProductResponce {
    private Long productId;
    private int quantity;

    public OrderProduct toOrderProduct(){
        return OrderProduct.builder()
                    .productId(productId)
                    .quantity(quantity)
                    .build();
    }
}
