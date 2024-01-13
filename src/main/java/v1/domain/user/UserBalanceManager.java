package v1.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import v1.commons.advice.LockHandler;
import v1.commons.advice.RedisKeyFactory;
import v1.commons.advice.TransactionHandler;
import v1.domain.order.OrderProduct;
import v1.entity.user.repository.BalanceRepository;

@Component
@RequiredArgsConstructor
public class UserBalanceManager {
    private final BalanceRepository balanceRepository;
    private final LockHandler lockHandler;
    private final TransactionHandler transactionHandler;
    private final RedisKeyFactory redisKeyFactory;

    @Transactional(readOnly = true)
    public void validate(Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("ID는 0이하가 될 수 없습니다.");
        }

        if (!balanceRepository.existsById(userId)) {
            throw new RuntimeException("확인할 수 없는 유저입니다.");
        }
    }
    
    @Transactional(readOnly = true)
    public Balance findByUserId(Long userId) {
        if(userId<=0){
            throw new IllegalArgumentException("ID는 0이하가 될 수 없습니다.");
        }
        return balanceRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("확인할 수 없는 사용자입니다."));
    }

    public void chargeBalance(Long userId, int balance) {
        if(balance<=0){
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
        }
        lockHandler.runOnLock(
            redisKeyFactory.createUserKey(userId),
            0L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                balanceRepository.chargeBalance(userId, balance);
                return null;
            })
        );
    }

    public void executePayment(Long userId, int balance) {
        lockHandler.runOnLock(
            redisKeyFactory.createUserKey(userId),
            2000L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                Balance userBalance = balanceRepository.findByUserId(userId).orElseThrow(() -> {throw new RuntimeException("확인할 수 없는 유저입니다.");});
                if(userBalance.getBalance() < balance){
                    throw new RuntimeException("보유 금액이 모자릅니다.");
                }
                balanceRepository.executePayment(userId, balance);
                return null;
            })
        );
    }
}
