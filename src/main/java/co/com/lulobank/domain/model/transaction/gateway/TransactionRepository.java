package co.com.lulobank.domain.model.transaction.gateway;

import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.model.transaction.TransactionRequest;

import java.util.List;

public interface TransactionRepository {

    void save(AccountTransaction accountTransaction);

    List<TransactionRequest.TransactionData> getTransactionsLastMinutes(Integer minutes, Integer accountId);
}
