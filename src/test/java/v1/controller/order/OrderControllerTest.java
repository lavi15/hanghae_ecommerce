package v1.controller.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import v1.controller.ControllerTestSupport;
import v1.domain.order.Order;
import v1.domain.order.OrderProduct;

class OrderControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("주문을 생성한다.")
    void createOrderTest() throws Exception {
        //given
        List<OrderProductRequest> orderProductRequests = new ArrayList<>();
        orderProductRequests.add(OrderProductRequest.builder().productId(1L).quantity(2).build());
        orderProductRequests.add(OrderProductRequest.builder().productId(2L).quantity(3).build());
        orderProductRequests.add(OrderProductRequest.builder().productId(3L).quantity(4).build());
        OrderRequest orderRequest = OrderRequest.builder()
            .userId(1L)
            .products(orderProductRequests)
            .build();

        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(OrderProduct.builder().productId(1L).quantity(2).build());
        orderProducts.add(OrderProduct.builder().productId(2L).quantity(3).build());
        orderProducts.add(OrderProduct.builder().productId(3L).quantity(4).build());
        Order order = Order.builder()
            .orderId(1L)
            .userId(1L)
            .orderProducts(orderProducts)
            .build();
        when(orderService.createOrder(any())).thenReturn(order);

        //when//then
        mockMvc.perform(post("/v1/orders/create")
            .content(objectMapper.writeValueAsString(orderRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data.orderId").value(1L))
            .andExpect(jsonPath("$.data.products.size()").value(3));
    }
}