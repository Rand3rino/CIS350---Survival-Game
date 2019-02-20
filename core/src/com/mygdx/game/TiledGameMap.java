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
    MapProperties mapProp;
    int[][] mapArray;
    float delta = 1;

    public TiledGameMap(){
        tiledMap = new TmxMapLoader().load("C:\\Users\\Rand3\\Desktop\\CIS350\\core\\assets\\Level0.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        mapProp = tiledMap.getProperties();

        createArray();

        entities = new ArrayList<Entity>();
        entities.add(new AI(300,300,this));
        entities.add(new Player(400, 400, this));
        mapArray[400][400] = 1;

    }

    public void render (OrthographicCamera camera, SpriteBatch batch){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.render(camera, batch);
        batch.end();
        update(delta);


    }
    public void update (float delta){
        super.update(delta);

    }
    public void dispose (){
        tiledMap.dispose();
    }

    // FOUND HERE: https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
    public int getWidth(){
        int mapWidth = mapProp.get("width", Integer.class);
        int tileWidth = mapProp.get("tilewidth", Integer.class);
        return mapWidth * tileWidth;
    }
    // FOUND HERE: https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
    public int getHeight(){
        int mapHeight = mapProp.get("height", Integer.class);
        int tileHeight = mapProp.get("tileheight", Integer.class);
        return mapHeight * tileHeight;
    }
    public int getLayer(){
        return 0;
    }

    private void createArray() {

        int width = getWidth();
        int height = getHeight();

        mapArray = new int[width][height];

        for (int i=0; i<width; i++)
            for (int j=0; j<height; j++)
                mapArray[i][j] = 0;

    }

    private void setMapArray(int x1, int y1, int x2, int y2) {
        mapArray[x1][y1] = 0;
        mapArray[x2][y2] = 1;
    }

    public int[][] getMapArray() {
        return mapArray;
    }

}