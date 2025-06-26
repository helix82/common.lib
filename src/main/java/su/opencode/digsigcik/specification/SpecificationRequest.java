package su.opencode.digsigcik.specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.opencode.digsigcik.specification.model.Filter;
import su.opencode.digsigcik.specification.model.Order;
import su.opencode.digsigcik.specification.model.Pagination;
import su.opencode.digsigcik.specification.model.Search;

import java.util.List;
import java.util.Objects;

@Data
@JsonInclude
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationRequest {

    // TODO: consider SpecificationRequestDto; consider eliminating any arrays in response

    @JsonProperty(value = "pagination")
    private Pagination pagination;

    @JsonProperty(value = "sortBy")
    private List<Order> sortBy;

    @JsonProperty(value = "filters")
    private List<Filter> filters;

    @JsonProperty(value = "search")
    private Search search;

    public boolean hasPagination() {
        return Objects.nonNull(pagination) && pagination.getItemsPerPage() > 0
                && pagination.getPage() >= 0;
    }

    // TODO: validation (Данияр)
    // TODO: test (Данияр)
    public boolean hasSorting() {
        if (Objects.nonNull(sortBy))
            for (Order o : sortBy)
                if (Objects.nonNull(o.getKey()) && !o.getKey().isBlank())
                    return true;
        return false;
    }

    // TODO: validation (Данияр)
    // TODO: test (Данияр)
    public boolean hasFilters() {

        if (Objects.nonNull(filters))
            for (Filter f : filters)
                if (Objects.nonNull(f.getOperator()) && Objects.nonNull(f.getFieldName()) && Objects.nonNull(f.getValues()) && !f.getFieldName().isBlank() && !f.getValues().isEmpty())
                    return true;
        return false;
    }

    // TODO: validation (Данияр)
    // TODO: test (Данияр)
    public boolean hasSearch() {
        return Objects.nonNull(search) && Objects.nonNull(search.getFields()) && !search.getFields().isEmpty()
                && Objects.nonNull(search.getTemplate()) && !search.getTemplate().isBlank();
    }
}
