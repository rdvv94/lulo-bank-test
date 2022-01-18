package co.com.lulobank.infrastructure.drivenadapters.account;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.model.account.gateway.AccountRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    private final AccountEntityRepository accountEntityRepository;

    public AccountRepositoryAdapter(AccountEntityRepository accountEntityRepository) {
        this.accountEntityRepository = accountEntityRepository;
    }

    @Override
    @Transactional
    public AccountResponse save(AccountRequest accountRequest) {
        var accountEntity = AccountMapper.toEntity(accountRequest.getAccount());
        var account = accountEntityRepository.findById(accountEntity.getAccountId());

        if (account.isPresent()) {
            var data = AccountMapper.toDtoDataResponse(account.get());
            return AccountResponse.builder().account(data.toBuilder()
                    .violations(List.of("account-already-initialized")).build()).build();
        }
        return AccountMapper.toDtoResponse(
                AccountMapper.toDtoDataResponse(accountEntityRepository.save(accountEntity)));
    }

    @Override
    @Transactional
    public AccountResponse update(AccountRequest accountRequest) {
        var accountEntity = AccountMapper.toEntity(accountRequest.getAccount());
        return AccountMapper.toDtoResponse(
                AccountMapper.toDtoDataResponse(accountEntityRepository.save(accountEntity)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountResponse.AccountData> getAccount(Integer accountId) {
        var account = accountEntityRepository.findByAccountId(accountId);
        return Objects.isNull(account) ? Optional.empty() : Optional.of(AccountMapper.toDtoDataResponse(account));
    }

}
