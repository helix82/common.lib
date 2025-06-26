package su.opencode.digsigcik.specification;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public enum FilterDataType {

    STRING,

    NUMBER,

    DATETIME_ISO;

    public List<?> parseSerializedValues(List<String> values) {
        switch (this) {
            case STRING:
                return strings(values);
            case NUMBER:
                return numbers(values);
            case DATETIME_ISO:
                return dateTimes(values);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Deprecated
    public List<String> strings(List<String> values) {
        return values;
    }

    public Double number(String value) {
        if (Objects.isNull(value) || value.isBlank())
            return null;
        else
            return Double.valueOf(value);
    }

    @Deprecated
    public List<Double> numbers(List<String> values) {
        List<Double> list = new ArrayList<>();
        for (String s : values)
            list.add(number(s));
        return list;
    }

    private final static SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final static SimpleDateFormat FORMAT2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public LocalDateTime dateTime(String value) {
        if (Objects.isNull(value) || value.isBlank())
            return null;
        else {
//            return DateUtils.parseZonedDateTime(value).toLocalDateTime();
            return parseLocalDateTimes(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME, DateTimeFormatter.ISO_OFFSET_DATE_TIME).orElseThrow();
        }
    }

    private static Optional<LocalDateTime> parseLocalDateTimes(String value, DateTimeFormatter ... formatters) {
        for (DateTimeFormatter formatter : formatters) {
            var opt = tryParseLocalDateTime(value, formatter);
            if (opt.isPresent())
                return opt;
        }
        return Optional.empty();
    }

    private static Optional<LocalDateTime> tryParseLocalDateTime(String value, DateTimeFormatter formatter) {
        try {
            return Optional.of(LocalDateTime.parse(value, formatter));
        } catch (DateTimeParseException exception) {
            return Optional.empty();
        }
    }

    @Deprecated
    public List<LocalDateTime> dateTimes(List<String> values) {
        List<LocalDateTime> list = new ArrayList<>();
        for (String s : values)
            list.add(dateTime(s));
        return list;
    }
}
