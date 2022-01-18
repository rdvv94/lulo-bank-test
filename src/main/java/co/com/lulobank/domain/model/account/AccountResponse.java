package co.com.lulobank.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccountResponse {

    private AccountData account;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    @JsonPropertyOrder({"active-card", "available-limit", "violations"})
    public static class AccountData {

        @JsonIgnore
        private Integer accountId;

        @JsonProperty("active-card")
        private boolean activeCard;

        @JsonProperty("available-limit")
        private Double availableLimit;

        private List<String> violations;
    }
}
