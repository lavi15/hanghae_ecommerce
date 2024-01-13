package v1.entity.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v1.entity.order.OrderProductEntity;

@Repository
public interface OrderProductEntityRepository extends JpaRepository<OrderProductEntity, Long>{

    OrderProductEntity findByProductId(Long productId);
}