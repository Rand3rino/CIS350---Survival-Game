package com.mygdx.game;

/**
 * Creates a rectangle that acts as collision between entities
 * @author Scott Nguyen
 */
public class Collision {

    /**
     * Directional variable on the x axis
     */
    float x;

    /**
     * Directional variable on the y axis
     */
    float y;

    /**
     * Integer variable for the width of the rectangle
     */
    int width;

    /**
     * Integer variable for the height of the rectangle
     */
    int height;

    /**
     * Constructor for the Collision class
     * @param x position on the x axis
     * @param y position on the y axis
     * @param width width of rectangle
     * @param height height of rectangle
     */
    public Collision(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Method to move the rectangle on a 2D plane
     * @param x position on x axis
     * @param y position on the y axis
     */
    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to check if other rectangles intersect each other
     * @param rect rectangle on the entity
     * @return true or false
     */
    public boolean doesCollide(Collision rect) {
        return x < rect.x + rect.width && y < rect.y + rect.height
                && x + width > rect.x && y + height > rect.height;
    }
}