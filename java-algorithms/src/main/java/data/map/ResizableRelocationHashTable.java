package data.map;

public class ResizableRelocationHashTable<K, V> implements HashTable<K, V> {

    private Entry<K, V>[] store;
    private int count = 0;

    public ResizableRelocationHashTable(int capacity) {
        store = createEntryArray(capacity);
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V>[] createEntryArray(int capacity) {
        return (Entry<K, V>[]) new Entry[capacity];
    }

    public int getCount() {
        return count;
    }

    public double getCurrentLoadFactor() {
        return (double) count / store.length;
    }

    public void put(K key, V value) {
        int index = computeFreeStoreIndex(key);
        if (store[index] == null) {
            count++;
        }
        store[index] = new Entry<>(key, value);
    }

    private int computeFreeStoreIndex(K key) {
        if (getCurrentLoadFactor() > 0.75d) {
            rehash(store.length * 2);
        }
        int index = key.hashCode();
        for (int i = 0; i < store.length; i++) {
            index = (index + i) % store.length;
            Entry<K, V> entry = store[index];
            if (entry == null || entry.key.equals(key)) {
                return index;
            }
        }
        throw new IllegalStateException("HashTable is in an inconsistent state (should never happen)");
    }

    private void rehash(int destinationSize) {
        Entry<K, V>[] previousStore = store;
        count = 0;
        store = createEntryArray(destinationSize);
        for (Entry<K, V> entry : previousStore) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }

    public V get(K key) {
        int index = find(key);
        if (index == -1) {
            return null;
        }
        return store[index].value;
    }

    public void remove(K key) {
        int index = find(key);
        if (index == -1) {
            return;
        }
        count--;
        store[index] = null;
        // optimization possible here not to rehash the whole table, but just subsequent items
        rehash(store.length);
    }

    private int find(K key) {
        int index = key.hashCode();
        for (int i = 0; i < store.length; i++) {
            index = (index + i) % store.length;
            Entry<K, V> entry = store[index];
            if (entry == null) {
                break;
            }
            if (key.equals(entry.key)) {
                return index;
            }
        }
        return -1; // not found
    }

    private static final class Entry<K, V> {
        final K key;
        final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
