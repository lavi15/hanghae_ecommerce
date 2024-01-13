package v1.controller.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import v1.controller.ControllerTestSupport;
import v1.domain.product.Product;

class ProductControllerTest extends ControllerTestSupport {
    @Test
    @DisplayName("모든 상품을 조회 및 반환한다.")
    void getProductsTest() throws Exception {
        //given
        List<Product> products = Arrays.asList(
            Product.builder()
                .productId(1L)
                .name("샴푸")
                .price(1000)
                .quantity(5)
                .build(),
            Product.builder()
                .productId(2L)
                .name("린스")
                .price(2000)
                .quantity(10)
                .build(),
            Product.builder()
                .productId(3L)
                .name("바디워시")
                .price(1500)
                .quantity(3)
                .build()
        );
        when(productService.getProducts()).thenReturn(products);

        //when//then
        mockMvc.perform(get("/v1/product/request"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data.size()").value(3))
            .andExpect(jsonPath("$.data[0].name").value("샴푸"))
            .andExpect(jsonPath("$.data[0].price").value(1000));
    }

    @Test
    @DisplayName("3일간 제일 많이 판매된 상품을 5개 반환한다.")
    void getPopularProductsInThreeDaysTest() throws Exception{
        //given
        List<Product> products = Arrays.asList(
            Product.builder()
                .productId(1L)
                .name("샴푸")
                .price(1000)
                .quantity(5)
                .build(),
            Product.builder()
                .productId(2L)
                .name("린스")
                .price(2000)
                .quantity(10)
                .build(),
            Product.builder()
                .productId(3L)
                .name("바디워시")
                .price(1500)
                .quantity(3)
                .build()
        );
        when(productService.getPopularProductsInThreeDays()).thenReturn(products);

        //when//then
        mockMvc.perform(get("/v1/product/popular"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data.size()").value(3))
            .andExpect(jsonPath("$.data[0].name").value("샴푸"))
            .andExpect(jsonPath("$.data[0].price").value(1000));
    }
}