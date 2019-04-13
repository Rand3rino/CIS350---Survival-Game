package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entity.Entity;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class GameMap {

    public ArrayList<Entity> entities;

    public GameMap() {
        entities = new ArrayList<Entity>();
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity current = iterator.next();
            if (current.isDead())
                iterator.remove(); // Removes the current object.
        }
        for (Entity entity : entities)
            entity.render(batch);
    }

    public void update(float delta) {
        for (Entity entity : entities)
            entity.update(delta);
    }

    public abstract void dispose();

    public boolean doesRectCollideWithMap(float x, float y, int width, int height) {
        if (x < 0 || y < 0 || x + width > 25 || y + height > 25)
            return true;
        else
            return false;
    }

    //public abstract TileType getTileTypeByLocation(int layer, float x, float y);
    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getLayer();
}
