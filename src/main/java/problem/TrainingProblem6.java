package problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Create a fast cache d storage mechanism that, given a limitation on the
 * amount of cache memory, will ensure that only the least recently used items
 * are discarded when the cache memory is reached when inserting a new item. It
 * supports 2 functions: String get(T t) and void put(String k, T t).
 */
public class TrainingProblem6<T> {

	private static final double LOAD_FACTOR = 1.33;

	private final Comparator<Entry<String, Holder<T>>> AGE_COMPARATOR = new Comparator<Entry<String, Holder<T>>>() {
		@Override
		public int compare(Entry<String, Holder<T>> e1, Entry<String, Holder<T>> e2) {
			return Integer.compare(e1.getValue().lastTouch, e2.getValue().lastTouch);
		}
	};

	private final class Holder<E> {

		E item;
		int lastTouch;

		Holder(E item) {
			this.item = item;
			touch();
		}

		void touch() {
			lastTouch = time;
		}
	}

	private int time = 0;
	private final int capacity;
	private final int itemsToClearOnCleanup;
	private final Map<String, Holder<T>> data;

	public TrainingProblem6(int capacity, int itemsToClearOnCleanup) {
		this.capacity = capacity;
		this.itemsToClearOnCleanup = itemsToClearOnCleanup;
		data = new HashMap<String, Holder<T>>((int) (capacity * LOAD_FACTOR));
	}

	public T get(String key) {
		Holder<T> holder = data.get(key);
		if (holder != null) {
			holder.touch();
			return holder.item;
		}
		return null;
	}

	public void put(String key, T item) {
		if (data.size() == capacity) {
			clearOldEntries();
		}
		data.put(key, new Holder<>(item));
		time++;
	}

	private void clearOldEntries() {
		List<Entry<String, Holder<T>>> entries = new ArrayList<>(data.entrySet());
		Collections.sort(entries, AGE_COMPARATOR);
		int limit = Math.min(data.size(), itemsToClearOnCleanup);
		for (int i = 0; i < limit; i++) {
			data.remove(entries.get(i).getKey());
		}
	}

	public int size() {
		return data.size();
	}
}
