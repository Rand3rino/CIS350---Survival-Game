package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import entity.Entity;
import entity.AI;
import entity.Player;
import java.util.ArrayList;


public class TiledGameMap extends GameMap {
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    MapProperties mapProp;
    int[][] mapArray;
    float delta = 1;
    Player p;
    TiledMapTileLayer collision;

    public TiledGameMap(){

        tiledMap = new TmxMapLoader().load("core/assets/test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        mapProp = tiledMap.getProperties();

        // Grabs the collision layer, wont work with other maps until I change em.
        // Also the boarder was not changed, resulting in no collision. Will fix.
        collision = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");

        p = new Player(200, 200, collision, null);
        createArray();
        entities = new ArrayList<Entity>();
        entities.add(new AI(200,200,collision, p));
        entities.add(p);
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
        setMapArray();
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
    }

    private void resetMapArray() {
        for (int i=0; i<getWidth(); i++)
            for (int j=0; j<getHeight(); j++)
                mapArray[i][j] = 0;
    }

    private void setMapArray() {

        // Vector position is a float, must cast to int
        int x = (int)p.getX();
        int y = (int)p.getY();

        // Set old position to 0, THERE MUST BE A BETTER WAY TO DO THIS
        resetMapArray();

        mapArray[x][y] = 1;
        //System.out.println(x + " " + y);
    }

    public int[][] getMapArray() {
        return mapArray;
    }



}