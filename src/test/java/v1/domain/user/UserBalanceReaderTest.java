package v1.domain.user;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class UserBalanceReaderTest {
    @Autowired
    private UserBalanceManager userBalanceManager;

    @Test
    @DisplayName("1미만의 userId를 넣을 시 IllegalArgumentException이 발생한다.")
    void findByUserIdTest() {
        //given
        Long userId = 0L;

        //when //then
        assertThrows(IllegalArgumentException.class, () -> {
            userBalanceManager.findByUserId(userId);
        });
    }
}