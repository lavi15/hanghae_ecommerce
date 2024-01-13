package v1.entity.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v1.entity.order.OrderEntity;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long>{

}
