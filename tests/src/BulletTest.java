import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class BulletTest {

    private Kroy testKroy;
    private Bullet testBullet;

    @Before
    public void init() {
        testKroy = new Kroy();
        testBullet = new Bullet(new Vector2(0, 0 ), new Vector2(100, 100), 10, 100, 10);
    }


    //Testing that the Bullet constructor is working as intended with standard input
    @Test
    public void bulletShouldInitializeCorrectly() {
        assertEquals(new Vector2(10, 10), testBullet.getCentre());
    }


}
