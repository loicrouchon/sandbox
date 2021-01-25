package data.map;

;

public class RelocationHashTable<K, V> implements HashTable<K, V> {
    private static class Pair<K, V> {
        final K key;
        final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value;
        }
    }

    private final Pair<K, V>[] array;

    private int count = 0;

    @SuppressWarnings("unchecked")
    public RelocationHashTable(int capacity) {
        array = new Pair[capacity];
    }

    public int getCount() {
        return count;
    }

    public double getCurrentLoadFactor() {
        return (double) count / array.length;
    }

    public void put(K key, V value) {
        assert key != null;
        boolean inserted = false;
        for (int i = 0; !inserted && i < array.length; i++) {
            int index = hash(key, i);
            if (array[index] == null || array[index].key.equals(key)) {
                if (array[index] == null) {
                    count++;
                }
                array[index] = new Pair<K, V>(key, value);
                inserted = true;
            }
        }
        if (!inserted) {
            throw new ArrayIndexOutOfBoundsException("overflow");
        }
    }

    public void remove(K key) {
        for (int i = 0; i < array.length; i++) {
            int index = hash(key, i);
            if (array[index] != null && array[index].key.equals(key)) {
                array[index] = null;
                count--;
            }
        }
    }

    public V get(K key) {
        V result = null;
        for (int i = 0; i < array.length; i++) {
            int index = hash(key, i);
            if (array[index] != null && array[index].key.equals(key)) {
                result = array[index].value;
                break;
            }
        }
        return result;
    }

    private int hash(K key, int loop) {
        if (loop > array.length) {
            throw new ArrayIndexOutOfBoundsException("overflow");
        }
        return (37 * key.hashCode() + 1 + loop) % array.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Pair<K, V> pair : array) {
            if (pair != null) {
                sb.append(pair.toString());
                sb.append(",");
            }
        }
        if (count > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}