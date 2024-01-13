package v1.entity.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import v1.domain.user.Balance;
import v1.entity.user.repository.BalanceEntityRepository;
import v1.entity.user.repository.BalanceRepository;


@SpringBootTest
@ActiveProfiles("dev")
class BalanceRepositoryImplTest {
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private BalanceEntityRepository balanceEntityRepository;

    @AfterEach
    void tearDown() {
        balanceEntityRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("user의 id로 balance를 찾아 balance class로 매핑하여 반환한다.")
    void findByUserIdTest() {
        //given
        Long userId = 1L;
        int currentBalance = 0;
        BalanceEntity balanceEntity = BalanceEntity.builder()
                .balance(currentBalance)
                .userId(userId)
                .build();
        balanceEntityRepository.save(balanceEntity);

        //when
        Balance balance = balanceRepository.findByUserId(userId).get();

        //then
        assertThat(balance.getUserId()).isEqualTo(userId);
        assertThat(balance.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("balance class로 save시 balanceEntity 객체로 매핑해서 db에 저장한다.")
    void saveTest() {
        //given
        Long userId = 1L;
        int currentBalance = 0;
        Balance balance = Balance.builder()
                .balance(currentBalance)
                .userId(userId)
                .build();

        //when
        balanceRepository.save(balance);

        //then
        assertThat(balance.getUserId()).isEqualTo(balanceEntityRepository.findByUserId(userId).get().getUserId());
    }
}