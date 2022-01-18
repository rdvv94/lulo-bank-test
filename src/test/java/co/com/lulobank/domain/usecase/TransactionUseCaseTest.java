package co.com.lulobank.domain.usecase;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.model.account.gateway.AccountRepository;
import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.model.transaction.TransactionRequest;
import co.com.lulobank.domain.model.transaction.gateway.TransactionRepository;
import co.com.lulobank.domain.usecase.transaction.TransactionOperations;
import co.com.lulobank.domain.usecase.transaction.TransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionUseCaseTest {

    TransactionRepository transactionRepository;
    AccountRepository accountRepository;

    TransactionRequest.TransactionData transactionData;

    TransactionOperations transactionOperations;

    @BeforeEach
    void init() {
        transactionData = TransactionRequest.TransactionData.builder()
                .accountId(1)
                .amount(1000.0)
                .merchant("Lulo Bank")
                .timeGive(LocalDateTime.now())
                .build();
        transactionRepository = mock(TransactionRepository.class);
        accountRepository = mock(AccountRepository.class);

        transactionOperations = new TransactionUseCase(transactionRepository, accountRepository);
    }

    @Test
    void cardNotActive() {

        var transaction = TransactionRequest.TransactionData.builder()
                .timeGive(LocalDateTime.now())
                .merchant("Lulo Bank")
                .amount(1000.0)
                .accountId(1)
                .build();

        var transactionRequest = new TransactionRequest();
        transactionRequest.setTransaction(transaction);

        var accountData = AccountResponse.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.FALSE)
                .availableLimit(1000.0)
                .build();

        when(accountRepository.getAccount(anyInt())).thenReturn(Optional.of(accountData));

        var response = transactionOperations.generateTransaction(transactionRequest);
        assertThat(response).isNotNull();
        assertThat(response.getAccount().getViolations().get(0)).isEqualTo("card-not-active");
    }

    @Test
    void insufficientLimit() {

        var transaction = TransactionRequest.TransactionData.builder()
                .timeGive(LocalDateTime.now())
                .merchant("Lulo Bank")
                .amount(1100.0)
                .accountId(1)
                .build();

        var transactionRequest = new TransactionRequest();
        transactionRequest.setTransaction(transaction);

        var accountData = AccountResponse.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.TRUE)
                .availableLimit(1000.0)
                .build();

        when(accountRepository.getAccount(anyInt())).thenReturn(Optional.of(accountData));

        var response = transactionOperations.generateTransaction(transactionRequest);
        assertThat(response).isNotNull();
        assertThat(response.getAccount().getViolations().get(0)).isEqualTo("insufficient-limit");
    }

    @Test
    void highFrequencySmallInterval() {
        var transaction = TransactionRequest.TransactionData.builder()
                .timeGive(LocalDateTime.now())
                .merchant("Lulo Bank")
                .amount(1000.0)
                .accountId(1)
                .build();

        var transactionRequest = new TransactionRequest();
        transactionRequest.setTransaction(transaction);

        var accountData = AccountResponse.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.TRUE)
                .availableLimit(1100.0)
                .build();

        when(accountRepository.getAccount(anyInt())).thenReturn(Optional.of(accountData));
        when(transactionRepository.getTransactionsLastMinutes(anyInt(), anyInt()))
                .thenReturn(List.of(transactionData, transactionData, transactionData));

        var response = transactionOperations.generateTransaction(transactionRequest);
        assertThat(response).isNotNull();
        assertThat(response.getAccount().getViolations().get(0)).isEqualTo("high-frequency-small-interval");
    }

    @Test
    void doubledTransaction() {
        var transaction = TransactionRequest.TransactionData.builder()
                .timeGive(LocalDateTime.now())
                .merchant("Lulo Bank")
                .amount(1000.0)
                .accountId(1)
                .build();

        var transactionRequest = new TransactionRequest();
        transactionRequest.setTransaction(transaction);

        var accountData = AccountResponse.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.TRUE)
                .availableLimit(1100.0)
                .build();

        when(accountRepository.getAccount(anyInt())).thenReturn(Optional.of(accountData));
        when(transactionRepository.getTransactionsLastMinutes(anyInt(), anyInt()))
                .thenReturn(List.of(transactionData, transactionData));

        var response = transactionOperations.generateTransaction(transactionRequest);
        assertThat(response).isNotNull();
        assertThat(response.getAccount().getViolations().get(0)).isEqualTo("doubled-transaction");
    }

    @Test
    void generateTransaction() {

        var transaction = TransactionRequest.TransactionData.builder()
                .timeGive(LocalDateTime.now())
                .merchant("Lulo Bank")
                .amount(1000.0)
                .accountId(1)
                .build();

        var transactionRequest = new TransactionRequest();
        transactionRequest.setTransaction(transaction);

        var accountData = AccountResponse.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.TRUE)
                .availableLimit(1100.0)
                .violations(new ArrayList<>())
                .build();

        var accountResponse = AccountResponse.builder().account(accountData).build();

        when(accountRepository.getAccount(anyInt())).thenReturn(Optional.of(accountData));
        doNothing().when(transactionRepository).save(any(AccountTransaction.class));
        when(transactionRepository.getTransactionsLastMinutes(anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        when(accountRepository.update(any(AccountRequest.class))).thenReturn(accountResponse);

        var response = transactionOperations.generateTransaction(transactionRequest);
        assertThat(response).isNotNull();
        assertThat(response.getAccount().getViolations()).isEmpty();
    }

}
