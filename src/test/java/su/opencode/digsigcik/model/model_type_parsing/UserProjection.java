package su.opencode.digsigcik.model.model_type_parsing;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_PROJECTION")
public class UserProjection extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "ИД проекции пользователя", example = "1")
    @JsonProperty("id")
    private Long id;

    @Unique
    @Column(name = "login", columnDefinition = "VARCHAR(25)")
    @Schema(name = "Логин проекции пользователя", example = "ivanov")
    private String login;

}
