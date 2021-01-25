package problem;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TrainingProblem5N<T> implements TrainingProblem5<T> {

	@Override
	public List<T> intersection(List<T> l1, List<T> l2) {
		List<T> intersection = new LinkedList<>();
		Set<T> s2 = new HashSet<>(l2);
		for (T object : l1) {
			if (s2.contains(object)) {
				intersection.add(object);
			}
		}
		return intersection;
	}
}