package v1.domain.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import v1.entity.order.repository.OrderProductRepository;
import v1.entity.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final OrderProductRepository orderProductRepository;
    private final ProductManager productManager;

    public List<Product> getProducts() {
        return productManager.findAllProducts();
    }

    public List<Product> getPopularProductsInThreeDays() {
        return orderProductRepository.getPopularProductsInThreeDays().stream()
                .map(orderProduct -> productManager.findByProductId(orderProduct.getProductId())).toList();
    }
}