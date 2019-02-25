package com.mygdx.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.WorldMap.Collision;


public class Player extends Entity {

    private static final int speed = 3;
    Collision rect;
    Texture image;
    TiledMapTileLayer collision;


    // The only change here is the map
    public Player (float x, float y, TiledMapTileLayer map, Entity e){

        super(x,y,EntityType.PLAYER, map, e);
        image = new Texture ("Battle_OPM_single_clear.png");
        this.rect = new Collision(getX(),getY(),getWidth(),getHeight());
        this.collision = map;


    }

    public void update (float deltaTime){

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            if(!collidesLeft()) {
                moveX(-speed * deltaTime);
                rect.move(getX(), getY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            if(!collidesRight()) {
                moveX(speed * deltaTime);
                rect.move(getX(), getY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            if(!collidesTop()) {
                moveY(speed * deltaTime);
                rect.move(getX(), getY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            if(!collidesBottom()) {
                moveY(-speed * deltaTime);
                rect.move(getX(), getY());
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    // Grabs players current tile position and checks its properties
    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = map.getCell((int) (x / map.getTileWidth()), (int) (y / map.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    // Checks if the player is colliding with tile on the right
    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += map.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    // Checks if it collides on the left
    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += map.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    // Checks for collision above the player
    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += map.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;

    }

    // Checks collision below the player
    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += map.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }

}