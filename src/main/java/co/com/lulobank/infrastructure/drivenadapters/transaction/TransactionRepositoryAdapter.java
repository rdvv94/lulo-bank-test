package co.com.lulobank.infrastructure.drivenadapters.transaction;

import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.model.transaction.TransactionRequest;
import co.com.lulobank.domain.model.transaction.gateway.TransactionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionEntityRepository transactionEntityRepository;

    public TransactionRepositoryAdapter(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    @Transactional
    public void save(AccountTransaction accountTransaction) {
        var transactionEntity = TransactionMapper.toEntity(accountTransaction);
        transactionEntityRepository.save(transactionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionRequest.TransactionData> getTransactionsLastMinutes(Integer minutes, Integer accountId) {
        var newDate = LocalDateTime.now().minusMinutes(minutes);
        return transactionEntityRepository.findByTimeGiveBeforeAndAccountAccountId(newDate, accountId)
                .stream()
                .map(TransactionMapper::toDtoTransaction)
                .collect(Collectors.toList());
    }

}
