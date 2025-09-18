import com.example.decathlon.heptathlon.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestHepScoreCalculator {

    @Test

    void testHep100M() {

        Hep100MHurdles hep100m = new Hep100MHurdles();
        double runningTime = 14;
        int expectedScore = 978;
        int actualScore = hep100m.calculateResult(runningTime);

        assertEquals(expectedScore,actualScore,"Score should be 978");

    }

    @Test

    void testHep200M() {

        Hep200M hep200M = new Hep200M();
        double runningTime = 14;
        int expectedScore = 2145;
        int actualScore = hep200M.calculateResult(runningTime);

        assertEquals(expectedScore,actualScore,"Score should be 2145");

    }

    @Test

    void test800M() {
        Hep800M hep800M = new Hep800M();
        double runningTime = 80;
        int expectedScore = 1824;
        int actualScore = hep800M.calculateResult(runningTime);


        assertEquals(expectedScore,actualScore,"Score should be 1824");

    }

    @Test

    void testHightJump() {
        HeptHightJump heptHightJump = new HeptHightJump();
        double distance = 80;
        int expectedScore = 16;
        int actualScore = heptHightJump.calculateResult(distance);

        assertEquals(expectedScore,actualScore,"Score should be 16");


    }

    @Test

    void testLongJump() {

        HeptLongJump heptLongJump = new HeptLongJump();
        double distance = 250;
        int expectedScore = 34;
        int actualScore = heptLongJump.calculateResult(distance);

        assertEquals(expectedScore,actualScore,"Score should be 34");
    }

    @Test

    void testShotPut() {

        HeptShotPut heptShotPut = new HeptShotPut();
        double distance = 44;
        int expectedScore = 2871;
        int actualScore = heptShotPut.calculateResult(distance);

        assertEquals(expectedScore,actualScore,"Score should be 2871");
    }

    @Test

    void testJavelinThrow() {

        HeptJavelinThrow heptJavelinThrow = new HeptJavelinThrow();
        double distance = 50;
        int expectedScore = 860;
        int actualScore = heptJavelinThrow.calculateResult(distance);

        assertEquals(expectedScore,actualScore,"Score should be 860");
    }
}
