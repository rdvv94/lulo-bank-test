package co.com.lulobank.domain.usecase.transaction.rules;

import co.com.lulobank.domain.model.transaction.AccountTransaction;

public interface PredicateTransaction {
    boolean valid(AccountTransaction accountTransaction);
}
