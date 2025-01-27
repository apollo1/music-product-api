package net.apollo1.musicproducts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComponentTest {

    @Autowired
    protected TestRestTemplate template;

    protected <T> HttpEntity<T> createRequest(T body) {
        var headers = new HttpHeaders();
        return new HttpEntity<>(body, headers);
    }
}
