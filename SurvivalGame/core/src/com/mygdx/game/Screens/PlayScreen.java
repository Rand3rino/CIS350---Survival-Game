package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private OrthographicCamera playCam;
    private Viewport gamePort;
    private Hud hud;
    private Music music;
    private boolean pause = false;
    private Texture text_pause;
    private Sprite sprite_pause;

    private TmxMapLoader mapLoader;


    private boolean wave2, wave3, waveFinal;

    public PlayScreen (SurvivalGame game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, gameCam);
        hud = new Hud(game.batch);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Boss Battle #3 V2.wav"));
        music.setVolume(0.1f);
        music.setLooping(true);
        music.play();

        wave2 = false;
        wave3 = false;
        waveFinal = false;

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map assets/battleMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);


        collision = (TiledMapTileLayer) map.getLayers().get("Collision");

        entities.add(p = new Player(300, 400, collision, null));
        entities.add(new Walker(200,50,collision, p));
        entities.add(new Walker(220,50,collision, p));
        entities.add(new Runner(290,70,collision, p));
        entities.add(new Runner(250,40,collision, p));

        text_pause = new Texture(Gdx.files.internal("pause.png"));
        sprite_pause = new Sprite(text_pause);


    }

    @Override
    public void show() {

    }

    public void handleInput(float deltaTime) {

    }

    public void update () {

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            pause();
            music.stop();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            super.update(deltaTime);
            gameCam.update();
            renderer.setView(gameCam);
        }

        spawnWaves();

        if (hud.getEnemyCount() == 0){
            game.setScreen(new WinScreen(game));
            dispose();
        }
        if (p.health.isDead()) {
            game.setScreen(new LoseScreen(game));
            dispose();
        }

    }

    private void spawnWaves() {
        if (!wave2 && hud.getEnemyCount() == 8) {
            wave2 = true;
            entities.add(new Runner(290, 70, collision, p));
            entities.add(new Runner(250, 40, collision, p));
            entities.add(new Runner(70, 290, collision, p));
            entities.add(new Runner(40, 250, collision, p));
        }
        else if (!wave3 && hud.getEnemyCount() == 4) {
            wave3 = true;
            entities.add(new Walker(290, 70, collision, p));
            entities.add(new Walker(250, 40, collision, p));
            entities.add(new Walker(40, 250, collision, p));
        }
        else if (!waveFinal && hud.getEnemyCount() == 1){
            waveFinal = true;
            // TODO Release the SLIME
        }
    }

    @Override
    public void render(float deltaTime) {

        if(pause){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                resume();
                music.play();
            }

        } else {
            update();
        }
            renderer.render();
            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();
            game.batch.begin();
            super.render(gameCam, game.batch);

            if(pause){
                game.batch.draw(text_pause, 200,200,400,400);
            }
            game.batch.end();
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
        pause = true;
    }

    @Override
    public void resume() {
        pause = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        music.dispose();
        text_pause.dispose();

    }
}
