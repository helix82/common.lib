package su.opencode.digsigcik.ucfk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class RejectionReason {

    @JsonProperty("GroupId")
    private Integer groupId;

    @JsonProperty("GroupName")
    private String groupName;

    @JsonProperty("GroupSortOrder")
    private Integer groupSortOrder;

    @JsonProperty("Text")
    private String text;

    @JsonProperty("TextSortOrder")
    private Integer textSortOrder;
}
