package net.apollo1.musicproducts;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Lazy
@Configuration
public class TestConfig {

    @LocalServerPort
    private int port;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder()
            .rootUri(String.format("http://localhost:%s", port))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Bean
    public TestRestTemplate testRestTemplate(final RestTemplateBuilder builder) {
        return new TestRestTemplate(builder);
    }

}
