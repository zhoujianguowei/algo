package tree.util;

import com.bjzhou.assist.entity.Queue;
import com.bjzhou.assist.utils.FileUtils;
import tree.structure.BiTree;

/**
 * 二叉树工具类，比如打印二叉树
 *
 * @author bjzhou
 * @date 2019-11-04
 */
public class BiTreeUtil {
    /**
     * 获取二叉树的深度
     *
     * @param bt
     * @param <T>
     * @return
     */
    public static <T> int getDepth(BiTree<T> bt) {
        if (bt == null) {
            return 0;
        }
        if (bt.left == null && bt.right == null) {
            return 1;
        }
        int leftDepth = getDepth(bt.left);
        int rightDepth = getDepth(bt.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 构造二叉排序树
     * @param nums
     * @param <T>
     * @return
     */
    public static <T> BiTree<T> constructBST(T[] nums) {
        BiTree<T> root = null;
        for (T num : nums) {
            BiTree<T> biTree = new BiTree<>(num);
            boolean[] found = new boolean[]{false};
            BiTree<T> sbt;
            //当前不存在根节点，创建根节点
            if ((sbt = searchValInBST(biTree, root, null, found)) == null) {
                root = new BiTree<>(num);
            } else {
                //只插入不重复的数据
                if (!found[0]) {
                    if (sbt.compareTo(biTree) > 0) {
                        sbt.left = biTree;
                    } else {
                        sbt.right = biTree;
                    }
                }
            }
        }
        return root;
    }

    /**
     * 在二叉排序树中查找指定的值，如果值不存在，那么那么返回的是访问路径的前一个节点。否则返回当前节点
     * @param target
     * @param root
     * @param pre
     * @param  found 标志位，标识当前数据是否在bst中已经存在
     * @param <T>
     * @return
     */
    private static <T> BiTree<T> searchValInBST(final BiTree<T> target, BiTree<T> root, BiTree<T> pre, boolean[] found) {
        if (root == null) {
            if (pre != null) {
                return pre;
            } else {
                //当前树是空的，无根节点
                return null;
            }
        }
        if (root.compareTo(target) == 0) {
            found[0] = true;
            return root;
        } else if (root.compareTo(target) > 0) {
            return searchValInBST(target, root.left, root, found);
        } else {
            return searchValInBST(target, root.right, root, found);
        }
    }


    /**
     * 测试当前树是否是二叉排序树。空树也是二叉排序树
     * @param root
     * @param pre 刚才访问的节点
     * @param <T>
     * @return
     */
    public static <T> boolean isBST(BiTree<T> root, BiTree<T> pre) {
        if (root == null) {
            return true;
        }
        if (!isBST(root.left, root)) {
            return false;
        }
        if (pre != null) {
            if (root.compareTo(pre) < 0) {
                return false;
            }
        }
        pre = root;
        if (!isBST(pre.right, pre)) {
            return false;
        }
        return true;
    }

    /**
     * 反转二叉树，即将二叉树的左右字树互换
     * @param bt
     * @param <T>
     */
    public static <T> void reverseBiTree(BiTree<T> bt) {
        if (bt != null) {
            BiTree<T> left = bt.left;
            BiTree<T> right = bt.right;
            BiTree<T> tmp = left;
            bt.left = right;
            bt.right = left;
            reverseBiTree(bt.left);
            reverseBiTree(bt.right);
        }
    }

    /**
     * 根据中序和后序遍历次序构建二叉树
     * @param midOrder 中序
     * @param i     中序左边界
     * @param j     中序右边界
     * @param postOrder 后序
     * @param m
     * @param n
     * @param root 根节点 首次调用时候为null
     * @param <T> 返回的是根节点的值
     */
    public static <T> BiTree<T> constructBiTreeByMidOrderPostOrder(BiTree<T>[] midOrder, int i, int j, BiTree<T>[] postOrder, int m, int n, BiTree<T> root) {
        if (j - i != n - m || j < i) {
            throw new IllegalArgumentException("参数错误");
        }
        //根节点
        if (root == null) {
            root = new BiTree<>();
        }
        if (i == j) {
            root.val = midOrder[i].val;
            return root;
        }
        root.val = postOrder[n].val;
        //在中序遍历中找到根节点
        int k = i;
        while (k <= j && midOrder[k].compareTo(root) != 0) {
            k++;
        }
        if (k > j) {
            throw new IllegalArgumentException("无法构建二叉树，参数有误");
        }
        //说明没有左字树
        if (k == i) {
            BiTree<T> rightNode = new BiTree<>();
            root.right = rightNode;
            constructBiTreeByMidOrderPostOrder(midOrder, i + 1, j, postOrder, m, n - 1, rightNode);
        }
        //说明没有右字树
        else if (k == j) {
            BiTree<T> leftNode = new BiTree<>();
            root.left = leftNode;
            constructBiTreeByMidOrderPostOrder(midOrder, i, j - 1, postOrder, m, n - 1, leftNode);
        } else {
            BiTree<T> leftNode = new BiTree<>();
            BiTree<T> rightNode = new BiTree<>();
            root.left = leftNode;
            root.right = rightNode;
            constructBiTreeByMidOrderPostOrder(midOrder, i, k - 1, postOrder, m, m + k - i - 1, leftNode);
            constructBiTreeByMidOrderPostOrder(midOrder, k + 1, j, postOrder, m + k - i, n - 1, rightNode);
        }
        return root;
    }

    /**
     * 破坏完全二叉树之间的层次关系
     *
     * @param nums
     * @param <T>
     */
    public static <T> void destroyBiTreeStructure(BiTree<T>[] nums) {
        for (BiTree<T> num : nums) {
            num.left = null;
            num.right = null;
            num.parent = null;
        }
    }

    /**
     * 完全二叉树构建树的层次结构
     *
     * @param nums
     * @param <T>
     */
    public static <T> void constructBiTreeStructure(BiTree<T>[] nums) {
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            //构建孩子节点联系
            int j = 2 * i + 1;
            if (j < length) {
                nums[i].left = nums[j];
            }
            j++;
            //构建右孩子
            if (j < length) {
                nums[i].right = nums[j];
            }
            //构建父节点联系
            if (i != 0) {
                j = (i - 1) / 2;
                nums[i].parent = nums[j];
            }
        }
    }

    /**
     * 打印二叉树结构（采用层次遍历的顺序访问二叉树）
     *
     * @param bt
     * @param sepWidth 对应满二叉树叶子节点之间的间距
     * @param <T>
     * @return
     */
    public static <T> StringBuilder printBiTree(BiTree<T> bt, int sepWidth) {
        //当前正在遍历的队列
        Queue<BiTree<T>> curQ = new Queue<>();
        //下一次需要遍历的队列
        Queue<BiTree<T>> nextQ = new Queue<>();
        //当前遍历的层次
        int level = 1;

        StringBuilder builder = new StringBuilder();
        /**
         * 父节点和子节点联系的符号，左子树使用/表示，右字树使用\表示
         */
        StringBuilder sepBuilder = new StringBuilder();
        StringBuilder nodeBuilder = new StringBuilder();
        if (bt == null) {
            return builder;
        }
        //初始化根节点的坐标
        int depth = getDepth(bt);
        bt.location = (int) (Math.pow(2, depth - 1) * sepWidth);
        curQ.enQueue(bt);
        while (!curQ.isEmpty()) {
            //该层节点距离父级节点的水平间距
            int nodeWidth = (int) (Math.pow(2, depth - level - 1) * sepWidth);
            //将该层的所有节点遍历完成
            while (!curQ.isEmpty()) {
                BiTree<T> curP = curQ.deQueue();
                for (int k = nodeBuilder.length(); k < curP.location; k++) {
                    nodeBuilder.append(" ");
                }
                //拼接二叉树
                nodeBuilder.append(curP.val);
                //设置子节点的位置
                if (curP.left != null) {
                    curP.left.location = curP.location - nodeWidth;

                }
                if (curP.right != null) {
                    curP.right.location = curP.location + nodeWidth;
                }
                if (curP.left != null) {
                    nextQ.enQueue(curP.left);
                    for (int k = sepBuilder.length(); k < curP.location - 1; k++) {
                        sepBuilder.append(" ");
                    }
                    sepBuilder.append("/");
                }
                if (curP.right != null) {
                    nextQ.enQueue(curP.right);
                    for (int k = sepBuilder.length(); k < curP.location + 1; k++) {
                        sepBuilder.append(" ");
                    }
                    sepBuilder.append("\\");
                }
            }
            builder.append(nodeBuilder);
            if (sepBuilder.length() > 0) {
                builder.append(FileUtils.LINE_BREAK);
                builder.append(sepBuilder);
                builder.append(FileUtils.LINE_BREAK);
            }
            sepBuilder = new StringBuilder();
            nodeBuilder = new StringBuilder();
            level++;
            curQ = nextQ;
            nextQ = new Queue<>();
        }
        return builder;
    }


}
