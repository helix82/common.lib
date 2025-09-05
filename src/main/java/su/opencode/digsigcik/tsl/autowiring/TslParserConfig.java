package su.opencode.digsigcik.tsl.autowiring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import su.opencode.digsigcik.tsl.service.TslParser;
import su.opencode.digsigcik.tsl.service.TslParserImpl;

@Configuration
public class TslParserConfig {
    @Bean
    public TslParser tslParser() {
        return new TslParserImpl();
    }
}
