package java.test.utils.time;

import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

//todo: add links for dependencies
public final class TimeTestUtils {

    private TimeTestUtils() {
        throw new IllegalStateException("You should not instantiate Utility class");
    }

    public static Clock getClock(final List<String> expectedOrderedTimeList) {
        final var clock = Mockito.mock(Clock.class);

        var instants = expectedOrderedTimeList
                .stream()
                .map(Instant::parse)
                .toArray(Instant[]::new);
        Mockito.when(clock.instant()).thenReturn(instants[0], Arrays.copyOfRange(instants, 1, instants.length));

        return clock;
    }
}
