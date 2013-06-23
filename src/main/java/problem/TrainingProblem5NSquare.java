package problem;

import java.util.LinkedList;
import java.util.List;

public class TrainingProblem5NSquare<T> implements TrainingProblem5<T> {

	@Override
	public List<T> intersection(List<T> l1, List<T> l2) {
		List<T> intersection = new LinkedList<>();
		for (T object : l1) {
			if (l2.contains(object)) {
				intersection.add(object);
			}
		}
		return intersection;
	}
}