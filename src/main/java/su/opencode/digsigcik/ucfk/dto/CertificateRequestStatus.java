package su.opencode.digsigcik.ucfk.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class CertificateRequestStatus {

    @JsonProperty("CertificateRequestId")
    private String certificateRequestId;

    @JsonProperty("Status")
    private int status;

    @JsonProperty("CertificateInfo")
    private CertificateInfo certificateInfo;

    @JsonProperty("CertificateXmlToSign")
    private String certificateXmlToSign;

    @JsonProperty("CertificateXsltToSign")
    private String certificateXsltToSign;

    @JsonProperty("CertificateBase64")
    private String certificateBase64;

    @JsonProperty("CancelProtocol")
    private CancelProtocol cancelProtocol;
}
