package su.opencode.digsigcik.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEvent {

    @Schema(example = "ivanov", required = true)
    @JsonProperty("login")
    String login;

    @Schema(example = "M4D_UPDATE", required = true)
    @JsonProperty("event")
    AuditLogEventEnum event;

    @Schema(example = "WARNING", required = true, defaultValue = "INFO")
    @JsonProperty("severity")
    AuditSeverityEnum severity;

    @Schema(example = "true", required = true, defaultValue = "false")
    @JsonProperty("success")
    Boolean wasSuccessful;

    @Schema(example = "Args are: ...")
    @JsonProperty("inputData")
    String inputData;

    @Schema(example = "Done! Results are...")
    @JsonProperty("resultData")
    String resultData;

    @Schema(example = "15", description = "Время, затраченное на операцию")
    @JsonProperty("timeElapsed")
    long timeElapsed;
}
