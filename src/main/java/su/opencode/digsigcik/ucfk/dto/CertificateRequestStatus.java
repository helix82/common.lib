package su.opencode.digsigcik.ucfk.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import su.opencode.digsigcik.audit.AllowAudit;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class CertificateRequestStatus {

    @JsonProperty("CertificateRequestId")
    @AllowAudit
    private String certificateRequestId;

    @JsonProperty("Status")
    @AllowAudit
    private int status;

    @JsonProperty("CertificateInfo")
    @AllowAudit
    private CertificateInfo certificateInfo;

    @JsonProperty("CertificateXmlToSign")
    @AllowAudit
    private String certificateXmlToSign;

    @JsonProperty("CertificateXsltToSign")
    @AllowAudit
    private String certificateXsltToSign;

    @JsonProperty("CertificateBase64")
    @AllowAudit
    private String certificateBase64;

    @JsonProperty("CancelProtocol")
    @AllowAudit
    private CancelProtocol cancelProtocol;
}
