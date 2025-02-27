package su.opencode.digsigcik.audit;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class MiniClient {

    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String RESPONSE_MIME_TYPE = "responseMimeType";
    public static final String USER_MIME_TYPE = "userMimeType";
    public static final String UPSTREAM = "upstream";
    public static final String UPLOAD = "upload";
    public static final String UPLOAD_FILE = "upfile";
    public static final String TEXT_XML = "text/xml";

    private RestTemplate restTemplate;
    private HttpHeaders sessionHeaders = new HttpHeaders();

    public MiniClient() {
        this(0);
    }

    public MiniClient(int timeoutMillis) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(timeoutMillis);
        requestFactory.setReadTimeout(timeoutMillis);
        this.restTemplate = new RestTemplate(requestFactory);
        configureRestTemplate();
    }

    private void configureRestTemplate() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        restTemplate.setUriTemplateHandler(factory);
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().putAll(sessionHeaders);
            ClientHttpResponse response = execution.execute(request, body);
            if (response.getHeaders().containsKey(HttpHeaders.SET_COOKIE)) {
                sessionHeaders.add(HttpHeaders.COOKIE, String.join(";", response.getHeaders().get(HttpHeaders.SET_COOKIE)));
            }
            return response;
        }));
    }

//    public String requestString(@NonNull final RestTemplateClientRequest restTemplateClientRequest) throws IOException {
//        HttpEntity<?> requestEntity = populateRequest(restTemplateClientRequest);
//        ResponseEntity<String> response = restTemplate.exchange(restTemplateClientRequest.getRequestUri(), restTemplateClientRequest.getHttpMethod(), requestEntity, String.class);
//        return response.getBody();
//    }

//    public JsonObject requestJson(@NonNull final RestTemplateClientRequest restTemplateClientRequest) throws IOException, HttpClientErrorException {
//        HttpEntity<?> requestEntity = populateRequest(restTemplateClientRequest);
//        ResponseEntity<String> response = restTemplate.exchange(restTemplateClientRequest.getRequestUri(), restTemplateClientRequest.getHttpMethod(), requestEntity, String.class);
//        return new Gson().fromJson(response.getBody(), JsonObject.class);
//    }

    public HttpStatus request(@NonNull final RestTemplateClientRequest restTemplateClientRequest) throws IOException, HttpClientErrorException {
        HttpEntity<?> requestEntity = populateRequest(restTemplateClientRequest);
        ResponseEntity<String> response = restTemplate.exchange(restTemplateClientRequest.getRequestUri(), restTemplateClientRequest.getHttpMethod(), requestEntity, String.class);
        return response.getStatusCode();
    }

    private HttpEntity<?> populateRequest(@NonNull RestTemplateClientRequest restTemplateClientRequest) throws IOException {

        HttpHeaders headers = new HttpHeaders();

        if (restTemplateClientRequest.getHttpHeader() != null) {
            headers.setAll(restTemplateClientRequest.getHttpHeader());
        }

        if (restTemplateClientRequest.getBearerToken() != null)
            headers.setBearerAuth(restTemplateClientRequest.getBearerToken());

        if (restTemplateClientRequest.getRequestContent() != null) {
            return new HttpEntity<>(restTemplateClientRequest.getRequestContent(), headers);
        } else if (restTemplateClientRequest.getRequestForm() != null) {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            restTemplateClientRequest.getRequestForm().forEach(params::add);
            return new HttpEntity<>(params, headers);
        } else if (restTemplateClientRequest.getFile() != null) {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add(UPLOAD, restTemplateClientRequest.getFile());
            String mimeType = Optional.ofNullable(Files.probeContentType(restTemplateClientRequest.getFile().toPath()))
                    .orElse(APPLICATION_OCTET_STREAM);
            body.add(USER_MIME_TYPE, mimeType);
            body.add(RESPONSE_MIME_TYPE, TEXT_XML);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            return new HttpEntity<>(body, headers);
        } else if (restTemplateClientRequest.getInputStream() != null) {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add(UPSTREAM, restTemplateClientRequest.getInputStream());
            body.add(USER_MIME_TYPE, restTemplateClientRequest.getMimeType());
            body.add(RESPONSE_MIME_TYPE, TEXT_XML);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            return new HttpEntity<>(body, headers);
        } else if (restTemplateClientRequest.getBytes() != null) {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("document", new ByteArrayResource(restTemplateClientRequest.getBytes()) {
                @Override
                public String getFilename() {
                    return restTemplateClientRequest.getFileName();
                }
            });
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            return new HttpEntity<>(body, headers);
        }

        return new HttpEntity<>(headers);
    }

    @Builder
    @Data
    public static class RestTemplateClientRequest {
        @NonNull
        HttpMethod httpMethod;
        @NonNull
        URI requestUri;
        Map<String, String> httpHeader;
        String requestContent;
        File file;
        String fileName;
        String bearerToken;
        InputStream inputStream;
        byte[] bytes;
        String mimeType;
        Map<String, String> requestForm;
    }
}
