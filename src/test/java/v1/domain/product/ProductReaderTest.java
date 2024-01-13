package v1.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import v1.entity.product.ProductEntity;
import v1.entity.product.repository.ProductEntityRepository;

@SpringBootTest
@ActiveProfiles("dev")
class ProductReaderTest {
    @Autowired
    private ProductReader productReader;

    @Autowired
    private ProductEntityRepository productEntityRepository;

    @AfterEach
    void tearDown() {
        productEntityRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("productId를 넣을 시 상품을 Product class로 매핑하여 반환한다.")
    void readTest1() {
        //given
        ProductEntity productEntity = ProductEntity.builder()
            .name("샴푸")
            .price(10000)
            .quantity(15)
            .build();

        productEntityRepository.save(productEntity);

        Long productId = productEntity.getId();

        //when
        Product product = productReader.read(productId);

        // then
        assertThat(product.getName()).isEqualTo("샴푸");
    }

    @Test
    @DisplayName("1미만의 productId를 넣을 시 IllegalArgumentException이 발생한다.")
    void readTest2() {
        //given
        Long productId = 0L;

        //when//then
        assertThrows(IllegalArgumentException.class,() ->{
            productReader.read(productId);
        });
    }

    @Test
    @DisplayName("없는 productId를 넣을 시 RuntimeException이 발생한다.")
    void readTest3() {
        //given
        Long productId = 2L;

        //when //then
        assertThrows(RuntimeException.class, () -> {
           productReader.read(productId);
        });
    }
}