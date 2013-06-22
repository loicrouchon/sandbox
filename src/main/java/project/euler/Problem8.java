package project.euler;

public class Problem8 {

	public static int max5ConsecutiveDigitsProduct(String digits) {
		int maxProduct = 0;
		int length = digits.length();
		for (int i = 0; i < length; i++) {
			String fiveDigits = digits.substring(i, Math.min(i + 5, length));
			int product = 1;
			for (Character c : fiveDigits.toCharArray()) {
				product *= Integer.parseInt(c.toString());
			}
			if (product > maxProduct) {
				maxProduct = product;
			}
		}
		return maxProduct;
	}
}