package co.com.lulobank.domain.model.transaction;

import co.com.lulobank.domain.model.account.AccountRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    private AccountRequest.AccountData account;
    private TransactionRequest.TransactionData transaction;

}
