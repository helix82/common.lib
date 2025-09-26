package su.opencode.digsigcik.ucfk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateChangeRequestStatus {

//    @NotBlank
    @JsonProperty("Status")
    private int status;

    @JsonProperty("CancelProtocol")
    private String cancelProtocol;

    @JsonProperty("StatusChangeDate")
    private String statusChangeDate;

}