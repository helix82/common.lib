package su.opencode.digsigcik.dtos.cryptoproxy.sign;

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
public class SignDocumentResponse {

    @JsonProperty(value = "ResultIsSuccess")
    private boolean success;

    @JsonProperty(value = "SignType")
    private SignType signType;

    @JsonProperty(value = "ResultContent")
    private String content;

    @JsonProperty(value = "Ref-ID")
    private String refId;
}
