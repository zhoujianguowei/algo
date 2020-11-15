package tree.util;

import oracle.sql.CHAR;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tree.structure.BiTree;
import tree.BiTreeFactory;
import tree.Heap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class BiTreeUtilTest {
    BiTree<String> bt;

    @Before
    public void setUp() throws Exception {
        BiTreeFactory factory = new BiTreeFactory();
        bt = factory.constructBiTree(BiTreeFactory.CONSTRUCT_STRATEGY_FILE, "src/export.txt", String.class);
    }

    @Test
    public void getDepth() throws Exception {
        int depth = BiTreeUtil.getDepth(bt);
        Assert.assertEquals(4, depth);
    }

    /**
     * 根据中序和后序遍历次序构建二叉树结构，测试二叉树结构
     A
     / \
     B    C
     /     / \
     D    E   F
     \
     G
     * @throws Exception
     */
    @Test
    public void constructBiTreeByMidOrderPostOrder() throws Exception {
        List<BiTree<Character>> treeList = new ArrayList<>(10);
        for (char a = 'A'; a <= 'G'; a++) {
            BiTree<Character> tree = new BiTree<>(a);
            treeList.add(tree);
        }
        BiTree<Character>[] midOrder = new BiTree[treeList.size()];
        char[] midVisitChars = new char[]{'D', 'G', 'B', 'A', 'E', 'C', 'F'};
        char ch;
        for (int i = 0; i < midVisitChars.length; i++) {
            ch = midVisitChars[i];
            midOrder[i] = getBiTree(ch, treeList);
        }
        BiTree<Character>[] postOrder = new BiTree[treeList.size()];
        char[] postVisitChars = new char[]{'G', 'D', 'B', 'E', 'F', 'C', 'A'};
        for (int i = 0; i < postVisitChars.length; i++) {
            ch = postVisitChars[i];
            postOrder[i] = getBiTree(ch, treeList);
        }
        BiTree<Character> root = BiTreeUtil.constructBiTreeByMidOrderPostOrder(midOrder, 0, midOrder.length - 1, postOrder, 0, postOrder.length - 1, null);
        System.out.println("construct tree by mid and post order");
        System.out.println(BiTreeUtil.printBiTree(root,1));
    }

    private <T> BiTree<T> getBiTree(T val, List<BiTree<T>> treeList) {
        for (BiTree<T> tree : treeList) {
            if (tree.val.equals(val)) {
                return tree;
            }
        }
        return null;
    }

    @Test
    public void printBiTree() throws Exception {
        StringBuilder builder = BiTreeUtil.printBiTree(bt, 1);
        System.out.println(builder);
    }

    @Test
    public void reverseBiTree() throws Exception {
        System.out.println("reverse before");
        System.out.println(BiTreeUtil.printBiTree(bt, 1));
        System.out.println("reverse after");
        BiTreeUtil.reverseBiTree(bt);
        System.out.println(BiTreeUtil.printBiTree(bt, 1));
    }
    @Test
    public void constructBST() throws Exception{
        Integer[] nums=new Integer[]{-12,-48,0,17,-12,-10,75,88,47,33,-29};
        BiTree<Integer> root=BiTreeUtil.constructBST(nums);
        System.out.println(BiTreeUtil.printBiTree(root,1));
    }

    @Test
    public void constructBiTreeStructure() throws Exception {
        int i = 1, length = 15;
        BiTree<Integer>[] nums = new BiTree[length];
        Random random = new Random();
        for (; i <= length; i++) {
            nums[i - 1] = new BiTree<>();
            nums[i - 1].val = random.nextInt(20);
        }
        BiTreeUtil.constructBiTreeStructure(nums);
        System.out.println("调整堆之前");
        System.out.println(BiTreeUtil.printBiTree(nums[0], 2));
        BiTreeUtil.destroyBiTreeStructure(nums);
        System.out.println("调整堆之后");
        Heap<BiTree<Integer>> heap = new Heap<>();
        heap.constructSmallHeap(nums);
        BiTreeUtil.constructBiTreeStructure(nums);
        System.out.println(BiTreeUtil.printBiTree(nums[0], 2));
    }

}