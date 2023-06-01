package java.test.utils.assertion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

/**
 * Junit5 Assertions utility class
 */
public final class AssertionsUtils {

    private AssertionsUtils() {
        throw new IllegalStateException("You should not instantiate Utility class");
    }

    public static <T extends Throwable> void assertThrows(final T expectedException, final Executable executable) {
        assertThrows(expectedException.getClass(), expectedException.getMessage(), executable);
    }

    public static <T extends Throwable> void assertThrows(final Class<T> expectedException, final String expectedMessage, final Executable executable) {
        final var thrownException = Assertions.assertThrows(expectedException, executable);
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }
}
