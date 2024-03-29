package v1.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import v1.entity.user.BalanceEntity;
import v1.entity.user.repository.BalanceEntityRepository;

@SpringBootTest
@ActiveProfiles("dev")
class BalanceServiceTest {
    @Autowired
    private BalanceEntityRepository balanceEntityRepository;

    @Autowired
    private UserBalanceManager userBalanceManager;

    @AfterEach
    void tearDown() {
        balanceEntityRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("0 초과의 유저포인트를 충전한다.")
    void chargeBalanceTest() {
        //given
        Long userId = 1L;
        int chargeBalance = 1000;

        BalanceEntity balanceEntity = BalanceEntity.builder()
            .balance(0)
            .userId(1L)
            .build();
        balanceEntityRepository.save(balanceEntity);

        //when
        userBalanceManager.chargeBalance(userId, chargeBalance);
        Balance balance = userBalanceManager.findByUserId(userId);

        //then
        assertThat(balance.getBalance()).isEqualTo(1000);
    }

    @Test
    @DisplayName("0 초과의 유저포인트를 충전한다.")
    void duplicateChargeBalanceTest() {
        //given
        Long userId = 1L;
        int chargeBalance = 1000;

        BalanceEntity balanceEntity = BalanceEntity.builder()
            .balance(0)
            .userId(1L)
            .build();
        balanceEntityRepository.save(balanceEntity);

        //when
        CompletableFuture<Void> firstCharge = CompletableFuture.runAsync(() ->
            userBalanceManager.chargeBalance(userId, chargeBalance)
        ).exceptionally(ex -> null);

        CompletableFuture<Void> secondCharge = CompletableFuture.runAsync(() ->
            userBalanceManager.chargeBalance(userId, chargeBalance)
        ).exceptionally(ex -> null);

        CompletableFuture.allOf(firstCharge, secondCharge).join();

        Balance afterCharge = userBalanceManager.findByUserId(userId);

        //then
        assertThat(afterCharge.getBalance()).isEqualTo(1000);
    }


    @Test
    @DisplayName("특정 유저의 잔액을 조회한다.")
    void getUserPointTest() {
        //given
        Long userId = 1L;

        BalanceEntity balanceEntity = BalanceEntity.builder()
            .balance(0)
            .userId(1L)
            .build();
        balanceEntityRepository.save(balanceEntity);

        //when
        Balance balance = userBalanceManager.findByUserId(userId);

        //then
        assertThat(balance.getBalance()).isEqualTo(0);
    }
}