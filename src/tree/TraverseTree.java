package tree;

import com.bjzhou.assist.entity.Queue;
import oracle.sql.CHAR;
import tree.structure.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 遍历数的工具类，遍历树一共有三种方式：
 * 分别是先序遍历、后序遍历以及层次遍历
 *
 * @author bjzhou
 * @date 2019-11-08
 */
public class TraverseTree {
    /**
     * 先序遍历，采用递归方式实现
     *
     * @param treeNode
     * @param <T>
     */
    public static <T> void rootFirstTraverse(TreeNode<T> treeNode) {
        if (treeNode != null) {
            treeNode.visit();
            if (treeNode.children != null) {
                for (TreeNode<T> child : treeNode.children) {
                    rootFirstTraverse(child);
                }
            }
        }
    }

    /**
     * 后序遍历
     *
     * @param treeNode
     * @param <T>
     */
    public static <T> void leftToRightTraverse(TreeNode<T> treeNode) {
        if (treeNode != null) {
            List<TreeNode<T>> children = treeNode.children;
            if (children != null) {
                for (TreeNode<T> child : children) {
                    leftToRightTraverse(child);
                }
            }
            treeNode.visit();
        }
    }

    /**
     * 层次遍历
     *
     * @param treeNode
     * @param <T>
     */
    public static <T> void levelTraverse(TreeNode<T> treeNode) {
        Queue<TreeNode<T>> queue = new Queue<>();
        if (treeNode != null) {
            queue.enQueue(treeNode);
        }
        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.deQueue();
            node.visit();
            List<TreeNode<T>> children = node.children;
            if (children != null) {
                for (TreeNode<T> child : children) {
                    queue.enQueue(child);
                }
            }
        }
    }

    /**
     * 构建数型结构，树型结构从上到下依次为
     *            A
     *      B       C       D
     *  E F G  H            I J
     *        K L
     * @return
     */
    public static TreeNode<Character> generateTree(){
        List<TreeNode<Character>> treeNodeList=new ArrayList<>();
        for(char i='A';i<='L';i++){
            TreeNode<Character> treeNode=new TreeNode<>(i);
            treeNodeList.add(treeNode);
        }
        //构建层次关系
        treeNodeList.get(0).children=new ArrayList<>();
        treeNodeList.get(0).children.add(treeNodeList.get(1));
        treeNodeList.get(0).children.add(treeNodeList.get(2));
        treeNodeList.get(0).children.add(treeNodeList.get(3));

        treeNodeList.get(1).children=new ArrayList<>();
        treeNodeList.get(1).children.add(treeNodeList.get(4));
        treeNodeList.get(1).children.add(treeNodeList.get(5));
        treeNodeList.get(1).children.add(treeNodeList.get(6));
        treeNodeList.get(1).children.add(treeNodeList.get(7));

        treeNodeList.get(3).children=new ArrayList<>();
        treeNodeList.get(3).children.add(treeNodeList.get(8));
        treeNodeList.get(3).children.add(treeNodeList.get(9));

        treeNodeList.get(7).children=new ArrayList<>();
        treeNodeList.get(7).children.add(treeNodeList.get(10));
        treeNodeList.get(7).children.add(treeNodeList.get(11));
        return  treeNodeList.get(0);
    }

    public static void main(String[] args) {
        TreeNode<Character> root = generateTree();
        System.out.println("先序遍历");
        rootFirstTraverse(root);
        System.out.println("后序遍历");
        leftToRightTraverse(root);
        System.out.println("层次遍历");
        levelTraverse(root);
    }
}
