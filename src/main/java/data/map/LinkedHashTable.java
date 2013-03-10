package data.map;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedHashTable<K, V> implements HashTable<K, V> {
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

    private final LinkedList<Pair<K, V>>[] array;

    private int count = 0;

    @SuppressWarnings("unchecked")
    public LinkedHashTable(int capacity) {
        array = new LinkedList[capacity];
    }

    public int getCount() {
        return count;
    }

    public double getCurrentLoadFactor() {
        return (double) count / array.length;
    }

    public void put(K key, V value) {
        assert key != null;
        int index = hash(key);
        if (array[index] == null) {
            array[index] = new LinkedList<Pair<K, V>>();
        } else {
            remove(key, index);
        }
        array[index].push(new Pair<K, V>(key, value));
        count++;
    }

    public void remove(K key) {
        int index = hash(key);
        remove(key, index);
        if (array[index] != null && array[index].isEmpty()) {
            array[index] = null;
        }
    }

    public V get(K key) {
        V result = null;
        int index = hash(key);
        if (array[index] != null) {
            for (Pair<K, V> pair : array[index]) {
                if (pair.key.equals(key)) {
                    result = pair.value;
                    break;
                }
            }
        }
        return result;
    }

    private int hash(K key) {
        return (37 * key.hashCode() + 1) % array.length;
    }

    private void remove(K key, int index) {
        if (array[index] == null) {
            return;
        }
        Iterator<Pair<K, V>> it = array[index].iterator();
        while (it.hasNext()) {
            Pair<K, V> pair = it.next();
            if (pair.key.equals(key)) {
                it.remove();
                count--;
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (LinkedList<Pair<K, V>> list : array) {
            if (list != null) {
                for (Pair<K, V> pair : list) {
                    sb.append(pair.toString());
                    sb.append(",");
                }
            }
        }
        if (count > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}