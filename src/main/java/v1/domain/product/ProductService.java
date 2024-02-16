package v1.domain.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final PopularProductsManager popularProductsManager;
    private final ProductManager productManager;

    public List<Product> getProducts() {
        return productManager.findAllProducts();
    }

    @Cacheable(value = "popularProducts")
    public List<Product> getPopularProductsInThreeDays() {
        return popularProductsManager.getPopularProductsInThreeDays();
    }

    @CacheEvict(value = "popularProducts", allEntries = true)
    public void clearCache() {
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePopularProductsDaily() {
        clearCache();
        popularProductsManager.getPopularProductsInThreeDays();
    }
}