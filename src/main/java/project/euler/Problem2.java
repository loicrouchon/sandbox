package project.euler;

public class Problem2 {

    public static int exec(int n) {
        int result = 0;
        int previousNb = 0;
        int currentNb = 1;
        while (currentNb < n) {
            if (currentNb % 2 == 0) {
                result += currentNb;
            }
            int temp = currentNb;
            currentNb = currentNb + previousNb;
            previousNb = temp;

        }
        return result;
    }
}