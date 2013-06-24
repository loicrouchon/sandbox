package problem;

import java.util.HashMap;
import java.util.Map;

/**
 * Create a fast cache d storage mechanism that, given a limitation on the
 * amount of cache memory, will ensure that only the least recently used items
 * are discarded when the cache memory is reached when inserting a new item. It
 * supports 2 functions: String get(T t) and void put(String k, T t).
 */
public class TrainingProblem6SortedLinkedList<T> implements TrainingProblem6<T> {

	private static final double LOAD_FACTOR = 1.33;

	private static final class Element<E> {
		final E data;
		Element<E> next = null;
		Element<E> previous = null;

		Element(E data) {
			this.data = data;
		}
	}

	private static final class DoubleLinkedList<T> {

		Element<T> head;
		Element<T> tail;

		void addFirst(Element<T> item) {
			if (head == null) {
				head = item;
				tail = item;
			} else {
				Element<T> next = head;
				head = item;
				item.next = next;
				next.previous = item;
			}
		}

		void addLast(Element<T> item) {
			if (head == null) {
				head = item;
				tail = item;
			} else {
				Element<T> previous = head;
				head = item;
				item.previous = previous;
				previous.next = item;
			}
		}

		void remove(Element<T> item) {
			Element<T> previous = item.previous;
			Element<T> next = item.next;
			if (previous != null) {
				previous.next = next;
			} else {
				head = next;
			}
			if (next != null) {
				next.previous = previous;
			} else {
				tail = previous;
			}
		}

		void moveToEnd(Element<T> item) {
			remove(item);
			addLast(item);
		}
	}

	private final DoubleLinkedList<String> accessOrderList = new DoubleLinkedList<>();
	private final Map<String, Element<String>> accessOrderMap;

	private final int capacity;
	private final int itemsToClearOnCleanup;
	private final Map<String, T> data;

	public TrainingProblem6SortedLinkedList(int capacity, int itemsToClearOnCleanup) {
		this.capacity = capacity;
		this.itemsToClearOnCleanup = itemsToClearOnCleanup;
		int mapsCapacity = (int) (capacity * LOAD_FACTOR);
		data = new HashMap<>(mapsCapacity);
		accessOrderMap = new HashMap<>(mapsCapacity);
	}

	@Override
	public T get(String key) {
		if (data.containsKey(key)) {
			reOrderKey(key);
			return data.get(key);
		}
		return null;
	}

	@Override
	public void put(String key, T item) {
		if (data.containsKey(key)) {
			reOrderKey(key);
		} else {
			if (data.size() == capacity) {
				clearOldEntries();
			}
			Element<String> keyItem = new Element<>(key);
			accessOrderList.addFirst(keyItem);
			accessOrderMap.put(key, keyItem);
		}
		data.put(key, item);
	}

	private void reOrderKey(String key) {
		Element<String> keyItem = accessOrderMap.get(key);
		accessOrderList.moveToEnd(keyItem);
	}

	private void clearOldEntries() {
		int limit = Math.min(data.size(), itemsToClearOnCleanup);
		int i = 0;
		Element<String> item = accessOrderList.tail;
		while (i < limit && item != null) {
			data.remove(item.data);
			accessOrderMap.remove(item.data);
			accessOrderList.remove(item);
			item = item.previous;
			i++;
		}
	}

	@Override
	public int size() {
		return data.size();
	}
}
