package net.apollo1.musicproducts.product;

import net.apollo1.musicproducts.ComponentTest;
import net.apollo1.musicproducts.product.model.Product;
import net.apollo1.musicproducts.productgroup.model.ProductGroup;
import net.apollo1.musicproducts.store.model.Store;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
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

@Testcontainers
@TestInstance(PER_CLASS)
class SaveProductsComponentTest extends ComponentTest {
    public static final String CONTROLLER_PATH = "/products";

    @Test
    @Sql(scripts = "classpath:data/insert_stores_product_groups.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should save a new product")
    void shouldSaveProduct() throws Exception {
        var request = createRequest(aProductRequest());
        var response = template.exchange(CONTROLLER_PATH, HttpMethod.POST, request, String.class);

        var expected = loadResponse("products", "saved-product");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expected, response.getBody(), ignoreIdComparator());
    }

    @Test
    @DisplayName("should get Bad Request when Store does not exist")
    void shouldGetBadRequestWhenSavingProductAndStoreDoesNotExist() throws Exception {
        var request = createRequest(aProductRequest());
        var response = template.exchange(CONTROLLER_PATH, HttpMethod.POST, request, String.class);

        var expectedResponse = """
                {
                    "error_code": "BAD_REQUEST",
                    "title": "Store Not Found",
                    "message": "Store ID fe22ec46-383b-46a0-a7f0-470bc5977f57 not found"
                }
                """;

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.STRICT);
    }

    @Test
    @Sql(scripts = "classpath:data/insert_stores_product_groups.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should get Bad Request when Product Group does not exist")
    void shouldGetBadRequestWhenSavingProductAndProductGroupDoesNotExist() throws Exception {
        var invalidProductGroupId = UUID.fromString("345a4a9c-814d-46e8-b361-1216f52d6376");
        var productRequest = aProductRequest(invalidProductGroupId);
        var request = createRequest(productRequest);
        var response = template.exchange(CONTROLLER_PATH, HttpMethod.POST, request, String.class);

        var expectedResponse = """
                {
                    "error_code": "BAD_REQUEST",
                    "title": "Product Group Not Found",
                    "message": "Product Group ID 345a4a9c-814d-46e8-b361-1216f52d6376 not found"
                }
                """;

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.STRICT);
    }

    private static @NotNull CustomComparator ignoreIdComparator() {
        return new CustomComparator(JSONCompareMode.LENIENT,
                new Customization("id", (o1, o2) -> true));
    }

    private Product aProductRequest() {
        return aProductRequest(UUID.fromString("e38abc59-47ef-4610-96f1-fbdc0b2fd9b0"));
    }

    private Product aProductRequest(UUID productGroupId) {
        Store store = Store.builder()
                .id(UUID.fromString("fe22ec46-383b-46a0-a7f0-470bc5977f57"))
                .build();

        ProductGroup productGroup = ProductGroup.builder()
                .id(productGroupId)
                .build();

        return Product.builder()
                .title("Deep Tan Vinyl")
                .distribution("PHYSICAL")
                .format("VINYL")
                .priceGbp("24.99")
                .priceUsd("22.99")
                .priceEur("23.99")
                .productReleaseDate(LocalDate.of(2023, 11, 10))
                .store(store)
                .productGroup(productGroup)
                .build();
    }


}