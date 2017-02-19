package ElementsOfSoftwareConstruction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 2/19/2017.
 */
@RunWith(Parameterized.class)
public class QuickSortTest {

    private int[] unsortedarray;
    private int[] sortedalready;


    public QuickSortTest(int[] unsortedarray, int[] sortedalready){
        this.unsortedarray = unsortedarray;
        this.sortedalready = sortedalready;}


    @Parameterized.Parameters
    public static Collection<Object[]> testData(){
        return Arrays.asList(new Object[][] {
                { new int[] {5,4,6}, new int[] {4,5,6} },
                { new int[] { 1 }, new int[] { 1 } },
                { new int[] {}, new int[] {} },
        });
    }


    @Test
    public void testSort(){
        QuickSort qs = new QuickSort();
        //I edited the QuickSort.java class' sort method to return me the sorted int[] array
        Assert.assertArrayEquals(sortedalready, qs.sort(unsortedarray));

    }
}




//@RunWith(Parameterized.class)
//public class QuickSortTest {
//
//
//
//    @Parameterized.Parameter
//    public static int[][] tobesorted1 ;
//
//
//
//    @After
//    public void tearDown() throws Exception {
//        System.out.println(Arrays.toString(tobesorted1));
//    }
//
//    @Parameterized.Parameters
//    public static Collection<Object[]> data(){
//        int[][][] data = new int[][][]{{{2,1,3},{1,2,3}},{{4,3,4},{3,4,4}}};
//        return Arrays.asList(data);
//
//    }
//
//    @Test
//    public void sort() throws Exception {
//        QuickSort qs = new QuickSort();
//
//        assertEquals(this.tobesorted1[1],qs.sort(tobesorted1[0]));
//    }
//
////    public int[] mysort(){
////        return Arrays.sort(tobesorted1);
////    }
//
//
//
//
//}