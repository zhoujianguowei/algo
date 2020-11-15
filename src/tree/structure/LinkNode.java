package tree.structure;

/**
 * 链表数据结构(双向)
 *
 * @author bjzhou
 * @date 2019-11-09
 */
public class LinkNode<T> extends Node {
    public LinkNode<T> pre;

    public LinkNode<T> next;

    public LinkNode(T val) {
        this.val = val;
    }

    public T getVal() {
        return (T) val;
    }

    @Override
    public void visit() {
        System.out.println(val);
    }
}
