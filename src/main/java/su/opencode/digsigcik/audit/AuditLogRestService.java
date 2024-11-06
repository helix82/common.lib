package su.opencode.digsigcik.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Slf4j
public class AuditLogRestService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final MiniClient client = new MiniClient();

    @Value("${audit.url}")
    private URI auditEndpointUrl;


    @SneakyThrows(JsonProcessingException.class)
    public void sendLogEvent(AuditLogEvent auditLogEvent) {

        String json = mapper.writeValueAsString(auditLogEvent);
        log.debug("Sending event: {}", json);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String bearer = (authentication instanceof JwtAuthenticationToken)
                ? ((JwtAuthenticationToken) authentication).getToken().getTokenValue()
                : null;

        try {
            // TODO: добавить обработку кодов (Данияр)
            client.request(
                    MiniClient.RestTemplateClientRequest
                            .builder()
                            .httpMethod(HttpMethod.POST)
                            .requestUri(auditEndpointUrl)
                            .requestContent(json)
                            .bearerToken(bearer)
                            .httpHeader(Map.of("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE))
                            .build()
            );
        } catch (IOException | HttpClientErrorException ex) {
            log.error(ex.getMessage());
        }
    }
}
