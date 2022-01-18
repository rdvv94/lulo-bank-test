package co.com.lulobank.infrastructure.drivenadapters.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Integer> {

    List<TransactionEntity> findByTimeGiveBeforeAndAccountAccountId(LocalDateTime dateTime, Integer accountId);
}
