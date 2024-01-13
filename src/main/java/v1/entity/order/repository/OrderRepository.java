package v1.entity.order.repository;

import v1.domain.order.Order;

public interface OrderRepository {
    Order save(Order order);

    void completePayment(Order order);
}
