package JAVAPractise

import org.junit.Before
import org.junit.Test

class BiSectionExampleTest extends GroovyTestCase {

    private BiSectionExample bi;

    @Before
    public void runBeforeEachTest()
    {
        bi = new BiSectionExample();
    }

    @Test
    public void test4MethodCoverage () {
        //System.out.print(bi.root(0.5, 100.3, 0.1));
        assert (bi.root(0.5, 100.3, 0.1) >= 100);
        //question: should we assert the returned value is the exact value we expect?
    }

    @Test
    public void test4LoopCoverage1 () {//loop once
        assert(bi.root(0,100,80) >          50);
    }
}
