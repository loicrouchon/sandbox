package problem;

public class TrainingProblem6SortedLinkedListTest extends TrainingProblem6Test {

	@Override
	protected <T> TrainingProblem6<T> createCacheManager(int capacity, int itemsTocleanUp) {
		return new TrainingProblem6SortedLinkedList<T>(capacity, itemsTocleanUp);
	}
}
