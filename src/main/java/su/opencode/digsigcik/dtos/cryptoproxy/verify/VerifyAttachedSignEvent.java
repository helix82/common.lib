package su.opencode.digsigcik.dtos.cryptoproxy.verify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class VerifyAttachedSignEvent {

    @JsonProperty(value = "ContentBase64", required = true)
    private String content;

//    @JsonProperty(value = "IsContentHash")
//    private boolean hash;

    @JsonProperty("SignatureType")
    private int signatureType;

//    @JsonProperty(value = "IncludeChain")
//    private boolean includeChain;
}
