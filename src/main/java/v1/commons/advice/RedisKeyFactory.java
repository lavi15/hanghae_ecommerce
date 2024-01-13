package v1.commons.advice;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyFactory {

    public String createUserKey(Long userId) {
        return "RLOCK_User_"+userId.toString();
    }

    public String createProductKey(Long productId) {
        return "RLOCK_Product_"+productId.toString();
    }


    public List<String> createProductKey(List<Long> productIds) {
        return productIds.stream().map(this::createProductKey).toList();
    }
}
