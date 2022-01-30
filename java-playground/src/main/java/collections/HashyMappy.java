package collections;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

class HashyMappy<K, V> {

    private static final int MIN_SIZE = 10;

    @ToString
    @AllArgsConstructor
    class Entry<K, V> {

        @NonNull K key;
        V value;
        Entry<K, V> next;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] array;

    @SuppressWarnings("unchecked")
    public HashyMappy() {
        array = new Entry[MIN_SIZE];
    }

    public void put(@NonNull K key, V value) {
        int index = index(key);
        var bucket = array[index];
        Entry<K, V> entry = getEntry(key, bucket);
        if (entry == null) {
            // add to bucket
            array[index] = new Entry<>(key, value, bucket);
        } else {
            // update value
            entry.value = value;
        }
    }

    public V get(@NonNull K key) {
        int index = index(key);
        Entry<K, V> entry = getEntry(key, array[index]);
        if (entry != null) {
            return entry.value;
        }
        return null;
    }

    private Entry<K, V> getEntry(K key, Entry<K, V> entry) {
        if (entry == null) {
            return null;
        }
        do {
            if (entry.key.equals(key)) {
                return entry;
            }
            entry = entry.next;
        } while (entry != null);
        return null;
    }

    private int index(K key) {
        return key.hashCode() % array.length;
    }
}
