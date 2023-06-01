package java.test.utils.it;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.test.utils.base.classes.WithRunningServerIntegrationTest;


import java.net.URI;
import java.util.stream.Stream;

public class SwaggerIntegrationTest extends WithRunningServerIntegrationTest {

    @ParameterizedTest
    @MethodSource("provideEndpoints")
    void testEndpointsWithoutAuth(final String endpoint, final HttpStatus expectedHttpStatus) {
        //given
        final URI uri = prepareUri(endpoint);

        //when
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(uri, null);

        //then
        Assertions.assertEquals(expectedHttpStatus, responseEntity.getStatusCode());
    }

    private static Stream<Arguments> provideEndpoints() {
        return Stream.of(
                Arguments.of(SwaggerEndpoints.SWAGGER_UI_INDEX_ENDPOINT_1, HttpStatus.OK),
                Arguments.of(SwaggerEndpoints.SWAGGER_UI_INDEX_ENDPOINT_2, HttpStatus.OK),
                Arguments.of(SwaggerEndpoints.OPEN_API_DEFINITION_ENDPOINT, HttpStatus.OK)
        );
    }

    private URI prepareUri(final String endpoint) {
        final var basePath = "";
        return prepareUri(basePath, endpoint);
    }

    private static class SwaggerEndpoints {
        private static final String SWAGGER_UI_INDEX_ENDPOINT_1 = "/swagger-ui.html";
        private static final String SWAGGER_UI_INDEX_ENDPOINT_2 = "/swagger-ui/index.html";
        private static final String OPEN_API_DEFINITION_ENDPOINT = "/v3/api-docs";
    }
}
