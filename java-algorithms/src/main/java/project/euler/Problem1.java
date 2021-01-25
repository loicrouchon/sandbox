package project.euler;

public class Problem1 {

    public static int sum3or5multiplesBelow(int n) {
        int result = 0;
        for (int i = 3; i < n; i++) {
            if (i % 3 == 0) {
                result += i;
            } else if (i % 5 == 0) {
                result += i;
            }
        }
        return result;
    }
}