package su.opencode.digsigcik.specification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {

    @JsonProperty(value = "value")
    String template;

    @JsonProperty(value = "fields")
    List<String> fields;
}
