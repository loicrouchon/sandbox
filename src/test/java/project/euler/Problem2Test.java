package project.euler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Problem2Test {

    @Test
    public void testProblem() {
        assertEquals(10, Problem2.exec(10));
        assertEquals(4613732, Problem2.exec(4000000));
    }
}
