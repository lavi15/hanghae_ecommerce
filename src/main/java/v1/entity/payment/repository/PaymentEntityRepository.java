package v1.entity.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v1.entity.payment.PaymentEntity;
import v1.entity.product.ProductEntity;

@Repository
public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, Long>{
}
