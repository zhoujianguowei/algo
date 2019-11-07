package tree.structure;

/**
 * 可排序节点
 *
 * @param <T>
 * @author bjzhou
 * @date 2019-11-07
 */
public class ComparableNode<T extends Comparable> extends Node implements Comparable<ComparableNode> {
    public T val;

    @Override
    public void visit() {
        System.out.print(val + "\t");
    }

    /**
     * 设置null值排列在后面（对于升序来说）
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(ComparableNode o) {
        if (o == null || o.val == null) {
            return -1;
        } else {
            if (val == null) {
                return 1;
            }
            return val.compareTo(o.val);
        }
    }
}
