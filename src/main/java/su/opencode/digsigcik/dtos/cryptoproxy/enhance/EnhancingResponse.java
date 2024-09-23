package su.opencode.digsigcik.dtos.cryptoproxy.enhance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_ABSENT, content = JsonInclude.Include.NON_ABSENT)
public class EnhancingResponse {

    @JsonProperty("Operation")
    private Operation operation;

    @Data
    public static class Operation {
        @JsonProperty("Result")
        private Result result;
        @JsonProperty("Status")
        private String status;
    }

    @Data
    public static class Result {
        @JsonProperty("ProcessedDocuments")
        private List<ProcessedDocument> processedDocuments;
    }

    @Data
    public static class ProcessedDocument {
        @JsonProperty("Content")
        private String content;
        @JsonProperty("Status")
        private String status;
    }
}
