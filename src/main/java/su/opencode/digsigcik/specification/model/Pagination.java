package su.opencode.digsigcik.specification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    // TODO: jakarta validation API
//    @Size(min = 0)
    @JsonProperty(value = "page")
    int page;

//    @Size(min = 1, max = 100)
    @JsonProperty(value = "itemsPerPage")
    int itemsPerPage;

    @Getter
//    @Size(min = 0)
    @JsonProperty(value = "pages")
    int pages;

//    @Size(min = 0)
    @JsonProperty(value = "count")
    long count;

    public int getItemsPerPage() {
        return (itemsPerPage < 0) ? 0 : itemsPerPage;
    }

    public int getPage() {
        return (page < 0) ? 0 : page;
    }
}
