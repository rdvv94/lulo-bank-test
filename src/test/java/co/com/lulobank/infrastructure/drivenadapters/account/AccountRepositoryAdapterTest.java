package co.com.lulobank.infrastructure.drivenadapters.account;

import co.com.lulobank.domain.model.account.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AccountRepositoryAdapterTest {

    @MockBean
    AccountEntityRepository accountEntityRepository;

    AccountRequest accountRequest;
    AccountEntity accountEntity;
    AccountRepositoryAdapter accountRepositoryAdapter;

    @BeforeEach
    void init() {
        var accountData = AccountRequest.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.TRUE)
                .availableLimit(1000.0)
                .build();

        accountEntity = new AccountEntity();
        accountEntity.setAccountId(1);
        accountEntity.setAvailableLimit(1000.0);
        accountEntity.setActiveCard(Boolean.TRUE);

        accountRequest = AccountRequest.builder().account(accountData).build();
        accountRepositoryAdapter = new AccountRepositoryAdapter(accountEntityRepository);
    }

    @TestFactory
    Stream<DynamicTest> save() {
        return Stream.of(
                dynamicTest("returns a record", () -> {
                    when(accountEntityRepository.findById(anyInt())).thenReturn(Optional.empty());
                    when(accountEntityRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);
                    var response = accountRepositoryAdapter.save(accountRequest);
                    assertThat(response).isNotNull();
                    assertThat(response.getAccount().getAccountId())
                            .isEqualTo(accountRequest.getAccount().getAccountId());
                }),
                dynamicTest("throws exception", () -> {
                    when(accountEntityRepository.findById(anyInt())).thenReturn(Optional.of(accountEntity));
                    var response = accountRepositoryAdapter.save(accountRequest);
                    assertThat(response).isNotNull();
                    assertThat(response.getAccount().getViolations()).isNotEmpty();
                    assertThat(response.getAccount().getViolations().get(0)).isEqualTo("account-already-initialized");
                })
        );
    }

    @Test
    void update() {
        when(accountEntityRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);
        var response = accountRepositoryAdapter.update(accountRequest);
        assertThat(response).isNotNull();
        assertThat(response.getAccount().getAccountId())
                .isEqualTo(accountRequest.getAccount().getAccountId());
    }

    @Test
    void getAccount() {
        when(accountEntityRepository.findByAccountId(anyInt())).thenReturn(accountEntity);
        var response = accountRepositoryAdapter.getAccount(1);
        assertThat(response).isPresent();
    }
}
