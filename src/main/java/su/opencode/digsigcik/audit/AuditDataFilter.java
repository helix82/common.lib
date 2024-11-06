package su.opencode.digsigcik.audit;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

@Slf4j
public class AuditDataFilter implements Supplier<Gson> {

    public static final String MASKED_BARCODE = "~~~~~~~~~~~";

    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private final static Set<Class<?>> handledClasses = new HashSet<>();
    private final static Set<Class<?>> handledSuperClasses = new HashSet<>();

    public AuditDataFilter() {

        gsonBuilder.addSerializationExclusionStrategy(new FieldOnlyExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
//                        boolean wasntFoundInIncludeProperties = auditConfiguration.shouldSkipField(fieldAttributes);
//                        return !fieldHasAllowAnnotation && wasntFoundInIncludeProperties;
                        boolean fieldHasAllowAnnotation = Objects.nonNull( fieldAttributes.getAnnotation(AllowAudit.class) );
                        return !fieldHasAllowAnnotation;
                    }
                })
                .serializeNulls()
                .setFormattingStyle(FormattingStyle.COMPACT)
                .setStrictness(Strictness.LENIENT)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES);

        /*
         *  Хэндлеры имеют более высоий приоритет (в сравнении с ExclusionStrategy), т.е. будут переписывать стереотипичное
         * поведение -- осторожнее с их добавлением. Готовые примеры описаны в конце класса.
         */

        putClassSuperHandler(gsonBuilder, MultipartFile.class, o -> MapParser.forArray(
                "Загружаемый файл", ((MultipartFile) o).getOriginalFilename(),
                "Размер", ((MultipartFile) o).getSize() + " байт"
        ));

    }

    @Override
    public Gson get() {
        return gsonBuilder.create();
    }

    public static boolean hasHandler(Object obj) {

        boolean contains = handledClasses.contains( obj.getClass() ) ||
                handledSuperClasses.stream().anyMatch(clazz -> clazz.isInstance(obj));

        if (!contains)
            log.warn("Missing handler implementation for class: {}", obj.getClass().getSimpleName());

        return contains;
    }

    // ===================================

    private static void putClassHandler(GsonBuilder builder, Class<?> clazz, MapParser extractor) {
        handledClasses.add(clazz);
        builder.registerTypeAdapter(clazz, makeAdapterUsingMapParser(extractor));
    }

    private static void putClassSuperHandler(GsonBuilder builder, Class<?> clazz, MapParser extractor) {
        handledSuperClasses.add(clazz);
        builder.registerTypeHierarchyAdapter(clazz, makeAdapterUsingMapParser(extractor));
    }

    private static TypeAdapter<?> makeAdapterUsingMapParser(MapParser extractor) {

        return new TypeAdapter<Object>() {

            @Override
            public void write(JsonWriter jsonWriter, Object o) throws IOException {

                var map = extractor.parse(o);
                jsonWriter.beginObject();

                for (String key : map.asList())
                    jsonWriter.name( key ).value( map.get(key) );

                jsonWriter.endObject();
            }

            @Override
            public Object read(JsonReader jsonReader) throws IOException {
                return null;
            }
        };
    }

    private String tryToUnwrap(Object o) {

        if (o == null) {
            log.warn("Serializing field is null");
            return "N/A";
        }

        // Не раскрываем содержимое свободно, если не добавлено в сипсок адаптеров
        return hasHandler( o ) ?
                get().toJson(o) :
                o.getClass().getSimpleName();
    }

    private static String shortString(String fullString) {
        return fullString.substring(0, 12) + "...";
    }

    /*              Deprecated?

        gsonBuilder.addSerializationExclusionStrategy(new FieldExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {

                        boolean should = AuditConfiguration.shouldSkipField(fieldAttributes);

//                        return auditConfiguration.shouldSkipField(fieldAttributes);
                        return Objects.isNull( fieldAttributes.getAnnotation(AllowAudit.class) );
                    }
                })
                .serializeNulls()
                .setFormattingStyle(FormattingStyle.COMPACT).setStrictness(Strictness.LENIENT)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES);

//        putClassHandler(JwtRequestDto.class, o -> MapParser.forArray(
//                "Login", String.valueOf( ((JwtRequestDto) o).getLogin() ),
//                "Pass", MASKED_BARCODE
//        ));

//        putClassHandler(JwtResponseDto.class, o -> MapParser.forArray(
//                "Token", tryToUnwrap( ((JwtResponseDto) o).getToken() ),
//                "Status", tryToUnwrap( ((JwtResponseDto) o).getStatus() )
//        ));

        putClassHandler(JwtErrorDto.class, o -> MapParser.forArray(
                "Ошибка: ", ((JwtErrorDto) o).getError().getMessage()
        ));

//        putClassHandler(ResponseEntity.class, o -> MapParser.forArray(
//                "Status Code", String.valueOf( ((ResponseEntity<?>) o).getStatusCodeValue() ),
//                "Body", tryToUnwrap( ((ResponseEntity<?>) o).getBody() )
//        ));

        putClassHandler(Long.class, o -> MapParser.forArray(
                "id", ((Long) o).toString()
        ));

        putClassSuperHandler(Map.class, o -> {
            LinkedMap<String, String> map = new LinkedMap<>();
            ((Map) o).keySet().stream().filter(String.class::isInstance).forEach(key -> {

                var mp = (Map<String, Object>) o;
                var value = mp.get(key);

                if ( hasHandler(value) )
                    map.put( (String) key, tryToUnwrap(value) );
            });
            return map;
        });

        putClassSuperHandler(MultipartFile.class, o -> MapParser.forArray(
                "Загружаемый файл", ((MultipartFile) o).getOriginalFilename(),
                "Размер", ((MultipartFile) o).getSize() + " байт"
        ));

        putClassHandler(MultipartHttpServletRequest.class, o -> MapParser.forArray(
                "Загружаемый файл", ((MultipartHttpServletRequest) o).getRequestHeaders().toString()
        ));

        putClassHandler(HttpSession.class, o -> MapParser.forArray(
                "Сессия Http", ((HttpSession) o).getId()
        ));

        putClassHandler(HttpStatus.class, o -> MapParser.forArray(
                "Status", tryToUnwrap( ((HttpStatus) o).getReasonPhrase() )
        ));
     */
}
