package java.test.utils.base.classes;

import org.junit.jupiter.api.BeforeEach;

/**
 * T - the type of the class under test
 */

@ExtendWith(MockitoExtension.class)
public abstract class AbstractUnitTest<T> {

    protected T underTest;

    @BeforeEach
    void beforeEach() {
        underTest = createUnderTestObject();
    }

    protected abstract T createUnderTestObject();

}
