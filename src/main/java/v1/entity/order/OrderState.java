package v1.entity.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderState {
    INIT("주문생성"),
    CANCELED("주문취소"),
    PAYMENT_COMPLETED("결제완료"),
    PAYMENT_CANCELED("결제취소"),
    RECEIVED("주문접수"),
    COMPLETED("배송완료");

    private final String text;
}
