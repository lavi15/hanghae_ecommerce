package v1.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v1.controller.ApiResponse;
import v1.domain.user.BalanceService;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @PutMapping("/balance/charge")
    public ApiResponse<String> chargeBalance(@RequestHeader(name = "Authorization") Long userId, @RequestBody ChargeBalanceRequest chargeBalanceRequest) {
        balanceService.chargeBalance(userId, chargeBalanceRequest.getChargePoint());
        return ApiResponse.ok("충전에 성공했습니다.");
    }

    @GetMapping("/balance")
    public ApiResponse<BalanceResponse> getUserPoint(@RequestHeader(name = "Authorization") Long userId) {
        return ApiResponse.ok(new BalanceResponse(balanceService.getUserPoint(userId)));
    }
}
