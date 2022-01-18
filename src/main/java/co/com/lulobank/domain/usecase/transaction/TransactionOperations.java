package co.com.lulobank.domain.usecase.transaction;

import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.model.transaction.TransactionRequest;

public interface TransactionOperations {

    AccountResponse generateTransaction(TransactionRequest transactionRequest);
}
