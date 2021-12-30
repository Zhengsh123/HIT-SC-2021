package IntervalSet;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class StaticTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testEmptyIntervalSetEmpty() {
        assertEquals("expected empty() set to have no component",
                Collections.emptyMap(), IntervalSet.empty().intervals());
        assertEquals("expected empty() set to have no component",
                Collections.emptySet(), IntervalSet.<String>empty().labels());
    }
    @Test
    public void testEmptyMultiIntervalSetEmpty()throws Exception {
        assertEquals("expected empty() set to have no component",
                Collections.emptySet(), MultiIntervalSet.empty().labels());
        IntervalSet tempEmptySet=IntervalSet.empty();
        MultiIntervalSet emptySet=new CommonMultiIntervalSet(tempEmptySet);
        assertEquals("expected empty() set to have no component",
                Collections.emptySet(), MultiIntervalSet.empty().labels());
    }
}
