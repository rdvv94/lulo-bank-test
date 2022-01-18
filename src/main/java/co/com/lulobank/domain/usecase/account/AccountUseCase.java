package co.com.lulobank.domain.usecase.account;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.model.account.gateway.AccountRepository;

public class AccountUseCase implements AccountOperations {

    private final AccountRepository accountRepository;

    public AccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse save(AccountRequest accountRequest) {
        return accountRepository.save(accountRequest);
    }
}
