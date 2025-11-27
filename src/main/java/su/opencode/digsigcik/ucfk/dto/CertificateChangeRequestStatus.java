package su.opencode.digsigcik.ucfk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.opencode.digsigcik.audit.AllowAudit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateChangeRequestStatus {

//    @NotBlank
    @JsonProperty("Status")
    @AllowAudit
    private int status;

    @JsonProperty("CancelProtocol")
    @AllowAudit
    private String cancelProtocol;

    @JsonProperty("StatusChangeDate")
    @AllowAudit
    private String statusChangeDate;

}