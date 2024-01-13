package v1.entity.payment.repository;

import java.util.List;
import java.util.Optional;
import v1.domain.payment.Payment;
import v1.domain.product.Product;

public interface PaymentRepository {
    void save(Payment payment);
}
