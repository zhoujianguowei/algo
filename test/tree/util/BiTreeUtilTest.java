package tree.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.ExceptionProxy;
import tree.BiTree;
import tree.BiTreeFactory;

import static org.junit.Assert.*;

public class BiTreeUtilTest {
    BiTree<String> bt;
    @Before
    public void setUp() throws Exception {
        BiTreeFactory factory=new BiTreeFactory();
        bt=factory.constructBiTree(BiTreeFactory.CONSTRUCT_STRATEGY_FILE,"src/export.txt");
    }

    @Test
    public void getDepth() throws Exception {
        int depth=BiTreeUtil.getDepth(bt);
        Assert.assertEquals(4,depth);
    }
    @Test
    public void printBiTree() throws Exception{
       StringBuilder builder= BiTreeUtil.printBiTree(bt,1);
        System.out.println(builder);
    }

}