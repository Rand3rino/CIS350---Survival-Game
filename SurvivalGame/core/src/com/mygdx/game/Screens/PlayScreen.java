package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMap;
import com.mygdx.game.Hud.Hud;
import com.mygdx.game.SurvivalGame;
import entity.Player;

public class PlayScreen extends GameMap implements Screen {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    MapProperties mapProp;

    float deltaTime = 1;
    Player p;
    TiledMapTileLayer collision;

    private SurvivalGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;

    public PlayScreen (SurvivalGame game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(SurvivalGame.WIDTH/2, SurvivalGame.HEIGHT/2, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("core/assets/something.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        // Grabs the collision layer, wont work with other maps until I change em.
        // Also the boarder was not changed, resulting in no collision. Will fix.
        collision = (TiledMapTileLayer) map.getLayers().get("Collision");

        p = new Player(300, 400, collision, null);
//        entities.add(new AI(100,50,collision, p));
        gameCam.position.set(p.getX(),p.getY(),0);
        entities.add(p);
    }

    @Override
    public void show() {

    }

//    public void handleInput(float deltaTime) {
//
//    }

    public void update () {
        super.update(deltaTime);
        gameCam.position.x = p.getX();
        gameCam.position.y = p.getY();
        System.out.println(p.getX() + " " + p.getY());
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float deltaTime) {
        update();

        //Clear the surround area around map with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.begin();
        super.render(gameCam, game.batch);
        game.batch.end();

//                game.batch.setProjectionMatrix(gameCam.combined);
//        game.batch.begin();
//        game.batch.end();

        if(gameOver()) {
            game.setScreen(new LoseScreen(game));
            dispose();
        }
    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
    }

    public boolean gameOver() {
        if (p.isDead()) {
            return true;
        }
        return false;
    }
}
