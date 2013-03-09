package project.euler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Problem4Test {

    @Test
    public void testProblem() {
        assertEquals(9009, Problem4.exec(99, 99));
        assertEquals(906609, Problem4.exec(999, 999));
        assertEquals(99000099, Problem4.exec(9999, 9999));
    }
}
