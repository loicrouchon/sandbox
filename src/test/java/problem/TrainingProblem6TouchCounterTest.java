package problem;

public class TrainingProblem6TouchCounterTest extends TrainingProblem6Test {

	@Override
	protected <T> TrainingProblem6<T> createCacheManager(int capacity, int itemsTocleanUp) {
		return new TrainingProblem6TouchCounter<T>(capacity, itemsTocleanUp);
	}
}
