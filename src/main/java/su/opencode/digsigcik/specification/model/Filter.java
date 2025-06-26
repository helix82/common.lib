package su.opencode.digsigcik.specification.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import su.opencode.digsigcik.specification.FilterDataType;
import su.opencode.digsigcik.specification.FilterOperator;

import java.util.List;

@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "filterDataType" })
public class Filter {

    @JsonProperty(value = "fieldName")
    private String fieldName;

    @JsonProperty(value = "operator")
    private FilterOperator operator;

    @JsonProperty(value = "value")
    private List<String> values;

    // TODO: refactor (Данияр)
    public List<?> tryParseValues(FilterDataType dataType) {
        try {
            return dataType.equals(FilterDataType.STRING) ? dataType.strings(values) :
                    dataType.equals(FilterDataType.NUMBER) ? dataType.numbers(values) :
                            dataType.dateTimes(values);
        } catch (Exception parseException) {
            log.error("Parse Exception: {}", parseException.getMessage());
            return null;
        }
    }
}
