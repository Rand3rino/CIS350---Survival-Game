package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entity.Entity;
import entity.EntityType;
import entity.Player;

import java.util.ArrayList;

public abstract class GameMap {
    public ArrayList<Entity> entities;
    public GameMap(){
        entities = new ArrayList<Entity>();
        entities.add(new Player(300, 250, this));
    }

    public void render (OrthographicCamera camera, SpriteBatch batch){
        for (Entity entity : entities){
            entity.render(batch);
        }
    }
    public void update (float delta){
        for (Entity entity : entities){
            entity.update(delta);
            if (Gdx.input.isKeyPressed(Input.Keys.A)){
            System.out.print("I pressed A");
            }
        }
    }

    public abstract void dispose ();

    public boolean  doesRectCollideWithMap(float x, float y, int width, int height){
        if (x < 0 || y < 0 || x + width > 25 || y + height > 25)
            return  true;
        else
            return false;
    }

    //public abstract TileType getTileTypeByLocation(int layer, float x, float y);
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayer();

}
