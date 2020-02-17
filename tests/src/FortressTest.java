import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.Fortress;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class FortressTest {

    private Kroy testKroy;
    private Fortress testFortress;

    @Before
    public void init() {
        testKroy = new Kroy();
        testFortress = new Fortress(new Vector2(0, 0), testKroy.mainGameScreen.textures.getFortress(0),
                testKroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(50, 50), 100,
                10);
    }

    //Testing that the Fortress constructor is working as intended with standard input
    @Test
    public void fortressShouldInitializeCorrectly() {
        assertEquals(new Vector2(0,0), testFortress.getPosition());
    }


}
