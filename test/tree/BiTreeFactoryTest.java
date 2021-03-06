package tree;

import com.bjzhou.assist.utils.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tree.structure.BiTree;

import java.io.File;

public class BiTreeFactoryTest {
    BiTreeFactory biTreeFactory;
    BiTree<String> bt;

    @Before
    public void setUp() throws Exception {
        biTreeFactory = new BiTreeFactory();
        bt = biTreeFactory.constructBiTree(BiTreeFactory.CONSTRUCT_STRATEGY_FILE, "src/bt.txt", String.class);
    }

    @Test
    public void constructBiTree() throws Exception {
        Assert.assertTrue(bt != null);
    }

    @Test
    public void exportTree() throws Exception {
        String exportTreeS = biTreeFactory.exportTree(bt);
        FileUtils fileUtils = FileUtils.getInstance();
        fileUtils.writeStrToFile(exportTreeS, new File("src/export.txt"), "UTF-8");
    }

}