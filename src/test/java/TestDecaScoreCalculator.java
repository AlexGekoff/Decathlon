
import com.example.decathlon.deca.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDecaScoreCalculator {

    @Test
    void testDec100m() {
        Deca100M deca100M = new Deca100M();

        double runningTime = 11.0;
        int expectedScore = 861;
        int actualScore = deca100M.calculateResult(runningTime);

        assertEquals(expectedScore, actualScore, "Score for 100m with 11s should be 861");

    }

    @Test
    void testLongJump() {
        DecaLongJump decaLongJump = new DecaLongJump();
        double distance = 300;
        int expectedScore = 66;
        int actualScore = decaLongJump.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 66");
    }

    @Test
    void testShotPut() {
        DecaShotPut decaShotPut = new DecaShotPut();
        double distance = 15;
        int expectedScore = 790;
        int actualScore = decaShotPut.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 790");

    }

    @Test
    void testHighJump() {
        DecaHighJump decaHighJump = new DecaHighJump();
        double distance = 80;
        int expectedScore = 8;
        int actualScore = decaHighJump.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 8");

    }

    @Test
    void test400M() {
        Deca400M deca400M = new Deca400M();
        double distance = 40;
        int expectedScore = 1333;
        int actualScore = deca400M.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 1333");

    }

    @Test
    void test100MHurdles() {
        Deca110MHurdles deca110MHurdles = new Deca110MHurdles();
        double distance = 12;
        int expectedScore = 1249;
        int actualScore = deca110MHurdles.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 1249");

    }

    @Test
    void testDiscusThrow() {
        DecaDiscusThrow decaDiscusThrow = new DecaDiscusThrow();
        double distance = 45;
        int expectedScore = 767;
        int actualScore = decaDiscusThrow.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 767");

    }

    @Test
    void testPoleVault() {
        DecaPoleVault decaPoleVault = new DecaPoleVault();
        double distance = 800;
        int expectedScore = 1938;
        int actualScore = decaPoleVault.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 1938");


    }

    @Test
    void testJavelinThrow() {
        DecaJavelinThrow decaJavelinThrow = new DecaJavelinThrow();
        double distance = 44;
        int expectedScore = 500;
        int actualScore = decaJavelinThrow.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 500");


    }

    @Test
    void test1500M() {
        Deca1500M deca1500M = new Deca1500M();
        double distance = 6;
        int expectedScore = 3359;
        int actualScore = deca1500M.calculateResult(distance);

        assertEquals(expectedScore, actualScore, "Score should be 3359");


    }
}
