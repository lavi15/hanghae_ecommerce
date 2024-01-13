package v1.domain.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {
    private Long paymentId;
    private Long orderId;
    private int totalPrice;

    @Builder(toBuilder = true)
    public Payment(Long paymentId, Long orderId, int totalPrice) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }
}
