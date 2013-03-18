package problem;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TrainingProblem1Test {

    private TrainingProblem1 trainingProblem1;

    @Before
    public void setUp() {
        trainingProblem1 = new TrainingProblem1();
    }

    @Test
    public void testCommonChars() {
        testCommonChars("", "", "");
        testCommonChars("", "a", "");
        testCommonChars("", "", "a");
        testCommonChars("a", "a", "a");
        testCommonChars("a", "a", "ab");
        testCommonChars("a", "ab", "a");
        testCommonChars("ab", "ab", "ab");
        testCommonChars("ab", "ab", "ba");
        testCommonChars("ba", "ba", "ab");
        testCommonChars("ba", "ba", "ba");
    }

    private void testCommonChars(String expected, String a, String b) {
        assertEquals(expected, trainingProblem1.commonCharsNSquared(a, b));
        assertEquals(expected, trainingProblem1.commonCharsN(a, b));
    }
}
