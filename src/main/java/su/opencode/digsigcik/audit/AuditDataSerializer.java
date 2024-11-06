package su.opencode.digsigcik.audit;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuditDataSerializer {

    private final Gson gson;

    public AuditDataSerializer(AuditDataFilter auditDataFilter) {
        gson = auditDataFilter.get();
    }

    public String toJson(List<Object> objects) {
        return objects.stream()
                .filter(Objects::nonNull)
//                .filter( o -> AuditDataFilter.hasHandler(o.getClass()) )
                .map( gson::toJson )
                .collect(
                        Collectors.joining(", ", "{", "}")
                );
    }

    @Deprecated(since = "должна быть фильтрация, встроенная или кастомная")
    public String tryParseMap(Map objects) {
        return gson.toJson(objects);
    }

    @Deprecated
    public Map<String, Object> toMap(List<Object> objects) {
        return gson.fromJson(
                toJson(objects),
                new TypeToken<Map<String, Object>>() {}.getType()
        );
    }
}
