package v1.commons.advice;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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
            try{
                locks.forEach(RLock::unlock);
            }catch (IllegalMonitorStateException e){
                throw new IllegalMonitorStateException("Unlock Failed");
            }
        }
    }

    public <T> T runOnLock(String key, Long waitTimeMs, Long leaseTimeMs, Supplier<T> execute) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean available = lock.tryLock(waitTimeMs, leaseTimeMs, TimeUnit.MILLISECONDS);
            if (!available) {
                log.error("Lock Failed");
                throw new RuntimeException("Lock Failed");
            }
            return execute.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                lock.unlock();
            }catch (IllegalMonitorStateException e) {
                log.error("Unlock Failed");
                throw new IllegalMonitorStateException("Unlock Failed");
            }
        }
    }
}
