package v1.entity.order;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import v1.domain.order.OrderProduct;

@Getter
@Entity(name = "order_product")
@RequiredArgsConstructor
public class OrderProductEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int quantity;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Builder(toBuilder = true)
    public OrderProductEntity(Long id, Long productId, int quantity, LocalDateTime createdDateTime,
                              OrderEntity orderEntity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.createdDateTime = createdDateTime;
        this.orderEntity = orderEntity;
    }

    public OrderProduct toOrderProduct() {
        return OrderProduct.builder()
                    .productId(getProductId())
                    .quantity(getQuantity())
                    .build();
    }

    public static OrderProductEntity fromOrderProduct(OrderProduct orderProduct){
        return OrderProductEntity.builder()
            .productId(orderProduct.getProductId())
            .quantity(orderProduct.getQuantity())
            .build();
    }
}