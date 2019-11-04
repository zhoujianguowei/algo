package tree;

import com.bjzhou.assist.entity.Queue;

import java.util.Stack;

/**
 * 遍历二叉树的类(提供先序、中序、后序以及层级遍历方法)
 * (对于先序、中序和后序提供其非递归实现)
 *
 * @author bjzhou
 * @date 2019-11-03
 */
public class TraverseBiThree {
    /**
     * 递归先序遍历
     *
     * @param bt
     * @param <T>
     */
    public static <T> void LDR_RECURSIVE(BiTree<T> bt) {
        if (bt != null) {
            bt.visit();
            LDR_RECURSIVE(bt.left);
            LDR_RECURSIVE(bt.right);
        }
    }

    /**
     * 先序遍历非递归实现
     *
     * @param bt
     * @param <T>
     */
    public static <T> void LDR_ITERATIVE(BiTree<T> bt) {
        Stack<BiTree<T>> stack = new Stack<>();
        BiTree<T> curP = bt;
        while (!stack.isEmpty() || curP != null) {
            while (curP != null) {
                curP.visit();
                stack.push(curP);
                curP = curP.left;
            }
            curP=stack.pop();
            curP=curP.right;
        }
    }

    /**
     * 中序遍历递归实现
     *
     * @param bt
     * @param <T>
     */
    public static <T> void DLR_RECURSIVE(BiTree<T> bt) {
        if (bt != null) {
            DLR_RECURSIVE(bt.left);
            bt.visit();
            DLR_RECURSIVE(bt.right);
        }
    }

    /**
     * 中序遍历迭代实现
     *
     * @param bt
     * @param <T>
     */
    public static <T> void DLR_ITERATIVE(BiTree<T> bt) {
        Stack<BiTree<T>> st = new Stack<>();
        BiTree<T> curP = bt;
        while (!st.isEmpty() || curP != null) {
            while (curP != null) {
                st.push(curP);
                curP = curP.left;
            }
            curP = st.pop();
            curP.visit();
            curP = curP.right;
        }
    }

    /***
     * 后序遍历递归实现
     * @param bt
     * @param <T>
     */
    public static <T> void LRD_RECURSIVE(BiTree<T> bt) {
        if (bt != null) {
            LRD_RECURSIVE(bt.left);
            LRD_RECURSIVE(bt.right);
            bt.visit();
        }
    }

    /**
     * 后序遍历迭代实现
     *
     * @param bt
     * @param <T>
     */
    public static <T> void LRD_ITERATIVE(BiTree<T> bt) {
        Stack<BiTree<T>> st = new Stack<>();
        BiTree<T> curP = bt;
        BiTree<T> preP = null;
        while (curP != null || !st.isEmpty()) {
            while (curP != null) {
                st.push(curP);
                curP = curP.left;
            }
            curP = st.peek();
            if (curP.right == null || curP.right.equals(preP)) {
                curP.visit();
                preP=curP;
                st.pop();
                curP = null;
            } else {
                curP = curP.right;
            }
        }
    }

    /**
     * 层次遍历访问二叉树
     * @param bt
     * @param <T>
     */
    public static <T> void level(BiTree<T> bt){
        Queue<BiTree<T>> queue=new Queue<>();
        if(bt==null){
            return;
        }
        queue.enQueue(bt);
        BiTree<T> curP=null;
        while(!queue.isEmpty()){
            curP=queue.deQueue();
            curP.visit();
            if(curP.left!=null){
                queue.enQueue(curP.left);
            }
            if(curP.right!=null){
                queue.enQueue(curP.right);
            }
        }
    }
}
