package net.apollo1.musicproducts.product;

import net.apollo1.musicproducts.ComponentTest;
import net.apollo1.musicproducts.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Testcontainers
@TestInstance(PER_CLASS)
class DeleteProductComponentTest extends ComponentTest {
    public static final String CONTROLLER_PATH = "/products/";
    public static final String VALID_PRODUCT_ID = "15ac6b6c-e6e8-423c-b5a8-bf7d44f43e77";

    @MockitoSpyBean
    ProductRepository productRepository;

    @Test
    @Sql(scripts = "classpath:data/insert_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:data/cleanup_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("should delete a product given an ID")
    void shouldDeleteProduct() {
        var request = createRequest(null);
        var response = template.exchange(CONTROLLER_PATH + VALID_PRODUCT_ID, HttpMethod.DELETE, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productRepository.findById(UUID.fromString(VALID_PRODUCT_ID))).isNotPresent();
    }

}