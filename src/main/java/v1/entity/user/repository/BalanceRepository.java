package v1.entity.user.repository;

import java.util.Optional;
import v1.domain.user.Balance;

public interface BalanceRepository {
    Optional<Balance> findByUserId(Long userID);

    void save(Balance balance);

    boolean existsById(Long userId);

    void chargeBalance(Long userId, int balance);

    void executePayment(Long userId, int balance);
}
