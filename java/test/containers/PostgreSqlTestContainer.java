package java.test.containers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSqlTestContainer extends PostgreSQLContainer<PostgreSqlTestContainer> {

    private static final String IMAGE_NAME = "postgres:11.18-alpine";

    public PostgreSqlTestContainer() {
        super(IMAGE_NAME);

        setCommand("postgres", "-c", "max_connections=20000");
    }

}
