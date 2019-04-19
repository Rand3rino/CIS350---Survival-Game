package Logic;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;


class PathFinderTest {

    @Test
    void isValidCell() {


            PathFinder pf = new PathFinder();
            pf.isValidCell(20,30);
            assertTrue(pf.isValidCell(20,30));
        }


    @Test
    void find() {
    }
}