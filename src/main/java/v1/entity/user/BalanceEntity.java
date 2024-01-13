package v1.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import v1.domain.user.Balance;
import v1.entity.BaseEntity;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "balance")
public class BalanceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int balance;

    private Long userId;

    @Builder(toBuilder = true)
    private BalanceEntity(Long id, int balance, Long userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
    }


    public static BalanceEntity fromBalance(Balance balance){
        return BalanceEntity.builder()
                .id(balance.getBalanceId())
                .balance(balance.getBalance())
                .userId(balance.getUserId())
                .build();
    }

    public Balance toBalance(){
        return Balance.builder()
                .balanceId(getId())
                .balance(getBalance())
                .userId(getUserId())
                .build();
    }

    public void chargeBalance(int balance) {
        this.balance+=balance;
    }

    public void executePayment(int balance){
        this.balance-=balance;
    }
}
