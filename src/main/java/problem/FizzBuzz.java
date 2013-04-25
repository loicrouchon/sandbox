package problem;

public class FizzBuzz {

	public String[] fizzBuzz(int n) {
		String[] results = new String[n];
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0) {
				if (i % 5 == 0) {
					results[i - 1] = "Fizz Buzz";
				} else {
					results[i - 1] = "Fizz";
				}
			} else if (i % 5 == 0) {
				results[i - 1] = "Buzz";
			} else {
				results[i - 1] = Integer.toString(i);
			}
		}
		return results;
	}
}
