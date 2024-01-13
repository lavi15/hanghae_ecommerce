package v1.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import v1.entity.user.repository.BalanceRepository;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final UserBalanceManager userBalanceManager;

    public void chargeBalance(Long userId, int chargeBalance) {
        userBalanceManager.validate(userId);
        userBalanceManager.chargeBalance(userId, chargeBalance);
    }

    public Balance getUserPoint(Long userId) {
        userBalanceManager.validate(userId);
        return userBalanceManager.findByUserId(userId);
    }
}