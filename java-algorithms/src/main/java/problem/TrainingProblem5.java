package problem;

import java.util.List;

public interface TrainingProblem5<T> {

	/**
	 * 
	 * Given two linked lists, return the intersection of the two lists: i.e.
	 * return a list containing only
	 * 
	 * the elements that occur in both of the input lists.
	 */
	List<T> intersection(List<T> l1, List<T> l2);

}