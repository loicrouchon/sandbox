package project.euler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Problem1Test {

    @Test
    public void testSum3or5multiplesBelow() {
        assertEquals(0, Problem1.sum3or5multiplesBelow(2));
        assertEquals(23, Problem1.sum3or5multiplesBelow(10));
        assertEquals(233168, Problem1.sum3or5multiplesBelow(1000));
    }
}
