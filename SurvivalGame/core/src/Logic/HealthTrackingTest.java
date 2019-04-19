package Logic;

import entity.Entity;
import entity.Potion;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class HealthTrackingTest {

    private Entity e;

    @Test
    void getHealth() {


            HealthTracking ht = new HealthTracking(e, 2, 1);

            assertTrue(ht.getHealth() == 2);

    }



    @Test
    void isDead() {

        HealthTracking ht = new HealthTracking(e, 0, 1);

        assertTrue(ht.isDead());

    }
}