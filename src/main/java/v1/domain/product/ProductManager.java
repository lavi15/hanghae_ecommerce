package v1.domain.product;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import v1.commons.advice.LockHandler;
import v1.commons.advice.RedisKeyFactory;
import v1.commons.advice.TransactionHandler;
import v1.domain.order.OrderProduct;
import v1.entity.product.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class ProductManager {
    private final LockHandler lockHandler;
    private final TransactionHandler transactionHandler;
    private final RedisKeyFactory redisKeyFactory;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public void validate(Long productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("ID는 0이하가 될 수 없습니다.");
        }

        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("확인할 수 없는 상품입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Product findByProductId(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("확인할 수 없는 상품입니다."));
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public int getProductPrice(Long productId) {
        return productRepository.getProductPrice(productId);
    }

    public void deductProduct(List<OrderProduct> orderProducts) {
        orderProducts.sort(Comparator.comparing(OrderProduct::getProductId));

        lockHandler.runOnLock(
            redisKeyFactory.createProductKey(orderProducts.stream().map(OrderProduct::getProductId).toList()),
            2000L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                orderProducts.forEach(orderProduct -> productRepository.deductProduct(orderProduct.getProductId(), orderProduct.getQuantity()));
                return null;
            })
        );
    }

    public void deductProduct(OrderProduct orderProduct) {
        lockHandler.runOnLock(
            redisKeyFactory.createProductKey(orderProduct.getProductId()),
            2000L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                productRepository.deductProduct(orderProduct.getProductId(), orderProduct.getQuantity());
                return null;
            })
        );
    }

    public void deductProduct(Long productId, int quantity) {
        lockHandler.runOnLock(
            redisKeyFactory.createProductKey(productId),
            2000L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                productRepository.deductProduct(productId, quantity);
                return null;
            })
        );
    }

    public void addProduct(List<OrderProduct> orderProducts) {
        orderProducts.sort(Comparator.comparing(OrderProduct::getProductId));

        lockHandler.runOnLock(
            redisKeyFactory.createProductKey(orderProducts.stream().map(OrderProduct::getProductId).toList()),
            2000L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                orderProducts.forEach(orderProduct -> productRepository.addProduct(orderProduct.getProductId(), orderProduct.getQuantity()));
                return null;
            })
        );
    }

    public void addProduct(OrderProduct orderProduct) {
        lockHandler.runOnLock(
            redisKeyFactory.createProductKey(orderProduct.getProductId()),
            2000L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                productRepository.addProduct(orderProduct.getProductId(), orderProduct.getQuantity());
                return null;
            })
        );
    }

    public void addProduct(Long productId, int quantity) {
        lockHandler.runOnLock(
            redisKeyFactory.createProductKey(productId),
            2000L,
            1000L,
            () -> transactionHandler.runOnWriteTransaction(() -> {
                productRepository.addProduct(productId, quantity);
                return null;
            })
        );
    }
}
