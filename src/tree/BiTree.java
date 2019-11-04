/**
 * 二叉树基本定义条件
 *
 * @author bjzhou
 * @date 2019-11-03
 */
package tree;

public class BiTree<T> extends Node {
    /**
     * 分别标识左、右以及父节点指针
     */
    public BiTree left,right,parent;
    /**
     * 打印二叉树时后使用的，标记当前节点的水平距离位置
     * @see {@link tree.util.BiTreeUtil#printBiTree(BiTree, int)}
     */
    public int location;
    @Override
    public void visit() {
        System.out.println(val);
    }
}
