package tree.util;

import com.bjzhou.assist.entity.Queue;
import com.bjzhou.assist.utils.FileUtils;
import org.apache.commons.lang.StringUtils;
import tree.BiTree;

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
