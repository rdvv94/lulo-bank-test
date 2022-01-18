package co.com.lulobank.domain.usecase.transaction;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.model.account.gateway.AccountRepository;
import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.model.transaction.TransactionRequest;
import co.com.lulobank.domain.model.transaction.gateway.TransactionRepository;
import co.com.lulobank.domain.usecase.transaction.rules.TransactionRules;
import co.com.lulobank.domain.usecase.transaction.rules.validatecard.ValidateCard;
import co.com.lulobank.domain.usecase.transaction.rules.validateinterval.ValidateTransactionInterval;
import co.com.lulobank.domain.usecase.transaction.rules.validatelimit.ValidateLimit;

import java.util.List;

public class TransactionUseCase implements TransactionOperations {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionUseCase(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public AccountResponse generateTransaction(TransactionRequest transactionRequest) {
        return accountRepository.getAccount(
                transactionRequest.getTransaction().getAccountId()).map(accountData -> {

            var accountTransaction = AccountTransaction.builder()
                    .transaction(transactionRequest.getTransaction())
                    .account(toRequest(accountData)).build();

            if (!TransactionRules.validateCard(accountData, new ValidateCard())) {
                return accountResponse(accountData, "card-not-active");
            }

            if (TransactionRules.validateLimit(accountTransaction, new ValidateLimit())) {
                return accountResponse(accountData, "insufficient-limit");
            }

            if (TransactionRules.transactionLimits(2, 3, accountData.getAccountId(),
                    new ValidateTransactionInterval(transactionRepository))) {
                return accountResponse(accountData, "high-frequency-small-interval");
            }

            if (TransactionRules.transactionLimits(2, 1, accountTransaction,
                    new ValidateTransactionInterval(transactionRepository))) {
                return accountResponse(accountData, "doubled-transaction");
            }

            transactionRepository.save(accountTransaction);
            var accountRequest = AccountRequest.builder()
                    .account(AccountRequest.AccountData.builder()
                            .accountId(accountData.getAccountId())
                            .availableLimit(accountData.getAvailableLimit() -
                                    transactionRequest.getTransaction().getAmount())
                            .activeCard(accountData.isActiveCard())
                            .build()).build();

            return accountRepository.update(accountRequest);

        }).orElse(AccountResponse.builder()
                .account(AccountResponse.AccountData.builder()
                        .violations(List.of("account-not-initialized")).build()).build());

    }

    private AccountRequest.AccountData toRequest(AccountResponse.AccountData accountData) {
        return AccountRequest.AccountData.builder()
                .accountId(accountData.getAccountId())
                .activeCard(accountData.isActiveCard())
                .availableLimit(accountData.getAvailableLimit())
                .build();
    }

    private AccountResponse accountResponse(AccountResponse.AccountData accountData, String violation) {
        return AccountResponse.builder()
                .account(accountData.toBuilder()
                        .violations(List.of(violation)).build()).build();
    }
}
