package v1.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import v1.commons.advice.LockHandler;
import v1.commons.advice.RedisKeyFactory;
import v1.commons.advice.TransactionHandler;
import v1.domain.user.Balance;
import v1.entity.payment.repository.PaymentRepository;
import v1.entity.user.repository.BalanceRepository;

@Component
@RequiredArgsConstructor
public class PaymentManager {
    private final PaymentRepository paymentRepository;

    @Transactional
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
