package v1.controller.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v1.controller.ApiResponse;
import v1.domain.product.ProductService;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/request")
    public ApiResponse<List<ProductResponse>> getProducts() {
        return ApiResponse.ok(productService.getProducts()
            .stream()
            .map(ProductResponse::new)
            .toList());
    }

    @GetMapping("/popular")
    public ApiResponse<List<ProductResponse>> getPopularProductsInThreeDays(){
        return ApiResponse.ok(productService.getPopularProductsInThreeDays()
                .stream()
                .map(ProductResponse::new)
                .toList());
    }
}
