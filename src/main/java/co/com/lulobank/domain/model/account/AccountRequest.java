package co.com.lulobank.domain.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    private AccountData account;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class AccountData {
        @JsonProperty("id")
        private Integer accountId;

        @JsonProperty("available-limit")
        private Double availableLimit;

        @JsonProperty("active-card")
        private boolean activeCard;
    }

}
