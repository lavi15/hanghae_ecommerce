package v1.domain.product;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import v1.entity.order.repository.OrderProductRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class PopularProductsManager {
    private final OrderProductRepository orderProductRepository;
    private final ProductManager productManager;
    private final RedissonClient redissonClient;
    private final String CACHE_KEY = "popular_products";

    public List<Product> getPopularProductsInThreeDays() {
        try {
            List<Product> cachedProducts = fetchCachedProducts();
            if (cachedProducts == null) {
                return updateCachedProducts();
            }
            return cachedProducts;
        } catch (Exception e) {
            log.error("Error retrieving popular products from cache", e);
            return fetchProductsFromDB();
        }
    }

    private List<Product> fetchCachedProducts() {
        RBucket<List<Product>> bucket = redissonClient.getBucket(CACHE_KEY);
        return bucket.get();
    }

    private List<Product> updateCachedProducts() {
        List<Product> products = fetchProductsFromDB();
        RBucket<List<Product>> bucket = redissonClient.getBucket(CACHE_KEY);
        bucket.set(products, 24, TimeUnit.HOURS);
        return products;
    }


    private List<Product> fetchProductsFromDB() {
        try {
            return orderProductRepository.getPopularProductsInThreeDays().stream()
                .map(orderProduct -> productManager.findByProductId(orderProduct.getProductId())).toList();
        } catch (Exception e) {
            log.error("Error fetching popular products from the database", e);
            throw new RuntimeException("상품 조회에 실패했습니다.");
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePopularProductsDaily() {
        updateCachedProducts();
    }
}
