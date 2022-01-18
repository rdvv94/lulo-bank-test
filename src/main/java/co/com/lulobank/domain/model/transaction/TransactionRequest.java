package co.com.lulobank.domain.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private TransactionData transaction;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionData {
        private String merchant;
        private Double amount;
        @JsonProperty("time")
        private LocalDateTime timeGive;
        @JsonProperty("account-id")
        private Integer accountId;
    }

}
