package net.apollo1.musicproducts.product;

import net.apollo1.musicproducts.ComponentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import static net.apollo1.musicproducts.ResourceLoader.loadResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Testcontainers
@TestInstance(PER_CLASS)
class GetAllProductsComponentTest extends ComponentTest {

    public static final String CONTROLLER_PATH = "/products";

    @Test
    @Sql(scripts = "classpath:data/insert_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should return all products")
    void shouldGetProducts() throws Exception {
        var request = createRequest(null);
        var response = template.exchange(CONTROLLER_PATH, HttpMethod.GET, request, String.class);

        var expected = loadResponse("products", "get-all-products");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("should return empty collection when there are no products")
    void shouldGetProductsWhenThereAreNone() throws Exception {
        var request = createRequest(null);
        var response = template.exchange(CONTROLLER_PATH, HttpMethod.GET, request, String.class);

        var expected = loadResponse("products", "get-all-products-empty");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.STRICT);
    }
}