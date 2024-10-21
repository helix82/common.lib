package su.opencode.digsigcik.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuditSeverityEnum {

    INFO("INFO"),
    DEBUG("DEBUG"),
    WARNING("WARNING"),
    ERROR("ERROR");

    private final String value;
}

