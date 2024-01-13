package v1.entity.order;

import v1.domain.order.OrderProduct;

public class PopularProduct {
    private Long productId;
    private int salesQuantity;

    public PopularProduct(Long productId, int salesQuantity) {
        this.productId = productId;
        this.salesQuantity = salesQuantity;
    }

    public OrderProduct toOrderProduct() {
        return OrderProduct.builder()
                .productId(productId)
                .quantity(salesQuantity)
                .build();
    }
}

