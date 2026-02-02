package su.opencode.digsigcik.pvva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import su.opencode.digsigcik.pvva.enums.PvvResponseType;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PvvResponse {

    @JsonProperty("responseType")
    PvvResponseType responseType;

    @JsonProperty("subscriptionData")
    Map<String, String> subscriptionData;

    @JsonProperty("errorDetails")
    String errorDetails;

    /**
     * Полезное содержимое, непосредственные данные, которые перемещаются между контурами можно хранить тут.
     * <br>
     * Или, если нужно передать файл, сунь его в filePayload (ниже).
     * <br><br>
     * <b>Не рекомендуется возвращать и <i>jsonPayload</i>, и <i>filePayload</i> одновременно</b>.
     */
    @JsonProperty("jsonPayload")
    Map<String, Object> jsonPayload;

    /**
     * Полезное содержимое, непосредственные данные, которые перемещаются между контурами можно хранить тут.
     * <br><br>
     * <b>Не рекомендуется возвращать и <i>jsonPayload</i>, и <i>filePayload</i> одновременно</b>.
     */
    @JsonProperty("filePayload")
    byte[] filePayload;


    // =================================================================================================================
    // =================================================================================================================
    // =================================================================================================================

    /**
     * Возвращает мультипарт сущность с двумя полями:
     * <br>
     * {@code file} - содержимое файла, причина существования этого формата<br>
     * {@code metadata} - здесь представление PvvResponse
     * @return ответ типа {@code MULTIPART_FORM_DATA}.
     */
    public static ResponseEntity<MultiValueMap<String, Object>> multipartOkResponse(byte[] payload) {
        var jsonResp = PvvResponse.builder()
                .responseType(PvvResponseType.OK)
                .build();

        MultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();

        // JSON part
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        multipartBody.add("metadata", new HttpEntity<>(jsonResp, jsonHeaders));

        // File part
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        fileHeaders.setContentDispositionFormData("file", "data.zip");
        ByteArrayResource resource = new ByteArrayResource(payload) {
            @Override
            public String getFilename() {
                return "data.zip";
            }
        };
        multipartBody.add("file", new HttpEntity<>(resource, fileHeaders));

        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipartBody);
    }

    public static ResponseEntity<PvvResponse> jsonOkResponse(Map<String, Object> payload) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(okPvvResponse(payload));
    }

    public static PvvResponse okPvvResponse(Map<String, Object> payload) {
        return PvvResponse.builder()
                .responseType(PvvResponseType.OK)
                .jsonPayload(payload)
                .build();
    }

    public static PvvResponse failedPvvResponse(String errorDetails) {
        return PvvResponse.builder()
                .responseType(PvvResponseType.FAILED)
                .errorDetails(errorDetails)
                .build();
    }

    public static PvvResponse idleContinuePvvResponse(String errorDetails) {
        return PvvResponse.builder()
                .responseType(PvvResponseType.IDLE_CONTINUE)
                .errorDetails(errorDetails)
                .build();
    }
}
