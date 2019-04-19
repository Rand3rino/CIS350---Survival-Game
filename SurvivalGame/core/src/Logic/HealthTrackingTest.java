package Logic;

import entity.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthTrackingTest {

    @Test
    public void constructorTest(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,3,3);

        assertEquals(3, h.getHealth());
    }

    @Test
    public void decHealthTest(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,3,3);

        h.decreaseHealth(3);

        assertEquals(0, h.getHealth());
    }

    @Test
    public void decHealthTest2() {
        Player p = new Player(100, 100, null, null);
        HealthTracking h = new HealthTracking(p, 3, 3);

        h.decreaseHealth(4);

        assertEquals(0, h.getHealth());
    }

    @Test
    public void decHealthTest3(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,3,3);

        h.decreaseHealth(2);

        assertEquals(1,h.getHealth());
    }

    @Test
    public void buffHealthTest(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,2,3);

        h.buffHealth(1);

        assertEquals(3,h.getHealth());
    }

    @Test
    public void buffHealthTest2(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,1,3);
        h.buffHealth(1);
        assertEquals(2,h.getHealth());
    }

    @Test
    public void buffHealthTest3(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,3,3);
        h.buffHealth(1);

        assertEquals(3,h.getHealth());
    }

    @Test
    public void isDeadTest(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,3,3);
        assertFalse(h.isDead());
    }

    @Test
    public void isDeadTest2(){
        Player p = new Player(100,100,null,null);
        HealthTracking h = new HealthTracking(p,0,3);

        assertTrue(h.isDead());
    }

}