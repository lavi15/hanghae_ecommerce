package v1.entity.order;

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
import v1.domain.order.OrderProduct;
import v1.entity.order.repository.OrderProductEntityRepository;
import v1.entity.order.repository.OrderProductRepository;

@SpringBootTest
@ActiveProfiles("dev")
class OrderProductRepositoryImplTest {
    @Autowired
    private OrderProductEntityRepository orderProductEntityRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {
        orderProductEntityRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("3일간 가장 많이 팔린 상품을 5개 반환한다.")
    void getPopularProductsInThreeDaysTest() {
        //given
        List<OrderProductEntity> orderProductEntities = new ArrayList<>();
        orderProductEntities.add(OrderProductEntity.builder().productId(1L).quantity(2).createdDateTime(LocalDateTime.now()).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(2L).quantity(4).createdDateTime(LocalDateTime.now()).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(3L).quantity(1).createdDateTime(LocalDateTime.now()).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(4L).quantity(5).createdDateTime(LocalDateTime.now().minusDays(2)).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(5L).quantity(3).createdDateTime(LocalDateTime.now().minusDays(3).plusSeconds(1)).build());
        orderProductEntities.add(OrderProductEntity.builder().productId(6L).quantity(10).createdDateTime(LocalDateTime.now().minusDays(3)).build());

        orderProductEntityRepository.saveAll(orderProductEntities);

        //when
        List<OrderProduct> orderProducts = orderProductRepository.getPopularProductsInThreeDays();

        //then
        assertThat(orderProducts).hasSize(5).extracting("productId").containsExactly(4L, 2L, 5L, 1L, 3L);
    }
}
