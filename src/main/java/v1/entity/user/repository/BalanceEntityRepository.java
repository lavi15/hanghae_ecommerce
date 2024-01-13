package v1.entity.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v1.entity.user.BalanceEntity;

@Repository
public interface BalanceEntityRepository extends JpaRepository<BalanceEntity, Long>{
    Optional<BalanceEntity> findByUserId(Long userId);
}
