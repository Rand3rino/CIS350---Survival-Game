package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entity.Entity;
import java.util.ArrayList;
import java.util.Iterator;


public abstract class GameMap {

    /** Array list of entities on the current map */
    public ArrayList<Entity> entities;

    /** Constructor of GameMap Class, instantiates the array list */
    public GameMap() {
        entities = new ArrayList<Entity>();
    }

    /**
     * Renders the entities and sets the camera of the game
     * @param camera camera of the screen
     * @param batch batch of sprites the be rendered
     */
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

    /**
     * Updates the entities
     * @param delta amount of time
     */
    public void update(float delta) {
        for (Entity entity : entities)
            entity.update(delta);
    }

    /** disposes assets */
    public abstract void dispose();

    /** Getter for width */
    public abstract int getWidth();

    /** Getter for height */
    public abstract int getHeight();

    /** Getter for layer of tiled map */
    public abstract int getLayer();
}
