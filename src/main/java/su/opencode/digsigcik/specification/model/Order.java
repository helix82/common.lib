package su.opencode.digsigcik.specification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @JsonProperty(value = "order")
    Sort.Direction order = Sort.Direction.ASC;

    /**
     * Имя поля для сортировки.
     * Например, поле <code>createdAt</code> в {@link su.opencode.digsigcik.log.model.AuditLogEntity}
     */
    @JsonProperty(value = "key")
    String key;
}