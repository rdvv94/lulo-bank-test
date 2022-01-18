package co.com.lulobank.infrastructure.drivenadapters.transaction;

import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.model.transaction.TransactionRequest;
import co.com.lulobank.infrastructure.drivenadapters.account.AccountMapper;

public class TransactionMapper {

    private TransactionMapper() {
    }

    public static TransactionEntity toEntity(AccountTransaction accountTransaction) {
        var transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(AccountMapper.toEntity(accountTransaction.getAccount()));
        transactionEntity.setAmount(accountTransaction.getTransaction().getAmount());
        transactionEntity.setTimeGive(accountTransaction.getTransaction().getTimeGive());
        transactionEntity.setMerchant(accountTransaction.getTransaction().getMerchant());
        return transactionEntity;
    }

    public static TransactionRequest.TransactionData toDtoTransaction(TransactionEntity transactionEntity) {
        return TransactionRequest.TransactionData.builder()
                .amount(transactionEntity.getAmount())
                .merchant(transactionEntity.getMerchant())
                .timeGive(transactionEntity.getTimeGive())
                .build();
    }
}
