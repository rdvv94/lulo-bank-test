package co.com.lulobank.domain.usecase.account;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;

public interface AccountOperations {

    AccountResponse save(AccountRequest accountRequest);
}
