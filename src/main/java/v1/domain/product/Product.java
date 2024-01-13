package v1.domain.product;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {
    private Long productId;
    private String  name;
    private int price;
    private int quantity;

    @Override
    public String toString() {
        return "Product{" +
            "productId=" + productId +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
    }

    public void deduct(int deductQuantity) {
        this.quantity-=deductQuantity;
    }

    public void add(int addQuantity) {
        this.quantity+=addQuantity;
    }
}
