package su.opencode.digsigcik.dtos.cryptoproxy;

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
public class UpdatePinCodeEvent {

    @JsonProperty(value = "UserLogin")
    private String userLogin;

    @JsonProperty(value = "CertificateCloudId")
    private Long certificateId;

    @JsonProperty(value = "OldCloudPinCode")
    private String oldPin;

    @JsonProperty(value = "NewCloudPinCode")
    private String newPin;

}
