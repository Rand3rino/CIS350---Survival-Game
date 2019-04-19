package Logic;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getXY() {

        Point p = new Point(20,250);

        assertTrue(p.getX() == 20);
        assertTrue(p.getY() == 250);

    }

    @Test
    void getNegXY() {

        Point p = new Point(-20,-250);

        assertTrue(p.getX() == -20);
        assertTrue(p.getY() == -250);

    }

    @Test
    void getZeroXY() {

        Point p = new Point(0,0);

        assertTrue(p.getX() == 0);
        assertTrue(p.getY() == 0);

    }

    @Test
    void getAnyXY() {

        Point p = new Point(2333,5555);

        assertTrue(p.getX() == 2333);
        assertTrue(p.getY() == 5555);

    }





}