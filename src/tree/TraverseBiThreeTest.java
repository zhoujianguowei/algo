package tree;

import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.ExceptionProxy;
import tree.BiTree;
import tree.BiTreeFactory;
import tree.TraverseBiThree;

import static org.junit.Assert.*;

public class TraverseBiThreeTest {

    BiTreeFactory biTreeFactory;
    BiTree<String> bt;

    @Before
    public void setUp() throws Exception {
        biTreeFactory = new BiTreeFactory();
        bt = biTreeFactory.constructFromFile("src/bt.txt");
    }

    @Test
    public void LDR_RECURSIVE() throws Exception {
        TraverseBiThree.LDR_RECURSIVE(bt);
    }

    @Test
    public void LDR_ITERATIVE() throws Exception {
        TraverseBiThree.LDR_ITERATIVE(bt);
    }
    @Test
    public void DLR_RECURSIVE() throws Exception{
        TraverseBiThree.DLR_RECURSIVE(bt);
    }
    @Test
    public void LRD_RECURSIVE() throws Exception{
        TraverseBiThree.LRD_RECURSIVE(bt);
    }
    @Test
    public void LRD_ITERATIVE() throws Exception{
        TraverseBiThree.LRD_ITERATIVE(bt);
    }
    @Test
    public void level() throws Exception{
        TraverseBiThree.level(bt);
    }

}