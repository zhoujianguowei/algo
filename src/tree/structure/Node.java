package tree.structure;

public abstract class Node<T> {
    public T val;

    /**
     * 访问当前节点的输出内容
     */
    public abstract void visit();
}
