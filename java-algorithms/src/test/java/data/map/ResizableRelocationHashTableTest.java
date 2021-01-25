package data.map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResizableRelocationHashTableTest extends HashTableTest {
    @Override
    protected <K, V> HashTable<K, V> getImplementation(int capacity) {
        return new ResizableRelocationHashTable<>(capacity);
    }

    @Override
    @Test
    public void collisions() {
        HashTable<Integer, String> map = getImplementation(3);
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(5, "5");
        map.put(6, "6");
        assertEquals("1", map.get(1));
        assertEquals("2", map.get(2));
        assertEquals("3", map.get(3));
        assertEquals("4", map.get(4));
        assertEquals("5", map.get(5));
        assertEquals("6", map.get(6));
        assertEquals(6, map.getCount());
        assertEquals(6d / (3 * 2 * 2), map.getCurrentLoadFactor(), 0.01d);
    }
}
