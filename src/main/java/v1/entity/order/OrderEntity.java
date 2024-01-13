package v1.entity.order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import v1.domain.order.Order;
import v1.entity.BaseEntity;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @OneToMany(mappedBy = "orderEntity")
    private List<OrderProductEntity> orderProductEntities;

    public Order toOrder() {
        return Order.builder()
                .orderId(id)
                .userId(userId)
                .orderProducts(getOrderProductEntities().stream().map(OrderProductEntity::toOrderProduct).toList())
                .build();
    }

    @Builder
    public OrderEntity(Long userId, List<OrderProductEntity> orderProductEntities, OrderState orderState) {
        this.userId = userId;
        this.orderProductEntities = orderProductEntities;
        this.orderState = orderState != null ? orderState : OrderState.INIT;;
    }

    public static OrderEntity fromOrder(Order order, List<OrderProductEntity> orderProductEntities){
        return OrderEntity.builder()
            .userId(order.getUserId())
            .orderProductEntities(orderProductEntities)
            .build();
    }
}