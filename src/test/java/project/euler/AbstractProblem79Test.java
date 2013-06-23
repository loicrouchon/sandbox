package project.euler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractProblem79Test {

	private final class Match extends BaseMatcher<String> {

		private final String passcode;
		private final String[] codes;
		private final List<String> notMatchingCodes = new ArrayList<>();

		public Match(String passcode, String[] codes) {
			this.passcode = passcode;
			this.codes = codes;
		}

		@Override
		public boolean matches(Object item) {
			boolean isExpectedPasscode = is(passcode).matches(item);
			boolean areCodeMatching = true;
			for (String code : codes) {
				if (!Problem79Naive.match((String) item, code)) {
					notMatchingCodes.add(code);
					areCodeMatching = false;
				}
			}
			return isExpectedPasscode && areCodeMatching;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(passcode);
			if (!notMatchingCodes.isEmpty()) {
				description.appendText(" following codes are not matching: " + notMatchingCodes);
			}
		}
	}

	private Problem79 problem79;

	@Before
	public void setUp() {
		problem79 = create();
	}

	abstract protected Problem79 create();

	@Test
	public void testProblem() {
		String[] codes;
		codes = new String[] { "123", "456", "345", "234" };
		assertThat(problem79.shortestPasscode(codes), match("123456", codes));

		codes = new String[] { "319", "680", "180", "690", "129", "620", "762", "689", "762", "318", "368", "710",
		        "720", "710", "629", "168", "160", "689", "716", "731", "736", "729", "316", "729", "729", "710",
		        "769", "290", "719", "680", "318", "389", "162", "289", "162", "718", "729", "319", "790", "680",
		        "890", "362", "319", "760", "316", "729", "380", "319", "728", "716" };
		assertThat(problem79.shortestPasscode(codes), match("73162890", codes));
	}

	private Match match(String passcode, String[] codes) {
		return new Match(passcode, codes);
	}
}
