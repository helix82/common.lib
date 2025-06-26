package su.opencode.digsigcik.specification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import su.opencode.digsigcik.specification.model.Filter;
import su.opencode.digsigcik.specification.model.Order;
import su.opencode.digsigcik.specification.model.Pagination;

import java.util.List;

@Data
@Builder
public class SpecificationResponse<T> {

    @JsonProperty(value = "data")
    private List<T> data;

    @JsonProperty(value = "pagination")
    private Pagination pagination;

    @JsonProperty(value = "sortBy")
    private List<Order> sortBy;

    @JsonProperty(value = "filters")
    private List<Filter> filters;
}
