package v1.entity.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v1.entity.product.ProductEntity;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long>{
}
