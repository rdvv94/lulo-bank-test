package co.com.lulobank.infrastructure.entrypoints.account;

import co.com.lulobank.domain.model.account.AccountRequest;
import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.usecase.account.AccountOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {AccountController.class})
class AccountControllerTest {

    @MockBean
    AccountOperations accountOperations;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void init() {
        openMocks(this);
    }

    @Test
    void save() throws Exception {

        var accountData = AccountResponse.AccountData.builder()
                .accountId(1)
                .activeCard(Boolean.TRUE)
                .availableLimit(1100.0)
                .violations(new ArrayList<>())
                .build();

        var accountResponse = AccountResponse.builder().account(accountData).build();

        when(accountOperations.save(any(AccountRequest.class))).thenReturn(accountResponse);

        var response = mockMvc
                .perform(
                        post("/account/save")
                                .contentType(APPLICATION_JSON_VALUE)
                                .content("{\n" +
                                        "    \"account\": {\n" +
                                        "        \"id\": 1,\n" +
                                        "        \"active-card\": true,\n" +
                                        "        \"available-limit\": 800\n" +
                                        "    }\n" +
                                        "}")
                )
                .andExpect(status().isCreated())
                .andReturn();

        var actual = response.getResponse().getContentAsString();
        assertThat(actual).isNotNull();

    }

}
