package v1.entity.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import v1.domain.payment.Payment;
import v1.domain.product.Product;
import v1.entity.BaseEntity;

@Entity
@RequiredArgsConstructor
@Table(name = "payment")
@Getter
public class PaymentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private int totalPrice;

    @Builder(toBuilder = true)
    public PaymentEntity(Long id, Long orderId, int totalPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public static PaymentEntity fromPayment(Payment payment) {
        return PaymentEntity.builder()
            .orderId(payment.getOrderId())
            .totalPrice(payment.getTotalPrice())
            .build();
    }
}
