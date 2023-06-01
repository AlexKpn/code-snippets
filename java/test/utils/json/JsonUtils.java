package java.test.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {
    private JsonUtils() {
        throw new IllegalStateException();
    }

    public static JsonNode toJsonNode(final String json, final ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.readValue(json, JsonNode.class);
    }
}
