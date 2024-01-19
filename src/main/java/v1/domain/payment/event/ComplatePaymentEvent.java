package v1.domain.payment.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import v1.domain.order.Order;
import v1.domain.payment.Payment;

@Getter
@RequiredArgsConstructor
public class ComplatePaymentEvent {
    private final Payment payment;
}