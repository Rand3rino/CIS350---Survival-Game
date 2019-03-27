package com.mygdx.game.Screens;


import com.badlogic.gdx.Screen;
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
import entity.Walker;
import entity.Player;
import entity.Runner;


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
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("core/assets/map assets/battleMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);


        collision = (TiledMapTileLayer) map.getLayers().get("Collision");

        entities.add(p = new Player(300, 400, collision, null));
        entities.add(new Walker(200,50,collision, p));
        entities.add(new Walker(220,50,collision, p));
        entities.add(new Runner(290,70,collision, p));
        entities.add(new Runner(250,40,collision, p));

    }

    @Override
    public void show() {

    }

    public void handleInput(float deltaTime) {

    }

    public void update () {
        super.update(deltaTime);
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float deltaTime) {
        update();
//        Gdx.gl.glClearColor(0,0,0,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.begin();
        super.render(gameCam, game.batch);
        game.batch.end();

        //        game.batch.setProjectionMatrix(gameCam.combined);
//        game.batch.begin();
//        game.batch.draw(texture,0,0);
//        game.batch.end();
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
}
