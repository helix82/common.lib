package su.opencode.digsigcik.ucfk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CancelProtocol {

    @JsonProperty("Created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String created;

    @JsonProperty("Number")
    private String number;

    @JsonProperty("Type")
    private Integer type;

    @JsonProperty("RejectionReasons")
    private List<RejectionReason> rejectionReasons;

    @JsonProperty("Signature")
    private String signature;
}
