package ElementsOfSoftwareConstruction;

import org.junit.Test;

import static org.junit.Assert.*;

public class FindMaxTest {
    @Test
    public void pass() throws Exception {
        FindMax findMax = new FindMax();
        assertEquals(17,findMax.max(new int[]{0,6,17,8,2}));
    }

    @Test
    public void fail() throws Exception {
        FindMax findMax = new FindMax();
        assertEquals(18,findMax.max(new int[]{0,6,17,8,2}));

    }


    @Test
    public void error() throws Exception {
        FindMax findMax = new FindMax();
        assertEquals(18.0,findMax.max(new int[]{0,6,17,8,2}));
    }
}