package co.com.lulobank.application.config;

import co.com.lulobank.domain.model.account.gateway.AccountRepository;
import co.com.lulobank.domain.model.transaction.gateway.TransactionRepository;
import co.com.lulobank.domain.usecase.account.AccountOperations;
import co.com.lulobank.domain.usecase.account.AccountUseCase;
import co.com.lulobank.domain.usecase.transaction.TransactionOperations;
import co.com.lulobank.domain.usecase.transaction.TransactionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConfig {

    @Bean
    public AccountOperations accountOperations(AccountRepository accountRepository) {
        return new AccountUseCase(accountRepository);
    }

    @Bean
    public TransactionOperations transactionOperations(TransactionRepository transactionRepository,
                                                       AccountRepository accountRepository) {
        return new TransactionUseCase(transactionRepository, accountRepository);
    }

}
