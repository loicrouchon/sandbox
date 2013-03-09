package project.euler;

public class Problem3 {

    public static long exec(long n) {
        long largestPrimeFactor = 1; // Not prime
        if (n % 2 == 0) {
            largestPrimeFactor = 2;
        }
        long upperBound = n;
        for (long i = 3; i <= upperBound; i += 2) {
            if (isPrime(i) && upperBound % i == 0) {
                largestPrimeFactor = i;
                upperBound /= largestPrimeFactor;
            }
        }
        return largestPrimeFactor;
    }

    private static boolean isPrime(long n) {
        long squareRootOfN = (long) Math.sqrt(n);
        if (n % 2 == 0) {
            return false;
        }
        for (long i = 3; i <= squareRootOfN; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}