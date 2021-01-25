package data.map;

public class LinkedHashTableTest extends HashTableTest {
    @Override
    protected <K, V> HashTable<K, V> getImplementation(int capacity) {
        return new LinkedHashTable<K, V>(capacity);
    }
}
