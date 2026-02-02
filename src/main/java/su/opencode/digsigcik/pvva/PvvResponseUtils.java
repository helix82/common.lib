package su.opencode.digsigcik.pvva;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import su.opencode.digsigcik.pvva.enums.PvvResponseType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

@UtilityClass
public class PvvResponseUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ResponseEntity<?> passingThroughJsonResponse(ResponseEntity<? extends PvvResponse> pvvResponseResponseEntity) {
        return genericSwaggerResponse(pvvResponseResponseEntity.getBody(), pvvResponseResponseEntity.getStatusCode(), PvvResponse::getJsonPayload);
    }

    public static ResponseEntity<?> genericJsonSwaggerResponse(ResponseEntity<? extends PvvResponse> pvvResponseResponseEntity) {
        return genericJsonSwaggerResponse(pvvResponseResponseEntity.getBody());
    }

    public static ResponseEntity<?> genericJsonSwaggerResponse(PvvResponse pvvResponse) {
        return genericSwaggerResponse(pvvResponse, HttpStatus.BAD_REQUEST, PvvResponse::getJsonPayload);
    }

    public static ResponseEntity<?> genericSwaggerResponse(PvvResponse pvvResponse, HttpStatus defaultOnErrorStatus, Function<PvvResponse, Object> getPayload) {
        switch (pvvResponse.getResponseType()) {
            case OK:
            case OK_SUBSCRIPTION_REQUIRED:
                return ResponseEntity.ok(getPayload.apply(pvvResponse));
            case IDLE_CONTINUE:
                return ResponseEntity.ok(Map.of("details", "Ожидайте прохождения операции (5-10 минут)"));
            default:
                return ResponseEntity.status(defaultOnErrorStatus).body(Map.of("details", pvvResponse.getErrorDetails()));
        }
    }

    public static ResponseEntity<?> genericFileSwaggerResponse(ResponseEntity<? extends PvvResponse> pvvResponseResponseEntity) {
        return genericFileSwaggerResponse(pvvResponseResponseEntity.getBody());
    }

    public static ResponseEntity<?> genericFileSwaggerResponse(PvvResponse pvvResponse) {
        return genericSwaggerResponse(pvvResponse, HttpStatus.BAD_REQUEST, PvvResponse::getFilePayload);
    }

    public static ResponseEntity<PvvResponse> internalServerError(Exception cause) {
        return ofException(cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static ResponseEntity<PvvResponse> badRequestError(Exception cause) {
        return ofException(cause, HttpStatus.BAD_REQUEST.value());
    }

    public static ResponseEntity<PvvResponse> ofException(Exception cause) {

        int statusCode = cause instanceof HttpClientErrorException
                ? ((HttpClientErrorException) cause).getRawStatusCode()
                : HttpStatus.INTERNAL_SERVER_ERROR.value();

        return ofException(cause, statusCode);
    }

    public static ResponseEntity<PvvResponse> ofException(Exception cause, int statusCode) {
        return ResponseEntity.status(statusCode).body(
                PvvResponse.builder().responseType(PvvResponseType.FAILED).errorDetails(cause.getMessage()).build()
        );
    }

    @SneakyThrows
    public static Map<String, Object> saveload(Object object) {
        return objectMapper.readValue(objectMapper.writeValueAsString(object), HashMap.class);
    }

    public static PvvResponse parseForPvvResponse(byte[] multipartResponse) throws IOException {

        Map<String, Object> multipart = parseMultipartResponseString(new String(multipartResponse));

        PvvResponse response = multipart.containsKey("metadata")
                ? objectMapper.convertValue(multipart.get("metadata"), PvvResponse.class)
                : new PvvResponse();

        response.setFilePayload(((byte[]) multipart.getOrDefault("file", null)));
        return response;
    }

    public static Map<String, Object> parseMultipartResponseString(String rawResponse) throws IOException {

        Map<String, Object> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        // Split by boundary (first line)
        String[] lines = rawResponse.split("\\r?\\n");
        if (lines.length < 2) return result;

        String boundary = lines[0].substring(2); // Remove "--"

        // Join all lines back
        String full = String.join("\n", Arrays.copyOfRange(lines, 1, lines.length));

        // Split by boundary
        String[] parts = full.split("--" + Pattern.quote(boundary));

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty() || part.equals("--")) continue;

            int headerEnd = part.indexOf("\n\n");
            if (headerEnd == -1) continue;

            String headers = part.substring(0, headerEnd);
            String body = part.substring(headerEnd + 2);

            if (headers.contains("name=\"metadata\"")) {
                PvvResponse metadata = mapper.readValue(body.trim(), PvvResponse.class);
                result.put("metadata", metadata);
            } else if (headers.contains("name=\"file\"")) {
                result.put("file", body.getBytes(StandardCharsets.UTF_8));
            }
        }

        return result;
    }
}
