package co.com.lulobank.infrastructure.drivenadapters.account;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;

import java.util.ArrayList;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountEntity toEntity(AccountRequest.AccountData accountData) {
        return new AccountEntity(accountData.getAccountId(),
                accountData.getAvailableLimit(),
                accountData.isActiveCard());
    }

    public static AccountResponse.AccountData toDtoDataResponse(AccountEntity accountEntity) {
        return AccountResponse
                .AccountData.builder()
                .accountId(accountEntity.getAccountId())
                .activeCard(accountEntity.isActiveCard())
                .availableLimit(accountEntity.getAvailableLimit())
                .violations(new ArrayList<>())
                .build();
    }

    public static AccountResponse toDtoResponse(AccountResponse.AccountData accountData) {
        return AccountResponse.builder().account(accountData).build();
    }
}
