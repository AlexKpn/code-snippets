package java.test.utils.keycloak;

import dasniko.testcontainers.keycloak.KeycloakContainer;

//todo: add links for dependencies

public class KeycloakTestContainer extends KeycloakContainer {

//    private static final String CONTEXT_PATH = "";
    private static final String IMAGE_NAME = "quay.io/keycloak/keycloak:19.0.3";
//    private static final String[] ENABLED_FEATURES = {};

    public KeycloakTestContainer() {
        super(IMAGE_NAME);

        withContextPath(CONTEXT_PATH);
        withReuse(true);
        withFeaturesEnabled(ENABLED_FEATURES);
    }
}
