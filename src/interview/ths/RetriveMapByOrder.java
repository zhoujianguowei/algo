package interview.ths;

/**
 * 特殊的map接口，能够实现map类的按序入队、出队
 */
public interface RetriveMapByOrder<K,V> {
    V get(K key);

    /**
     * 获取key所对应前一个key的value值，如果不存在
     * 返回null
     * @param key
     * @return
     */
    V getBefore(K key);

    /**
     * 插入指定的值，如果该值已存在，那么用新值覆盖旧值，否则返回null
     * @param key
     * @param value
     * @return
     */
    V put(K key,V value);

    /**
     * 同{@link RetriveMapByOrder#getBefore(Object)}，不过是获取其key的后面一个
     * 值的value
     * @param key
     * @return
     */
    V getAfter(K key);
    int size();
}
