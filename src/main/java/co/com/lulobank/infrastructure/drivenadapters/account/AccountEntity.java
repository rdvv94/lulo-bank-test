package co.com.lulobank.infrastructure.drivenadapters.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts", schema = "public")
public class AccountEntity {

    @Id
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "available_limit", nullable = false)
    private Double availableLimit;

    @Column(name = "active_card", nullable = false)
    private boolean activeCard;
}
