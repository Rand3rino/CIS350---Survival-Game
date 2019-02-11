package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import entity.Entity;
import entity.Player;

import java.util.ArrayList;

public class TiledGameMap extends GameMap {
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    public TiledGameMap(){
    tiledMap = new TmxMapLoader().load("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\map1.tmx");
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    }

    public void render (OrthographicCamera camera, SpriteBatch batch){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.render(camera, batch);
        batch.end();


    }
    public void update (float delta){
        super.update(delta);

    }
    public void dispose (){
        tiledMap.dispose();
    }


    public int getWidth(){
    return 0;
    }

    public int getHeight(){
        return 0;
    }
    public int getLayer(){
        return 0;
    }

}