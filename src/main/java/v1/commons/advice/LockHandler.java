package v1.commons.advice;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LockHandler {
    private final RedissonClient redissonClient;

    public <T> T runOnLock(List<String> keys, Long waitTimeMs, Long leaseTimeMs, Supplier<T> execute) {
        List<RLock> locks = keys.stream().map(key -> redissonClient.getLock(key)).toList();
        try {
            boolean available = locks.stream()
                .allMatch(lock -> {
                    try {
                        return lock.tryLock(waitTimeMs, leaseTimeMs, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        return false;
                    }
                });
            if (!available) {
                throw new RuntimeException("Lock Fail");
            }
            return execute.get();
        } finally {
            locks.forEach(RLock::unlock);
        }
    }

    public <T> T runOnLock(String key, Long waitTimeMs, Long leaseTimeMs, Supplier<T> execute) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean available = lock.tryLock(waitTimeMs, leaseTimeMs, TimeUnit.MILLISECONDS);
            if (!available) {
                throw new RuntimeException("Lock Fail");
            }
            return execute.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
