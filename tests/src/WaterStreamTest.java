import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.misc.StatBar;
import com.dicycat.kroy.misc.WaterStream;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class WaterStreamTest {

    private WaterStream testWaterStream;

    @Before
    public void init() {
        testWaterStream = new WaterStream(new Vector2(0, 0));
    }

    /**
     * Test ID: WaterStream_1.1
     *
     * Input: N/A
     * Expected Output: Correct initialized variables
     */
    //Testing that the constructor for WaterStream works correctly
    @Test
    public void waterStreamShouldInitializeCorrectly() {
        assertEquals(new Vector2(0, 0), testWaterStream.getPosition());
    }
}
