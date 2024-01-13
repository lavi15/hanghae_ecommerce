package v1.controller.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChargeBalanceRequest {
    private int chargePoint;

    @Builder
    public ChargeBalanceRequest(int chargePoint) {
        this.chargePoint = chargePoint;
    }
}
