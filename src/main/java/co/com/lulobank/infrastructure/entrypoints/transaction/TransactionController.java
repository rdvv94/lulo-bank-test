package co.com.lulobank.infrastructure.entrypoints.transaction;

import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.model.transaction.TransactionRequest;
import co.com.lulobank.domain.usecase.transaction.TransactionOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    private final TransactionOperations transactionOperations;

    public TransactionController(TransactionOperations transactionOperations) {
        this.transactionOperations = transactionOperations;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<AccountResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionOperations.generateTransaction(transactionRequest));
    }

}
