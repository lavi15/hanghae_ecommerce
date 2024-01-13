package v1.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import v1.entity.orderproduct.OrderProductEntity;
import v1.entity.orderproduct.repository.OrderProductEntityRepository;
import v1.entity.product.ProductEntity;
import v1.entity.product.repository.ProductEntityRepository;

@SpringBootTest
@ActiveProfiles("dev")
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Autowired
    private OrderProductEntityRepository orderProductEntityRepository;

    @AfterEach
    void tearDown() {
        orderProductEntityRepository.deleteAllInBatch();
        productEntityRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("모든 상품을 조회하여 반환한다.")
    void getProductsTest() {
        //given
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(ProductEntity.builder()
            .name("샴푸")
            .price(10000)
            .quantity(15)
            .build());
        productEntities.add(ProductEntity.builder()
            .name("린스")
            .price(20000)
            .quantity(13)
            .build());
        productEntities.add(ProductEntity.builder()
            .name("오일")
            .price(15000)
            .quantity(10)
            .build());

        productEntityRepository.saveAll(productEntities);

        //when
        List<Product> products = productService.getProducts();

        //then
        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("3일간 가장 많이 팔린 상품을 가장 많이 팔린순으로 정렬하여 5개 반환한다.")
    void getPopularProductsInThreeDaysTest() {
        //given
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(ProductEntity.builder()
            .name("샴푸")
            .price(10000)
            .quantity(15)
            .build());
        productEntities.add(ProductEntity.builder()
            .name("린스")
            .price(20000)
            .quantity(13)
            .build());
        productEntities.add(ProductEntity.builder()
            .name("오일")
            .price(15000)
            .quantity(10)
            .build());
        productEntities.add(ProductEntity.builder()
            .name("바디로션")
            .price(30000)
            .quantity(20)
            .build());
        productEntities.add(ProductEntity.builder()
            .name("바디워시")
            .price(12000)
            .quantity(15)
            .build());
        productEntities.add(ProductEntity.builder()
            .name("클랜징폼")
            .price(20000)
            .quantity(11)
            .build());

        productEntityRepository.saveAll(productEntities);

        List<OrderProductEntity> orderProductEntities = new ArrayList<>();
        orderProductEntities.add(OrderProductEntity.builder().productId(4L).quantity(2).createdDateTime(LocalDateTime.now()).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(5L).quantity(4).createdDateTime(LocalDateTime.now()).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(6L).quantity(1).createdDateTime(LocalDateTime.now()).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(7L).quantity(5).createdDateTime(LocalDateTime.now().minusDays(2)).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(8L).quantity(3).createdDateTime(LocalDateTime.now().minusDays(3).plusSeconds(1)).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(9L).quantity(10).createdDateTime(LocalDateTime.now().minusDays(3)).build());

        orderProductEntityRepository.saveAll(orderProductEntities);

        //when
        List<Product> products = productService.getPopularProductsInThreeDays();

        //then (3일간 가장 많이 팔린 상품을 5개 반환한다.)
        assertThat(products).hasSize(5);

        //then (가장 많이 팔린 상품순으로 정렬하여 반환한다.)
        assertThat(products).extracting("productId").containsExactly(7L, 5L, 8L, 4L, 6L);
    }
}