package co.com.lulobank.domain.usecase.transaction.rules.validatecard;

import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.usecase.transaction.rules.PredicateAccount;

public class ValidateCard implements PredicateAccount {

    public boolean valid(AccountResponse.AccountData accountData) {
        return accountData.isActiveCard();
    }
}
