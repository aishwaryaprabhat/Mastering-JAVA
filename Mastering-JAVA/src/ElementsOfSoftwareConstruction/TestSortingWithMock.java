package ElementsOfSoftwareConstruction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 2/19/2017.
 */

public class TestSortingWithMock {
    private int[] tobesortedarray;
    private int[] sortedArray;
    private int maximum;
    private FindMaxUsingSorting findMax;

    public TestSortingWithMock (int[] tobesortedarray, int[] sortedArray, int maximum) {
        this.tobesortedarray = tobesortedarray;
        this.sortedArray = sortedArray;
        this.maximum = maximum;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (
                new Object [][]{
                        {new int[]{3, 2, 1}, new int[]{1, 2, 3}, 3},
                        {new int[]{7, 8, 2, 1, 3}, new int[]{1, 2, 3, 7, 8}, 8},
                        {new int[]{2, 2, 1, 1, 1}, new int[]{1, 1, 1, 2, 2}, 2}
                });
    }
    @Test
    public void testMaxWithMockSort() {
        Mockery context = new JUnit4Mockery();

        final Sorter sorter = context.mock(Sorter.class);

        context.checking(new Expectations() {{
            oneOf(sorter).sort(tobesortedarray);
            will(returnValue(sortedArray));
        }});

        assertEquals(findMax.findmax(tobesortedarray, sorter), maximum);

        context.assertIsSatisfied();
    }

}