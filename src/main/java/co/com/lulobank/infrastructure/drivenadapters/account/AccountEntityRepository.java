package co.com.lulobank.infrastructure.drivenadapters.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Integer> {

    AccountEntity findByAccountId(Integer accountId);
}
