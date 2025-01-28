package net.apollo1.musicproducts.product;

import net.apollo1.musicproducts.ComponentTest;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.productgroup.model.ProductGroup;
import net.apollo1.musicproducts.store.model.Store;
import org.json.JSONException;
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

import java.time.LocalDate;
import java.util.UUID;

import static net.apollo1.musicproducts.ResourceLoader.loadResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Testcontainers
@TestInstance(PER_CLASS)
class UpdateProductsComponentTest extends ComponentTest {
    public static final String CONTROLLER_PATH = "/products/";
    public static final String VALID_PRODUCT_ID = "15ac6b6c-e6e8-423c-b5a8-bf7d44f43e77";
    public static final String INVALID_PRODUCT_ID = "31486a80-6c0f-4917-a29b-1bdd0932cf51";

    @Test
    @Sql(scripts = "classpath:data/insert_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should update an existing product")
    void shouldUpdateExistingProduct() throws Exception {
        var request = createRequest(aProductRequest());
        var response = template.exchange(CONTROLLER_PATH + VALID_PRODUCT_ID, HttpMethod.PUT, request, String.class);

        var expected = loadResponse("products", "updated-product");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("Should receive a 404 when the product is not found in the database")
    void shouldReturnNotFoundWhenProductIdIsNotPresentInDatabase() throws JSONException {
        var request = createRequest(aProductRequest());
        var response = template.exchange(CONTROLLER_PATH + INVALID_PRODUCT_ID, HttpMethod.PUT, request, String.class);

        var expectedResponse = """
                {
                    "error_code": "PRODUCT_NOT_FOUND",
                    "title": "Not Found",
                    "message": "Product ID 31486a80-6c0f-4917-a29b-1bdd0932cf51 not found"
                }
                """;

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.STRICT);
    }

    private Product aProductRequest() {
        return aProductRequest(UUID.fromString("e38abc59-47ef-4610-96f1-fbdc0b2fd9b0"));
    }

    private Product aProductRequest(UUID productGroupId) {
        Store store = Store.builder()
                .id(UUID.fromString("023e4795-5f59-4650-bd86-d7990803759c"))
                .build();

        ProductGroup productGroup = ProductGroup.builder()
                .id(productGroupId)
                .title("Music")
                .releaseDate(LocalDate.of(2021, 8, 7))
                .build();

        return Product.builder()
                .id(UUID.fromString(VALID_PRODUCT_ID))
                .title("Chelsea Wolfe CD + Booklet")
                .distribution("PHYSICAL")
                .format("CD")
                .priceGbp("19.99")
                .priceUsd("17.99")
                .priceEur("18.99")
                .productReleaseDate(LocalDate.of(2024, 3, 15))
                .store(store)
                .productGroup(productGroup)
                .build();
    }


}