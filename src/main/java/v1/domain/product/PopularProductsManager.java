package v1.domain.product;

import java.util.List;
import java.util.concurrent.TimeUnit;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import v1.entity.order.repository.OrderProductRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class PopularProductsManager {
    private final OrderProductRepository orderProductRepository;
    private final ProductManager productManager;

    @Transactional
    public List<Product> getPopularProductsInThreeDays() {
        try {
            return orderProductRepository.getPopularProductsInThreeDays().stream()
                    .map(orderProduct -> productManager.findByProductId(orderProduct.getProductId())).toList();
        } catch (Exception e) {
            log.error("Error fetching popular products from the database", e);
            throw new RuntimeException("상품 조회에 실패했습니다.");
        }
    }
}
