package co.com.lulobank.domain.model.account.gateway;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;

import java.util.Optional;

public interface AccountRepository {

    AccountResponse save(AccountRequest accountRequest);

    AccountResponse update(AccountRequest accountRequest);

    Optional<AccountResponse.AccountData> getAccount(Integer accountId);
}
