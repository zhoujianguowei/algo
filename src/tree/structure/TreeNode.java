package tree.structure;

import java.util.List;

/**
 * 普通树结构的定义
 *
 * @param <T>
 * @author bjzhou
 * @date 2019-11-08
 */
public class TreeNode<T> extends Node implements Comparable<TreeNode> {
    public List<TreeNode<T>> children;

    public TreeNode(T val) {
        this.val = val;
    }

    /**
     * 如果{@link Node#val}实现了{@link Comparable}接口，那么
     * 比较的是这两个值；否则直接比较两对象的hash值。
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(TreeNode o) {
        if (o == null) {
            return -1;
        }
        if (this.val != null && o.val == null) {
            Class<?> cls = this.val.getClass();
            if (Comparable.class.isAssignableFrom(cls)) {
                return ((Comparable) this.val).compareTo(o.val);
            }
        }
        return this.hashCode() - o.hashCode();
    }

    @Override
    public void visit() {
        System.out.println(val);
    }
}
