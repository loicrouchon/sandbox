package data.map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

abstract public class HashTableTest {

    abstract protected <K, V> HashTable<K, V> getImplementation(int capacity);

    @Test
    public void empty() {
        HashTable<Integer, String> map = getImplementation(10);
        assertEquals(0, map.getCount());
        assertEquals(0, map.getCurrentLoadFactor(), 0.01d);
    }

    @Test
    public void getInvalidKey() {
        HashTable<Integer, String> map = getImplementation(10);
        assertEquals(null, map.get(42));
        assertEquals(0, map.getCount());
        assertEquals(0, map.getCurrentLoadFactor(), 0.01d);
    }

    @Test
    public void getValidKey() {
        HashTable<Integer, String> map = getImplementation(10);
        map.put(42, "toto");
        assertEquals("toto", map.get(42));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);

        assertEquals(null, map.get(43));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);
    }

    @Test
    public void getUpdateKey() {
        HashTable<Integer, String> map = getImplementation(10);
        map.put(42, "toto");
        assertEquals("toto", map.get(42));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);

        map.put(42, "tata");
        assertEquals("tata", map.get(42));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);
    }

    @Test
    public void removeKey() {
        HashTable<Integer, String> map = getImplementation(10);
        map.put(42, "toto");
        assertEquals("toto", map.get(42));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);

        assertEquals(null, map.get(43));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);

        map.remove(43);

        assertEquals("toto", map.get(42));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);

        assertEquals(null, map.get(43));
        assertEquals(1, map.getCount());
        assertEquals(1 / 10d, map.getCurrentLoadFactor(), 0.01d);

        map.remove(42);

        assertEquals(null, map.get(42));
        assertEquals(0, map.getCount());
        assertEquals(0, map.getCurrentLoadFactor(), 0.01d);

        assertEquals(null, map.get(43));
        assertEquals(0, map.getCount());
        assertEquals(0, map.getCurrentLoadFactor(), 0.01d);
    }

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
        assertEquals(6 / 3d, map.getCurrentLoadFactor(), 0.01d);
    }

    @Test
    public void collisionsWithRemoves() {
        HashTable<Integer, String> map = getImplementation(3);
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        assertEquals("1", map.get(1));
        assertEquals("2", map.get(2));
        assertEquals("3", map.get(3));
        map.remove(2);
        assertEquals(null, map.get(2));
        assertEquals(2, map.getCount());
        assertEquals(2 / 3d, map.getCurrentLoadFactor(), 0.01d);
        map.put(4, "4");
        assertEquals("1", map.get(1));
        assertEquals("3", map.get(3));
        assertEquals("4", map.get(4));
        map.remove(3);
        map.remove(4);
        assertEquals(null, map.get(3));
        assertEquals(null, map.get(4));
        assertEquals(1, map.getCount());
        assertEquals(1 / 3d, map.getCurrentLoadFactor(), 0.01d);
        map.put(5, "5");
        map.put(6, "6");
        assertEquals("1", map.get(1));
        assertEquals("5", map.get(5));
        assertEquals("6", map.get(6));
        assertEquals(3, map.getCount());
        assertEquals(3 / 3d, map.getCurrentLoadFactor(), 0.01d);
    }
}
