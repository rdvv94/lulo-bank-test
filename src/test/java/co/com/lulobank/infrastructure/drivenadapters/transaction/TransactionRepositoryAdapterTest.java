package co.com.lulobank.infrastructure.drivenadapters.transaction;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.model.transaction.TransactionRequest;
import co.com.lulobank.infrastructure.drivenadapters.account.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionRepositoryAdapterTest {

    @MockBean
    TransactionEntityRepository transactionEntityRepository;

    TransactionEntity transactionEntity;
    AccountTransaction accountTransaction;
    TransactionRepositoryAdapter transactionRepositoryAdapter;

    @BeforeEach
    void init() {
        var accountData = AccountRequest.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.TRUE)
                .availableLimit(1000.0)
                .build();

        var transaction = TransactionRequest.TransactionData.builder()
                .timeGive(LocalDateTime.now())
                .merchant("Lulo Bank")
                .amount(1000.0)
                .accountId(1)
                .build();

        accountTransaction = AccountTransaction.builder()
                .account(accountData)
                .transaction(transaction)
                .build();

        var accountEntity = new AccountEntity();
        accountEntity.setAccountId(1);
        accountEntity.setAvailableLimit(1000.0);
        accountEntity.setActiveCard(Boolean.TRUE);

        transactionEntity = new TransactionEntity();
        transactionEntity.setMerchant("Lulo Bank");
        transactionEntity.setAmount(1000.0);
        transactionEntity.setTimeGive(LocalDateTime.now());
        transactionEntity.setAccount(accountEntity);
        transactionRepositoryAdapter = new TransactionRepositoryAdapter(transactionEntityRepository);
    }

    @Test
    void getTransactionsLastMinutes() {
        when(transactionEntityRepository.findByTimeGiveBeforeAndAccountAccountId(any(LocalDateTime.class),
                anyInt())).thenReturn(List.of(transactionEntity));
        var response = transactionRepositoryAdapter
                .getTransactionsLastMinutes(1, 1);
        assertThat(response).isNotEmpty();
    }
}
