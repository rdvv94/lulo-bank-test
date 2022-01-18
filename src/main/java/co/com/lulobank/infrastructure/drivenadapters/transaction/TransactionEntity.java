package co.com.lulobank.infrastructure.drivenadapters.transaction;

import co.com.lulobank.infrastructure.drivenadapters.account.AccountEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "transactions", schema = "public")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @Column(name = "merchant", nullable = false)
    private String merchant;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "time_give", nullable = false)
    private LocalDateTime timeGive;

    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity account;
}
