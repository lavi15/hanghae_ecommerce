package v1.entity.payment.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import v1.domain.payment.Payment;
import v1.domain.product.Product;
import v1.entity.payment.PaymentEntity;
import v1.entity.product.ProductEntity;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentEntityRepository paymentEntityRepository;

    @Override
    public void save(Payment payment) {
        paymentEntityRepository.save(PaymentEntity.fromPayment(payment));
    }
}
