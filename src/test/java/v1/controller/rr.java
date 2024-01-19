package v1.controller;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import v1.entity.product.ProductEntity;

@SpringBootTest
@ActiveProfiles("prod")
public class rr {

    @Test
    @DisplayName("")
    void name() {
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
        //when

        //then
    }
}
