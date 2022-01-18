package co.com.lulobank.domain.usecase.transaction.rules.validatelimit;

import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.usecase.transaction.rules.PredicateTransaction;

public class ValidateLimit implements PredicateTransaction {

    @Override
    public boolean valid(AccountTransaction accountTransaction) {
        return accountTransaction.getTransaction().getAmount() > accountTransaction.getAccount().getAvailableLimit();
    }
}
