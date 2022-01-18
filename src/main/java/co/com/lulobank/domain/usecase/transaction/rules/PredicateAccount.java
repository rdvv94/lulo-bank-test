package co.com.lulobank.domain.usecase.transaction.rules;

import co.com.lulobank.domain.model.account.AccountResponse;

public interface PredicateAccount {

    boolean valid(AccountResponse.AccountData accountData);
}
