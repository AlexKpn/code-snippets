package java.test.utils.it;

import com.akelius.university.secret.code.test.util.mapping.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.test.utils.base.classes.WithRunningServerIntegrationTest;

import java.net.URI;
import java.test.utils.json.JsonUtils;
import java.util.stream.Stream;

class ActuatorIntegrationTest extends WithRunningServerIntegrationTest {

    @ParameterizedTest
    @MethodSource("provideExposedActuatorEndpointsWithoutAuth")
    void testBaseEndpointsWithoutAuth(final String endpoint, final HttpStatus expectedHttpStatus) {
        //given
        final URI uri = prepareUri(endpoint);

        //when
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(uri, String.class);

        //then
        Assertions.assertEquals(expectedHttpStatus, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("provideProbeEndpoints")
    void testProbeEndpointShouldReturnUp(final String endpoint) throws JsonProcessingException {
        //given
        final URI uri = prepareUri(endpoint);

        //when
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(uri, String.class);

        //then
        final JsonNode responseJsonNode = JsonUtils.toJsonNode(responseEntity.getBody(), objectMapper);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("UP", responseJsonNode.get("status").asText());
    }

    private static Stream<Arguments> provideExposedActuatorEndpointsWithoutAuth() {
        return Stream.of(
                Arguments.of(ActuatorEndpoints.INFO_ENDPOINT, HttpStatus.OK),
                Arguments.of(ActuatorEndpoints.PROMETHEUS_ENDPOINT, HttpStatus.OK),
                Arguments.of(ActuatorEndpoints.HEALTH_ENDPOINT, HttpStatus.OK),
                Arguments.of(ActuatorEndpoints.LOGGERS_ENDPOINT, HttpStatus.UNAUTHORIZED),
                Arguments.of(ActuatorEndpoints.ENV_ENDPOINT, HttpStatus.UNAUTHORIZED),
                Arguments.of(ActuatorEndpoints.METRICS_ENDPOINT, HttpStatus.UNAUTHORIZED)
        );
    }

    private static Stream<Arguments> provideProbeEndpoints() {
        return Stream.of(
                Arguments.of(ActuatorEndpoints.ProbeEndpoints.READINESS_PROBE_ENDPOINT),
                Arguments.of(ActuatorEndpoints.ProbeEndpoints.LIVENESS_PROBE_ENDPOINT)
        );
    }

    private URI prepareUri(final String endpoint) {
        final var basePath = "/actuator";
        return prepareUri(basePath, endpoint);
    }

    private static class ActuatorEndpoints {
        static final String ENV_ENDPOINT = "/env";
        static final String HEALTH_ENDPOINT = "/health";
        static final String INFO_ENDPOINT = "/info";
        static final String LOGGERS_ENDPOINT = "/loggers";
        static final String METRICS_ENDPOINT = "/metrics";
        static final String PROMETHEUS_ENDPOINT = "/prometheus";

        static class ProbeEndpoints {
            static final String LIVENESS_PROBE_ENDPOINT = ActuatorEndpoints.HEALTH_ENDPOINT + "/liveness";
            static final String READINESS_PROBE_ENDPOINT = ActuatorEndpoints.HEALTH_ENDPOINT + "/readiness";
        }
    }
}
