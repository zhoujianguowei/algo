package interview.ths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RetriveMapByOrderImplTest {
    RetriveMapByOrderImpl<String, Integer> retriveMapByOrder;

    @Before
    public void setUp() throws Exception {
        retriveMapByOrder = new RetriveMapByOrderImpl();
        retriveMapByOrder.put("2", 2);
        retriveMapByOrder.put("3", 3);
        retriveMapByOrder.put("11", 11);
        retriveMapByOrder.put("14", 14);
    }

    @Test
    public void get() throws Exception {
        Assert.assertTrue(retriveMapByOrder.get("2") == 2);
        Assert.assertTrue(retriveMapByOrder.get("4") == null);
    }

    @Test
    public void getBefore() throws Exception {
        Assert.assertTrue(retriveMapByOrder.getBefore("3") == 2);
        Assert.assertTrue(retriveMapByOrder.getBefore("2") == null);
    }

    @Test
    public void put() throws Exception {
        retriveMapByOrder.put("11", 110);
        Assert.assertTrue(retriveMapByOrder.get("11") == 110);
    }

    @Test
    public void getAfter() throws Exception {
        Assert.assertTrue(retriveMapByOrder.getAfter("11") == 14);
        Assert.assertTrue(retriveMapByOrder.getAfter("14") == null);
    }

    @Test
    public void size() throws Exception {
        Assert.assertTrue(retriveMapByOrder.size() == 4);
    }

}