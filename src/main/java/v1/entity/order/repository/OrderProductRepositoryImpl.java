package v1.entity.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import v1.domain.order.OrderProduct;
import v1.entity.order.OrderProductEntity;
import v1.entity.order.PopularProduct;
import v1.entity.order.QOrderProductEntity;

@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Autowired
    private OrderProductEntityRepository orderProductEntityRepository;

    public OrderProductRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderProduct> getPopularProductsInThreeDays(){
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        QOrderProductEntity orderProduct = QOrderProductEntity.orderProductEntity;

        return jpaQueryFactory
            .select(Projections.constructor(PopularProduct.class,
                orderProduct.productId,
                orderProduct.quantity.sum().as("salesQuantity")))
            .from(orderProduct)
            .where(orderProduct.createdDateTime.after(threeDaysAgo))
            .groupBy(orderProduct.productId)
            .orderBy(orderProduct.quantity.sum().desc())
            .limit(5)
            .fetch()
            .stream()
            .map(PopularProduct::toOrderProduct)
            .toList();
    }

    @Override
    public void saveAll(List<OrderProduct> orderProducts) {
        orderProductEntityRepository.saveAll(orderProducts.stream().map(orderProduct -> OrderProductEntity.fromOrderProduct(orderProduct)).toList());
    }
}
