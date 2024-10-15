package su.opencode.digsigcik.dtos.cryptoproxy.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class UserFavouriteCertificateEvent {
    @JsonProperty(value = "Login", required = true)
    private String login;
}
