package net.apollo1.musicproducts.productgroup;

import net.apollo1.musicproducts.ComponentTest;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Testcontainers
@TestInstance(PER_CLASS)
class ProductGroupComponentTest extends ComponentTest {

    List<Arguments> unimplementedProductGroupsEndpoints() {
        return List.of(
                Arguments.of(Named.of("get Product Group Given Product Group ID", "/productgroups/" + UUID.randomUUID())),
                Arguments.of(Named.of("Get All Product Groups", "/productgroups"))
        );
    }

    @MethodSource("unimplementedProductGroupsEndpoints")
    @ParameterizedTest(name = "should return 501 NOT_IMPLEMENTED for unimplemented stores endpoints")
    void shouldGet501WhenGettingProductGroups(String controllerPath) {
        var request = createRequest(null);
        var response = template.exchange(controllerPath, HttpMethod.GET, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
    }

}