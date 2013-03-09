package project.euler;

public class Problem4 {

    public static int exec(int maxI, int maxJ) {
        int maxResult = 1;
        for (int i = maxI; i > 0; i--) {
            for (int j = maxJ; j > 0; j--) {
                int result = i * j;
                if (result > maxResult && isPalindrome(result)) {
                    maxResult = result;
                    break;
                }
            }
        }
        return maxResult;
    }

    public static boolean isPalindrome(int n) {
        String str = Integer.toString(n);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!(str.charAt(i) == str.charAt(length - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}