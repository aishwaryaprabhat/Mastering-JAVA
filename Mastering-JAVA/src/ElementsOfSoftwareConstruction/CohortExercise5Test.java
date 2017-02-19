package ElementsOfSoftwareConstruction;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 2/19/2017.
 */
public class CohortExercise5Test {
    @Test
    public void getint() throws Exception {
        CohortExercise5 c = new CohortExercise5(2.3);
        assertEquals(1,c.getint());
    }

}