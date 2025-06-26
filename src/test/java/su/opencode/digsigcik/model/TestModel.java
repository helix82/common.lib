package su.opencode.digsigcik.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import su.opencode.digsigcik.audit.AuditLogEventEnum;
import su.opencode.digsigcik.audit.AuditSeverityEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEST_MODEL")
public class TestModel {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @Schema(example = "1", required = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @Schema(example = "2024-09-03T11:43:17", required = true)
    @CreationTimestamp
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Column(name = "login", columnDefinition = "VARCHAR(255)")
    @Schema(example = "ivanov", required = true)
    @JsonProperty("login")
    private String login;

    @Column(name = "event", columnDefinition = "VARCHAR(255)")
    @Schema(example = "M4D_UPDATE", required = true)
    @Enumerated(EnumType.STRING)
    @JsonProperty("event")
    private AuditLogEventEnum event = AuditLogEventEnum.NA;

    @Column(name = "severity", columnDefinition = "VARCHAR")
    @Schema(example = "WARNING", required = true, defaultValue = "INFO")
    @Enumerated(EnumType.STRING)
    @JsonProperty("severity")
    private AuditSeverityEnum severity = AuditSeverityEnum.INFO;

    @Column(name = "success", columnDefinition = "BOOLEAN")
    @Schema(example = "true", required = true, defaultValue = "false")
    @JsonProperty("success")
    private Boolean success = false;

    @Column(name = "input_data", columnDefinition = "VARCHAR")
    @Schema(example = "Args are: ...")
    @JsonProperty("inputData")
    private String inputData;

    @Column(name = "output_data", columnDefinition = "VARCHAR")
    @Schema(example = "Done! Results are...")
    @JsonProperty("outputData")
    private String outputData;

    @Column(name = "time_elapsed", columnDefinition = "BIGINT")
    @Schema(example = "10", description = "Размер работы ивента в миллисекундах")
    @JsonProperty("timeElapsed")
    private long timeElapsed;

    @Column(name = "payload_size", columnDefinition = "BIGINT")
    @Schema(example = "10", description = "Размер обрабатываемых данных в байтах")
    @JsonProperty("payloadSize")
    private long payloadSize;

    /*
    public static AuditLogEntity getInstance(AuditLogEventEnum event) {
        AuditLogEntity logEntity = new AuditLogEntity();
        logEntity.setEvent(event);
        return logEntity;
    }

    // TODO: refactor this or SpecificationBuilder
    @Deprecated
    public static AuditLogEntity of(CommonAuditEntityDto dto) {
        AuditLogEntity logEntity = new AuditLogEntity();
        if ( Objects.nonNull(dto.getLogin()) && !dto.getLogin().isBlank() )
            logEntity.setLogin(dto.getLogin());
        if ( Objects.nonNull(dto.getEvent()) )
            logEntity.setEvent(dto.getEvent());
        if ( Objects.nonNull(dto.getSeverity()) )
            logEntity.setSeverity(dto.getSeverity());
        if ( Objects.nonNull(dto.getWasSuccessful()) )
            logEntity.setSuccess(dto.getWasSuccessful());
        if ( Objects.nonNull(dto.getInputData()) && !dto.getInputData().isBlank() )
            logEntity.setInputData(dto.getInputData());
        if ( Objects.nonNull(dto.getResultData()) && !dto.getResultData().isBlank() )
            logEntity.setOutputData(dto.getResultData());

        return logEntity;
    }

    public static AuditLogEntity of(AuditLogEvent event) {
        AuditLogEntity logEntity = new AuditLogEntity();
        if ( Objects.nonNull(event.getLogin()) && !event.getLogin().isBlank() )
            logEntity.setLogin(event.getLogin());
        if ( Objects.nonNull(event.getEvent()) )
            logEntity.setEvent(event.getEvent());
        if ( Objects.nonNull(event.getSeverity()) )
            logEntity.setSeverity(event.getSeverity());
        if ( Objects.nonNull(event.getWasSuccessful()) )
            logEntity.setSuccess(event.getWasSuccessful());
        if ( Objects.nonNull(event.getInputData()) && !event.getInputData().isBlank() )
            logEntity.setInputData(event.getInputData());
        if ( Objects.nonNull(event.getResultData()) && !event.getResultData().isBlank() )
            logEntity.setOutputData(event.getResultData());

        logEntity.setTimeElapsed(
                Objects.requireNonNullElse(event.getTimeElapsed(), -1L)
        );

        logEntity.setPayloadSize(
                Objects.requireNonNullElse(event.getPayloadSize(), -1L)
        );

        return logEntity;
    }

    @Deprecated
    public boolean isPukfRelated() {
        return this.event == AuditLogEventEnum.SIMPLE_SIGN_SIGNING
                || this.event == AuditLogEventEnum.DIG_SIGN_CREATE
                || this.event == AuditLogEventEnum.DIG_SIGN_VERIFY;
    }

    public boolean isSecurityRelated() {
        // TODO: вынести категоризацию в общую библиотеку, можно создать отдельный класс-мапу (Данияр 10-03-2025)
        return this.event == AuditLogEventEnum.USER_LOGIN
                || this.event == AuditLogEventEnum.USER_LOGOUT
                || this.event == AuditLogEventEnum.USER_CERTS_UPLOAD
                || this.event == AuditLogEventEnum.SIMPLE_SIGN_CREATE
                || this.event == AuditLogEventEnum.SIMPLE_SIGN_UPDATE
                || this.event == AuditLogEventEnum.SIMPLE_SIGN_DELETE
                || this.event == AuditLogEventEnum.CRL_UPLOAD
                || this.event == AuditLogEventEnum.CRL_DELETE
                || this.event == AuditLogEventEnum.TLS_CA_CERTS_UPLOAD
                || this.event == AuditLogEventEnum.TLS_CA_CERTS_CREATE
                || this.event == AuditLogEventEnum.TLS_CA_CERTS_DELETE
                || this.event == AuditLogEventEnum.TLS_SERV_CERTS_REVOKE
                || this.event == AuditLogEventEnum.TLS_SERV_CERTS_DELETE
                || this.event == AuditLogEventEnum.M4D_CREATE
                || this.event == AuditLogEventEnum.M4D_VERIFY
                || this.event == AuditLogEventEnum.M4D_UPDATE
                || this.event == AuditLogEventEnum.M4D_DELETE
                || this.event == AuditLogEventEnum.CONTAINER_VERIFY;
    }

    public String asSyslogRecord() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            return String.format("id %d | login %s", id, login);
        }
    }
     */

}
