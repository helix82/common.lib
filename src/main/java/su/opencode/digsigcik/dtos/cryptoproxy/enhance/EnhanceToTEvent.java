package su.opencode.digsigcik.dtos.cryptoproxy.enhance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.opencode.digsigcik.dtos.cryptoproxy.sign.SignType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class EnhanceToTEvent {

    @JsonProperty(value = "ContentBase64")
    private String content;

    @JsonProperty(value = "CAdESType")
    private SignType cAdESType;

    @JsonProperty(value = "UserLogin")
    private String userLogin;

    @JsonProperty(value = "IsContentHash")
    private boolean hash;

    @JsonProperty(value = "CreateDetachedSign")
    private boolean detached;
}
