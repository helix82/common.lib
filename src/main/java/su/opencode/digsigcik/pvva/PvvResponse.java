package su.opencode.digsigcik.pvva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import su.opencode.digsigcik.pvva.enums.PvvResponseType;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PvvResponse {

    @JsonProperty("responseType")
    PvvResponseType responseType;

    @JsonProperty("subscriptionData")
    Map<String, String> subscriptionData;

    @JsonProperty("errorDetails")
    String errorDetails;

    @JsonProperty("jsonPayload")
    Map<String, Object> jsonPayload;

    @JsonProperty("filePayload")
    MultipartFile filePayload;

}
