package tree;

import com.bjzhou.assist.entity.Queue;
import com.bjzhou.assist.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tree.util.BiTreeUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 构建二叉树的工具类
 *
 * @author bjzhou
 * @date 2019-11-03
 */
public class BiTreeFactory {
    /**
     * 构建二叉树的策略
     */
    /**
     * 从文件中创建二叉树,按照数组存储方式进行构建
     */
    public static final String CONSTRUCT_STRATEGY_FILE = "construct_from_file";
    /**
     * 节点为空标识
     */
    public static final String EMPTY_NODE_FLAG = "null";
    private static final Logger LOGGER = LoggerFactory.getLogger(BiTreeFactory.class);

    /**
     * @param strategy 构造方式
     * @param source   数据来源
     * @param <T>
     * @return
     */
    public <T> BiTree<T> constructBiTree(String strategy, String source) {
        Objects.requireNonNull(strategy);
        Objects.requireNonNull(source);
        BiTree bt = null;
        switch (strategy) {
            case CONSTRUCT_STRATEGY_FILE:
                try {
                    bt = constructFromFile(source);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage(), e);
                }
                break;
            default:
                break;
        }
        return bt;
    }

    /**
     * 从文本文件中构建二叉树，按照完全二叉树的分布形式，如果
     * 节点的值为空，那么使用null进行分割。
     * 节点之间使用，或者空格进行分割,编码采用UTF-8形式
     *
     * @param sourcePath
     * @param <T>
     * @return
     * @throws Exception
     */
    protected <T> BiTree<T> constructFromFile(String sourcePath) throws Exception {
        FileUtils fileUtils = FileUtils.getInstance();
        File file = new File(sourcePath);
        if (!file.exists() || file.isDirectory()) {
            throw new IOException("路径不是一个合法的文件");
        }
        if (!file.canRead()) {
            LOGGER.error("当前文件：{}不可读", sourcePath);
            throw new IOException("当前文件不可读");
        }
        String fileContent = fileUtils.extractFileContent(file, true);
        String[] nodes = fileContent.split("(\\s+|,)");
        List<BiTree<T>> biTreeList = new ArrayList<>(nodes.length);
        /**
         * {@link Node#val}字段
         */
        for (String val : nodes) {
            BiTree<T> biTree = new BiTree<>();
            if (val.equals(EMPTY_NODE_FLAG)) {
                biTree = null;
            } else {
                biTree.val = val;
            }
            biTreeList.add(biTree);
        }
        /**
         * 构建二叉树的层次关系,设置左、右、父指针
         */
        for (int i = 0; i < biTreeList.size(); i++) {
            BiTree<T> biTree = biTreeList.get(i);
            if (biTree == null) {
                continue;
            }
            //左孩子
            int j = 2 * i + 1;
            if (j < biTreeList.size()) {
                biTree.left = biTreeList.get(j);
            }
            //右孩子
            j++;
            if (j < biTreeList.size()) {
                biTree.right = biTreeList.get(j);
            }
            //父节点指针
            if (i > 0) {
                j = (i - 1) / 2;
                biTree.parent = biTreeList.get(j);
            }
        }
        return biTreeList.get(0);
    }

    /**
     * 导出树的结构，以文本方式，节点之间使用空格进行分割
     *
     * @param bt
     * @param <T>
     * @return
     */
    public <T> String exportTree(BiTree<T> bt) {
        StringBuilder result = new StringBuilder();
        if (bt == null) {
            return result.toString();
        }
        Queue<BiTree<T>> queue = new Queue<>();
        /**
         * 采用层次遍历方式，不过将队列中空节点也存放进去
         */
        Queue<BiTree<T>> nextQ = new Queue<>();
        queue.enQueue(bt);
        result.append(bt.val).append(FileUtils.LINE_BREAK);
        BiTree<T> curP = null;
        /**
         * 下一层的树结构
         */
        StringBuilder nextLevelBuilder = new StringBuilder();
        int depWidth = BiTreeUtil.getDepth(bt);
        int level = 1;
        /**
         * 每一层的遍历都是为了将下一层树的节点结构构建出来。
         * 当遍历到倒数第二层的时候，需要将下一层末尾为空的节点过滤掉
         */
        while (!queue.isEmpty() && level < depWidth) {
            while (!queue.isEmpty()) {
                curP = queue.deQueue();
                //当前节点是空，左右孩子节点都为
                if (curP == null) {
                    nextLevelBuilder.append(EMPTY_NODE_FLAG).append(" ").append(EMPTY_NODE_FLAG).append(" ");
                    nextQ.enQueue(null);
                    nextQ.enQueue(null);
                } else {
                    if (curP.left != null) {
                        nextLevelBuilder.append(curP.left.val).append(" ");
                    } else {
                        nextLevelBuilder.append(EMPTY_NODE_FLAG).append(" ");
                    }
                    if (curP.right != null) {
                        nextLevelBuilder.append(curP.right.val).append(" ");
                    } else {
                        nextLevelBuilder.append(EMPTY_NODE_FLAG).append(" ");
                    }
                    nextQ.enQueue(curP.left);
                    nextQ.enQueue(curP.right);
                }
            }
            //访问的是倒数第二层，将末尾多余的EMPTY_NODE_FLAG删除
            if (level == depWidth - 1) {
                String[] sepArrays = nextLevelBuilder.toString().split(" ");
                int k = sepArrays.length - 1;
                while (sepArrays[k].equals(EMPTY_NODE_FLAG)) {
                    k--;
                }
                nextLevelBuilder = new StringBuilder();
                for (int i= 0; i<=k; i++) {
                    nextLevelBuilder.append(sepArrays[i]).append(" ");
                }
            }
            result.append(nextLevelBuilder);
            result.append(FileUtils.LINE_BREAK);
            nextLevelBuilder = new StringBuilder();
            queue = nextQ;
            nextQ = new Queue<>();
            level++;

        }
        return result.toString();
    }

}
