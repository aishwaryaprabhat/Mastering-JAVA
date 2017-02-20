package ElementsOfSoftwareConstruction;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class SubmarineRYANTest {
    @Test
    public void moveplayer1() throws Exception {
        SubmarineRYAN s = new SubmarineRYAN(new Submarine("Ryan1"), new Submarine("Ryan2"));

        assertEquals(new double[]{101,102},s.moveplayer1(101,102));

    }

    @Test
    public void moveplayer2() throws Exception {
        SubmarineRYAN s = new SubmarineRYAN(new Submarine("Ryan1"), new Submarine("Ryan2"));
        assertEquals(new double[]{101,102},s.moveplayer2(101,102));

    }


    @Test
    public void collissiondetected() throws Exception {
        SubmarineRYAN s = new SubmarineRYAN(new Submarine("Ryan1"), new Submarine("Ryan2"));
        s.moveplayer1(101,102);
        s.moveplayer2(101,102);
        assertTrue(s.collissiondetected());

    }
    private Timer testTimer;
    @Before
    public void runBeforeEachTimerTest(){
        testTimer = new Timer();
    }

    @Test
    public void test5Seconds() throws InterruptedException {
        testTimer.startTimer();
        TimeUnit.SECONDS.sleep(5);
        testTimer.stopTimer();
        assert(testTimer.getElapsedTime() == 500);
    }

}
