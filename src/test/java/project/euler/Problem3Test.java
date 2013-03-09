package project.euler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Problem3Test {

    @Test
    public void testProblem() {
        assertEquals(29, Problem3.exec(13195));
        assertEquals(6857, Problem3.exec(600851475143L));
    }
}
