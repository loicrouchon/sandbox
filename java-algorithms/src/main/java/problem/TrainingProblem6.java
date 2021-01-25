package problem;

public interface TrainingProblem6<T> {

	public abstract T get(String key);

	public abstract void put(String key, T item);

	public abstract int size();

}