package v1.controller.product;

import lombok.Builder;
import lombok.Getter;
import v1.domain.product.Product;

@Getter
public class ProductResponse {
    private Long productId;
    private String  name;
    private int price;
    private int quantity;

    public ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }

    @Builder
    public ProductResponse(Long productId, String name, int price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
