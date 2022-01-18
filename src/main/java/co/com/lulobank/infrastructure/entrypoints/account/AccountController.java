package co.com.lulobank.infrastructure.entrypoints.account;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.usecase.account.AccountOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountOperations accountOperations;

    public AccountController(AccountOperations accountOperations) {
        this.accountOperations = accountOperations;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<AccountResponse> save(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri())
                .body(accountOperations.save(accountRequest));
    }

}
