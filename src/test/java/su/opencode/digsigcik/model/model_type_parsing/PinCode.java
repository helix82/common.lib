package su.opencode.digsigcik.model.model_type_parsing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.opencode.digsigcik.audit.AllowAudit;
import su.opencode.digsigcik.specification.annotation.EnableDeepSearch;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(name = "PIN_CODE")
@NoArgsConstructor
@AllArgsConstructor
public class PinCode extends DateAudit {

    /*
    *** Взято из синапи для тестирования разбора полей сложносоставного объекта
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @AllowAudit
    private Long id;

    @EnableDeepSearch
    @OneToOne
    @JoinColumn(name = "user_projection_id")
    @AllowAudit
    private UserProjection userProjection;

    @Column(name = "pin_code_hash")
    @Schema(name = "Хэш пин-кода", example = "cd70b...24f45a")
    @AllowAudit
    private String pinCodeHash;

}