package problem;


public class TrainingProblem9 {
	/**
	 * Write a function (with helper functions if needed) called to Excel that
	 * takes an excel column value (A,B,C,D…AA,AB,AC,… AAA..) and returns a
	 * corresponding integer value (A=1,B=2,… AA=27...)
	 */
	public int columnNumber(String columnName) {
		int columnNumber = 0;
		char[] chars = columnName.toCharArray();
		for (char c : chars){
			columnNumber = columnNumber * 26 + Character.digit(Character.toLowerCase(c), 36) - 9;
		}
		return columnNumber;
	}
}
