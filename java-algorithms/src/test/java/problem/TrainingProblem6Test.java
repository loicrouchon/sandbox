package problem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

abstract public class TrainingProblem6Test {

	abstract protected <T> TrainingProblem6<T> createCacheManager(int capacity, int itemsTocleanUp);

	@Test
	public void testEmptyCacheManager() {
		TrainingProblem6<String> cacheManager = createCacheManager(10, 5);
		assertThat(cacheManager.size(), is(0));
	}

	@Test
	public void testAddElements() {
		TrainingProblem6<String> cacheManager = createCacheManager(10, 5);
		cacheManager.put("key1", "value1");
		cacheManager.put("key2", "value2");
		assertThat(cacheManager.size(), is(2));
		assertThat(cacheManager.get("key1"), is("value1"));
		assertThat(cacheManager.get("key2"), is("value2"));
	}

	@Test
	public void testAddElementsWithSameKey() {
		TrainingProblem6<String> cacheManager = createCacheManager(10, 5);
		cacheManager.put("key1", "value1");
		cacheManager.put("key1", "value2");
		assertThat(cacheManager.size(), is(1));
		assertThat(cacheManager.get("key1"), is("value2"));
	}

	@Test
	public void testAddElementsToCapacity() {
		TrainingProblem6<String> cacheManager = createCacheManager(4, 2);
		cacheManager.put("key1", "value1");
		cacheManager.put("key2", "value2");
		cacheManager.put("key3", "value3");
		cacheManager.put("key4", "value4");
		assertThat(cacheManager.size(), is(4));
		assertThat(cacheManager.get("key1"), is("value1"));
		assertThat(cacheManager.get("key2"), is("value2"));
		assertThat(cacheManager.get("key3"), is("value3"));
		assertThat(cacheManager.get("key4"), is("value4"));
	}

	@Test
	public void testAddMoreElementsThanCapacity() {
		TrainingProblem6<String> cacheManager = createCacheManager(4, 2);
		cacheManager.put("key1", "value1");
		cacheManager.put("key2", "value2");
		cacheManager.put("key3", "value3");
		cacheManager.put("key4", "value4");
		cacheManager.put("key5", "value5");
		assertThat(cacheManager.size(), is(3));
		assertThat(cacheManager.get("key1"), CoreMatchers.nullValue());
		assertThat(cacheManager.get("key2"), CoreMatchers.nullValue());
		assertThat(cacheManager.get("key3"), is("value3"));
		assertThat(cacheManager.get("key4"), is("value4"));
		assertThat(cacheManager.get("key5"), is("value5"));
	}

	@Test
	public void testAddMoreElementsThanCapacityTouchCheck() {
		TrainingProblem6<String> cacheManager = createCacheManager(4, 2);
		cacheManager.put("key1", "value1");
		cacheManager.put("key2", "value2");
		cacheManager.put("key3", "value3");
		assertThat(cacheManager.get("key1"), is("value1"));
		cacheManager.put("key4", "value4");
		cacheManager.put("key5", "value5");
		assertThat(cacheManager.size(), is(3));
		assertThat(cacheManager.get("key1"), is("value1"));
		assertThat(cacheManager.get("key2"), CoreMatchers.nullValue());
		assertThat(cacheManager.get("key3"), CoreMatchers.nullValue());
		assertThat(cacheManager.get("key4"), is("value4"));
		assertThat(cacheManager.get("key5"), is("value5"));
	}

	@Test
	public void testAddMoreElementsThanCapacityOverwriteCheck() {
		TrainingProblem6<String> cacheManager = createCacheManager(4, 2);
		cacheManager.put("key1", "value1");
		cacheManager.put("key2", "value2");
		cacheManager.put("key3", "value3");
		cacheManager.put("key1", "value1.bis");
		cacheManager.put("key4", "value4");
		cacheManager.put("key5", "value5");
		assertThat(cacheManager.size(), is(3));
		assertThat(cacheManager.get("key1"), is("value1.bis"));
		assertThat(cacheManager.get("key2"), CoreMatchers.nullValue());
		assertThat(cacheManager.get("key3"), CoreMatchers.nullValue());
		assertThat(cacheManager.get("key4"), is("value4"));
		assertThat(cacheManager.get("key5"), is("value5"));
	}

	@Test
	public void testHugeContent() {
		int capacity = 100000;
		int limit = capacity * 10;
		int itemsTocleanUp = capacity / 5;
		TrainingProblem6<String> cacheManager = createCacheManager(capacity, itemsTocleanUp);
		for (int i = 1; i <= limit; i++) {
			String str = Integer.toString(i);
			cacheManager.put(str, str);
			int expectedSize = (i <= capacity) ? i
			        : (i - ((int) ((i - 1 - (capacity - itemsTocleanUp)) / itemsTocleanUp) * itemsTocleanUp));
			assertThat("i is " + i, cacheManager.size(), is(expectedSize));
		}
	}
}
