package su.opencode.digsigcik.pvva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.opencode.digsigcik.pvva.enums.PvvResponseType;

import java.util.Map;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PvvResponse {

    @JsonProperty("responseType")
    PvvResponseType responseType;

    @JsonProperty("subscriptionData")
    Map<String, String> subscriptionData;

    @JsonProperty("errorDetails")
    String errorDetails;

    @JsonProperty("saveload")
    Map<String, Object> saveload;
}
