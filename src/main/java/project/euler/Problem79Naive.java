package project.euler;

public class Problem79Naive implements Problem79 {

	@Override
	public String shortestPasscode(String... codes) {
		StringBuilder passcode = new StringBuilder();
		for (String code : codes) {
			matchPasscode(passcode, code);
		}
		optimize(passcode, codes);
		return passcode.toString();
	}

	private void matchPasscode(StringBuilder passcode, String code) {
		int previousCharIndex = -1;
		char[] chars = code.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			Character c = chars[i];
			int indexOfC = passcode.indexOf(c.toString(), previousCharIndex);
			if (indexOfC == -1) {
				indexOfC = previousCharIndex + 1;
				if (i + 1 < chars.length && passcode.indexOf(Character.toString(chars[i + 1]), previousCharIndex) > -1) {
					passcode.insert(indexOfC, c);
				} else {
					passcode.append(c);
				}
			}
			previousCharIndex = indexOfC;
		}
	}

	private void optimize(StringBuilder passcode, String[] codes) {
		for (int i = 0; i < passcode.length(); i++) {
			Character c = passcode.charAt(i);
			passcode.deleteCharAt(i);
			boolean matchAllCodes = matchAllCodes(passcode, codes);
			if (!matchAllCodes) {
				passcode.insert(i, c);
			} else {
				i--;
			}
		}

	}

	private static boolean matchAllCodes(StringBuilder passcode, String[] codes) {
		for (String code : codes) {
			if (!match(passcode.toString(), code)) {
				return false;
			}
		}
		return true;
	}

	public static boolean match(String passcode, String code) {
		int previousCharIndex = -1;
		for (Character c : code.toCharArray()) {
			int indexOfC = passcode.indexOf(c.toString(), previousCharIndex);
			if (indexOfC == -1) {
				return false;
			}
			previousCharIndex = indexOfC;
		}
		return true;
	}
}