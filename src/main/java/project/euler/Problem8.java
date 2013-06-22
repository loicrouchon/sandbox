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
	// public static int max5ConsecutiveDigitsProduct(String digitsString) {
	// int maxProduct = 1;
	// int product = 1;
	// char[] digits = digitsString.toCharArray();
	// Deque<Integer> previousDigits = new ArrayDeque<Integer>(5);
	// for (int i = 0; i < Math.max(5, digits.length); i++) {
	// int oldestDigit = 1;
	// product = computeCurrentProduct(product, digits, i, oldestDigit,
	// previousDigits);
	// if (product > maxProduct) {
	// maxProduct = product;
	// }
	// }
	// for (int i = 5; i < digits.length; i++) {
	// int oldestDigit = previousDigits.pop();
	// product = computeCurrentProduct(product, digits, i, oldestDigit,
	// previousDigits);
	// if (product > maxProduct) {
	// maxProduct = product;
	// }
	// }
	// return maxProduct;
	// }
	//
	// private static int computeCurrentProduct(int product, char[] digits, int
	// i, int oldestDigit, Deque<Integer> previousDigits) {
	// int digit = Integer.parseInt(Character.toString(digits[i]));
	// previousDigits.addLast(digit);
	// product = product / oldestDigit * digit;
	// return product;
	// }
}