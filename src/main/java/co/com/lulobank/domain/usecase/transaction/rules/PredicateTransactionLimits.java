package co.com.lulobank.domain.usecase.transaction.rules;

import co.com.lulobank.domain.model.transaction.AccountTransaction;

public interface PredicateTransactionLimits {

    boolean valid(Integer minutes, Integer transactionLimit, Integer accountId);

    boolean valid(Integer minutes, Integer transactionLimit, AccountTransaction accountTransaction);
}
