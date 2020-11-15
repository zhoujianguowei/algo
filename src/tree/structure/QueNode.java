package tree.structure;

/**
 * 队列的链式结构
 *
 * @param <T>
 * @author bjzhou
 * @date 2019-11-09
 */
public class QueNode<T> extends Node {
    /**
     * 队头
     */
    public LinkNode<T> front;
    /**
     * 队尾
     */
    public LinkNode<T> tail;

    @Override
    public void visit() {
        System.out.println(val);
    }

    /**
     * 入队
     *
     * @param node
     */
    public void enq(LinkNode<T> node) {
        if (tail == null) {
            front = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    public boolean isEmpty() {
        return front == null;
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        while (!isEmpty()) {
            LinkNode<T> node = deq();
            node=null;
        }
    }

    /**
     * 出队
     *
     * @return
     */
    public LinkNode<T> deq() {
        if (isEmpty()) {
            throw new NullPointerException("队列为空");
        }
        LinkNode<T> retVal = front;
        front = front.next;
        //队列中最后一个元素
        if (front == null) {
            tail = null;
        }
        retVal.next = null;
        retVal.pre = null;
        return retVal;
    }
}
