package net.apollo1.musicproducts.product;

import net.apollo1.musicproducts.ComponentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static net.apollo1.musicproducts.ResourceLoader.loadResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Testcontainers
@TestInstance(PER_CLASS)
class FilterProductsComponentTest extends ComponentTest {

    public static final String CONTROLLER_PATH = "/products/filter";

    List<Arguments> filterParamsAndExpectedResponses() {
        return List.of(
                Arguments.of("?productTitle=Goat+Girl+MP3", "get-product-filtered-by-product-title"),
                Arguments.of("?storeName=Rough+Trade", "get-product-filtered-by-store-name"),
                Arguments.of("?productGroupTitle=Merch", "get-product-filtered-by-product-group-title"),
                Arguments.of("?productGroupReleaseDateFrom=2023-01-01&productGroupReleaseDateTo=2023-12-31", "get-product-filtered-by-product-group-release-date"),
                Arguments.of("?productReleaseDateFrom=2023-01-01&productReleaseDateTo=2023-12-31", "get-product-filtered-by-product-release-date"),
                Arguments.of("?storeName=Reckless+Records", "get-empty-product-list-filtered-by-unknown-store-name")
        );
    }

    @MethodSource("filterParamsAndExpectedResponses")
    @ParameterizedTest(name = "should filter Products based on query params")
    @Sql(scripts = "classpath:data/insert_products-for-filtering.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should return all products")
    void shouldFilterByProductTitle(String params, String responseFile) throws Exception {
        var request = createRequest(null);
        var response = template.exchange(CONTROLLER_PATH + params, HttpMethod.GET, request, String.class);

        var expected = loadResponse("products", responseFile);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.STRICT);
    }

}