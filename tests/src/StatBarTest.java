import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.misc.StatBar;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class StatBarTest {

    private StatBar testStatBar;

    @Before
    public void init() {
        testStatBar = new StatBar(Vector2.Zero, "Blue.png", 3);
    }

    /**
     * Test ID; StatBar_1.1
     *
     * Input: N/A
     * Expected Output: Correct initialized variables
     */
    //Testing that the constructor initializes StatBar correctly by checking values
    @Test
    public void StatBarShouldInitializeCorrectly() {
        assertEquals(3, testStatBar.getStatBarHeight());
    }
}
