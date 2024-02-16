package v1.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import v1.domain.order.OrderProduct;
import v1.entity.product.ProductEntity;
import v1.entity.product.repository.ProductEntityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductManagerTest {
    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Autowired
    private ProductManager productManager;

    @Test
    @DisplayName("서로 다른 순서로 주문이 이루어질때의 동시성 테스트")
    void deductProductTest() {
        //given
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(ProductEntity.builder()
                .name("샴푸")
                .price(10000)
                .quantity(10)
                .build());
        productEntities.add(ProductEntity.builder()
                .name("린스")
                .price(20000)
                .quantity(10)
                .build());
        productEntities.add(ProductEntity.builder()
                .name("오일")
                .price(15000)
                .quantity(10)
                .build());

        productEntityRepository.saveAll(productEntities);

        //when
        CompletableFuture<Void> firstDeduct = CompletableFuture.runAsync(() ->
                productManager.deductProduct(Arrays.asList(
                        new OrderProduct(1L, 1),
                        new OrderProduct(2L, 1),
                        new OrderProduct(3L, 1)
                ))
        ).exceptionally(ex -> null);

        CompletableFuture<Void> secondDeduct = CompletableFuture.runAsync(() ->
                productManager.deductProduct(Arrays.asList(
                        new OrderProduct(2L, 1),
                        new OrderProduct(1L, 1),
                        new OrderProduct(3L, 1)
                ))
        ).exceptionally(ex -> null);

        CompletableFuture.allOf(firstDeduct, secondDeduct).join();

        //then
        assertThat(productEntities).extracting("quantity").containsExactly(8, 8, 8);
    }
}