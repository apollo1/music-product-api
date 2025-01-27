package net.apollo1.musicproducts.product;

import net.apollo1.musicproducts.ComponentTest;
import net.apollo1.musicproducts.product.repository.ProductRepository;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static net.apollo1.musicproducts.ResourceLoader.loadResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Testcontainers
@TestInstance(PER_CLASS)
class ProductControllerComponentTest extends ComponentTest {

    public static final String CONTROLLER_PATH = "/products/";
    public static final String VALID_PRODUCT_ID = "15ac6b6c-e6e8-423c-b5a8-bf7d44f43e77";
    private static final UUID INVALID_PRODUCT_ID = UUID.fromString("832de4b7-4e96-420a-b485-a7ef96f0b1c1");

    @MockitoSpyBean
    private ProductRepository productRepository;

    @Test
    @DirtiesContext
    @Sql(scripts = "classpath:data/insert_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should return the product associated with the product ID supplied")
    void getProductForId() throws Exception {
        var request = createRequest(null);
        var response = template.exchange(CONTROLLER_PATH + VALID_PRODUCT_ID, HttpMethod.GET, request, String.class);

        var expected = loadResponse("products", "get-product-by-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.STRICT);
    }

    @Test
    @DirtiesContext
    @Sql(scripts = "classpath:data/insert_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Should receive a 404 with no content when the product is not found in the database")
    void shouldReturnNotFoundWhenProductIdIsNotPresentInDatabase() throws JSONException {
        var request = createRequest(null);
        var response = template.exchange(CONTROLLER_PATH + INVALID_PRODUCT_ID, HttpMethod.GET, request, String.class);

        var expected = loadResponse("products", "get-product-by-id-not-found");

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("Should receive an Internal Server Error Response when database throws exception")
    void shouldGetInternalServerErrorResponseWhenDatabaseBarfs() throws JSONException {
        when(productRepository.findById(INVALID_PRODUCT_ID)).thenThrow(new RuntimeException("NOPE"));
        var request = createRequest(null);
        var expectedResponse = """
                {
                    "error_code": "SERVER_ERROR",
                    "title": "Internal Server Error",
                    "message": "The server encountered an internal error was unable to complete the request."
                }
                """;

        var response = template.exchange(CONTROLLER_PATH + INVALID_PRODUCT_ID, HttpMethod.GET, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), true);
    }
}