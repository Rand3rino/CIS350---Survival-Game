package com.mygdx.game;

public class Collision {

    float x, y;
    int width, height;

    public Collision(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean doesCollide(Collision rect) {
        return x < rect.x + rect.width && y < rect.y + rect.height
                && x + width > rect.x && y + height > rect.height;
    }
}