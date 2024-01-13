package v1.entity.product.repository;

import java.util.List;
import java.util.Optional;
import v1.domain.product.Product;

public interface ProductRepository{
    List<Product> findAll();
    Optional<Product> findById(Long productId);

    void saveAll(List<Product> products);

    boolean existsById(Long productId);

    void deductProduct(Long productId, int quantity);

    void addProduct(Long productId, int quantity);

    int getProductPrice(Long productId);
}
