package interview.ths;

import java.util.HashMap;

class Entry<K, V> {
    K k;
    V v;
    Entry<K, V> pre;
    Entry<K, V> next;

    Entry(K key, V value, Entry<K, V> pre, Entry<K, V> next) {
        this.k = key;
        this.v = value;
        this.pre = pre;
        this.next = next;
    }
}

/**
 * 对应的实现类
 * @param <K>
 * @param <V>
 */
public class RetriveMapByOrderImpl<K, V> implements RetriveMapByOrder<K, V> {
    HashMap<K, V> map = new HashMap<>();
    HashMap<K, Entry<K, V>> k2NodeMap = new HashMap<>();
    private Entry<K, V> head;
    private Entry<K, V> tail;

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public V getBefore(K key) {
        Entry<K, V> entry = k2NodeMap.get(key);
        if (entry == null || entry.pre == null) {
            return null;
        }
        return entry.pre.v;
    }

    @Override
    public V put(K key, V value) {
        if (!map.containsKey(key)) {
            Entry<K, V> entry = new Entry<>(key, value, tail, null);
            if (tail == null) {
                head = tail = entry;
            }
            tail.next = entry;
            tail = entry;
            map.put(key, value);
            k2NodeMap.put(key, entry);
            map.put(key, value);
            return null;
        } else {
            Entry<K, V> entry = k2NodeMap.get(key);
            V oldV = map.get(key);
            entry.v = value;
            map.put(key, value);
            return oldV;
        }
    }

    @Override
    public V getAfter(K key) {
        Entry<K, V> entry = k2NodeMap.get(key);
        if (entry == null || entry.next == null) {
            return null;
        }
        return entry.next.v;
    }

    @Override
    public int size() {
        return map.size();
    }
}
