package su.opencode.digsigcik.audit;

import org.apache.commons.collections4.map.LinkedMap;

import java.util.Objects;

@FunctionalInterface
interface MapParser {

    LinkedMap<String, String> parse(Object obj);

    static LinkedMap<String, String> forArray(String ... keysAndValues) {

        var map = new LinkedMap<String, String>();

        for (int i = 0; i < keysAndValues.length - 1; i+=2) {
            String key = Objects.requireNonNullElse(keysAndValues[i], "N/A");
            String value = Objects.requireNonNullElse(keysAndValues[i+1], "N/A");
            map.put(key, value);
        }

        return map;
    }
}
