package v1.entity.order.repository;

import java.util.List;
import v1.domain.order.OrderProduct;

public interface OrderProductRepository {
    List<OrderProduct> getPopularProductsInThreeDays();

    void saveAll(List<OrderProduct> orderProducts);
}
