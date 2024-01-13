package v1.domain.order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProduct{
    private Long productId;
    private int quantity;

    @Builder
    public OrderProduct(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
