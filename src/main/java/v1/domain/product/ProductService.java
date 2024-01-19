package v1.domain.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final PopularProductsManager popularProductsManager;
    private final ProductManager productManager;

    public List<Product> getProducts() {
        return productManager.findAllProducts();
    }

    public List<Product> getPopularProductsInThreeDays() {
        return popularProductsManager.getPopularProductsInThreeDays();
    }
}