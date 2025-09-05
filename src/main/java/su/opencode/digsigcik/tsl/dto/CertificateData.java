package su.opencode.digsigcik.tsl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificateData {
    @JsonProperty("accreditationStatus")
    private String accreditationStatus;
    @JsonProperty("serialNumber")
    private String serialNumber;
    @JsonProperty("dateStart")
    private LocalDateTime dateStart;
    @JsonProperty("dateEnd")
    private LocalDateTime dateEnd;
    @JsonProperty("thumbprint")
    private String thumbprint;
    @JsonProperty("keyId")
    private String keyId;
    @JsonProperty("issuedTo")
    private String issuedTo;
    @JsonProperty("typeInput")
    private String typeInput;
    @JsonProperty("fileRef")
    private Long fileRef;
    @JsonProperty("certificate")
    private String certificate;
}
