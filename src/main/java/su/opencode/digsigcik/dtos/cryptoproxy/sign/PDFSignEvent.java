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
public class PDFSignEvent {

    /**
     * Создаёт только ПРИКРЕПЛЁННУЮ подпись для PDF-документа?
     */

    @JsonProperty(value = "ContentBase64")
    private String content;

    @JsonProperty(value = "UserLogin")
    private String userLogin;

    @JsonProperty("CertificateId")
    private long certificateId;

    @JsonProperty("CertPinCode")
    private String pinCode;

    @JsonProperty(value = "IsContentHash")
    private boolean hash;
}
