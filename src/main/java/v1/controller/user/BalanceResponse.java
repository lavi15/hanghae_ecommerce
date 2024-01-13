package v1.controller.user;

import lombok.Getter;
import v1.domain.user.Balance;

@Getter
public class BalanceResponse {
    private int balance;

    public BalanceResponse(Balance balance) {
        this.balance = balance.getBalance();
    }
}
