package Logic;

/**
 * Point Class
 *
 * Used as a location on a plane by the pathfinder
 *
 * @author Ramell Collins
 * @version 1.0
 */
public class Point {

    private int xVal, yVal;

    /**
     * Point Constructor
     *
     * Sets the point's x and y value
     * @param x the horizontal value of the plane
     * @param y the vertical value of the plane
     */
    public Point(int x, int y) {
        xVal = x;
        yVal = y;
    }

    /**
     * xVal Getter
     * @return xVal the horizontal value of the plane
     */
    public int getX() {
        return xVal;
    }

    /**
     * yVal Getter
     * @return yVal the vertical value of the plane
     */
    public int getY() {
        return yVal;
    }

}
