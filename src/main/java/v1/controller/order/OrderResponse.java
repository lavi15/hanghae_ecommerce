package v1.controller.order;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private List<OrderProductRequest> products;

}
