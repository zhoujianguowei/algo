package tree.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.ExceptionProxy;
import tree.BiTree;
import tree.BiTreeFactory;
import tree.Heap;

import java.util.Random;

import static org.junit.Assert.*;

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

    @Test
    public void printBiTree() throws Exception {
        StringBuilder builder = BiTreeUtil.printBiTree(bt, 1);
        System.out.println(builder);
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