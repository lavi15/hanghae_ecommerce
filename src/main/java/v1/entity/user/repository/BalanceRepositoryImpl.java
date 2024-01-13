package v1.entity.user.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import v1.domain.user.Balance;
import v1.entity.user.BalanceEntity;

@Repository
@RequiredArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {
    private final BalanceEntityRepository balanceEntityRepository;

    @Override
    public Optional<Balance> findByUserId(Long userId) {
        return balanceEntityRepository.findByUserId(userId).map(BalanceEntity::toBalance);
    }

    @Override
    public void save(Balance balance) {
        balanceEntityRepository.save(BalanceEntity.fromBalance(balance));
    }

    @Override
    public boolean existsById(Long userId) {
        return balanceEntityRepository.existsById(userId);
    }

    @Override
    public void chargeBalance(Long userId, int balance) {
        BalanceEntity balanceEntity = balanceEntityRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("확인할 수 없는 유저입니다."));
        balanceEntity.chargeBalance(balance);
    }

    @Override
    public void executePayment(Long userId, int balance) {
        BalanceEntity balanceEntity = balanceEntityRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("확인할 수 없는 유저입니다."));
        balanceEntity.executePayment(balance);
    }
}