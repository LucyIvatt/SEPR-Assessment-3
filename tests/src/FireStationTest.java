import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireStation;
import com.dicycat.kroy.entities.FireTruck;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class FireStationTest {

    private Kroy testKroy;
    private FireStation testFireStation;

    private FireTruck targetTruck;
    private Float[] testTruckStats = new Float[] {1f, 1f, 100f, 1000f};


    @Before
    public void init() {
        testKroy = new Kroy();
        testFireStation = new FireStation();
        targetTruck = new FireTruck(new Vector2(0, 0), testTruckStats, 1);

    }

    //Testing that the FireStation constructor is working as intended with standard input
    @Test
    public void fireStationShouldInitializeCorrectly() {
        assertEquals(100, testFireStation.getHealthPoints());
    }



}
