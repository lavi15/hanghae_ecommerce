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
    private UserBalanceReader userBalanceReader;

    @Test
    @DisplayName("1미만의 userId를 넣을 시 IllegalArgumentException이 발생한다.")
    void readTest() {
        //given
        Long userId = 0L;

        //when //then
        assertThrows(IllegalArgumentException.class, () -> {
            userBalanceReader.read(userId);
        });
    }

    @Test
    @DisplayName("1미만의 chargeBalance를 넣을 시 IllegalArgumentException이 발생한다.")
    void readTest2() {
        //given
        Long userId = 1L;
        int chargeBalance = 0;

        //when //then
        assertThrows(IllegalArgumentException.class, () -> {
            userBalanceReader.read(userId, chargeBalance);
        });
    }

    @Test
    @DisplayName("존재하지 않는 userId를 넣을 시 RuntimeException을 발생시킨다.")
    void readTest3() {
        //given
        Long userId = 1L;

        //when //then
        assertThrows(RuntimeException.class, () -> {
            userBalanceReader.read(userId);
        });
    }
}