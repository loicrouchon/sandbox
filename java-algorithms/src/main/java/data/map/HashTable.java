package data.map;

public interface HashTable<K, V> {

    int getCount();

    double getCurrentLoadFactor();

    void put(K key, V value);

    void remove(K key);

    V get(K key);
}
