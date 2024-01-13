package v1.commons.advice;

import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;

@Component
public class TransactionHandler {

    @Transactional
    public <T> T runOnWriteTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}