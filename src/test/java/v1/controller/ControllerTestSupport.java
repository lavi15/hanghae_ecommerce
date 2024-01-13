package v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import v1.controller.order.OrderController;
import v1.controller.product.ProductController;
import v1.controller.user.BalanceController;
import v1.domain.order.OrderService;
import v1.domain.product.ProductService;
import v1.domain.user.BalanceService;

@WebMvcTest(controllers = {
    BalanceController.class,
    ProductController.class,
    OrderController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected BalanceService balanceService;

}
