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
import entity.*;

import java.util.Iterator;

/**
 * Screen for battling monsters and taking names
 * @author Randy Nguyen, Edited by Scott Nguyen, Ramell Collins, Isfar Basett
 * @version 1.1
 */
public class PlayScreen extends GameMap implements Screen {

    /**
     * Variable for the tiled map
     */
    TiledMap map;

    /**
     * Variable for the map renderer
     */
    OrthogonalTiledMapRenderer renderer;

    /**
     * Variable for map properties
     */
    MapProperties mapProp;

    /**
     * The amount of Time
     */
    float deltaTime = 1;

    /**
     * Player entity
     */
    Player p;

    /**
     * Map layer containing collision properties
     */
    TiledMapTileLayer collision;

    /**
     * State of Game variable
     */
    private SurvivalGame game;

    /**
     * Camera variable
     */
    private OrthographicCamera gameCam;

    /**
     * Viewport variable
     */
    private Viewport gamePort;

    /**
     * HUD variable
     */
    private Hud hud;

    /**
     * Music variable
     */
    private Music music;

    /**
     * boolean variable for pause when ESC is pressed
     */
    private boolean pause = false;

    /**
     * boolean for the final update before changing screens
     */
    private boolean finalUpdate = false;

    /**
     * boolean for the second to final update before changing screens
     */
    private boolean secondToFinalUpdate = false;

    /**
     * Variable for the pause texture
     */
    private Texture text_pause;


    /**
     * Engine variable for loading the map
     */
    private TmxMapLoader mapLoader;

    /**
     * Boolean variable for the first wave of enemies
     */
    private boolean wave1;

    /**
     * boolean variable for the second waves of enemies
     */
    private boolean wave2;

    /**
     * Boolean variable for the third wave of enemies
     */
    private boolean wave3;

    /**
     * Boolean variables for the final wave of enemies
     */
    private boolean waveFinal;

    /**
     * Constructor for the play screen class
     * @param game current state of the game
     */
    public PlayScreen(SurvivalGame game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, gameCam);
        hud = new Hud(game.batch);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Boss Battle #3 V2.wav"));
        music.setVolume(0.1f);
        music.setLooping(true);
        music.play();

        wave1 = true;
        wave2 = false;
        wave3 = false;
        waveFinal = false;

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map assets/battleMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        collision = (TiledMapTileLayer) map.getLayers().get("Collision");
        entities.add(p = new Player(600, 600, collision, null));
        text_pause = new Texture(Gdx.files.internal("pause.png"));
    }

    /**
     * method to show the play screen
     */
    @Override
    public void show() {

    }

    /**
     * method the updates the game and spawns the waves of enemies
     */
    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {

            // Calls the pause method
            pause();
            music.stop();

            // Suspends the game for 0.1 seconds
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
    }


    private void spawnWaves() {
        if (wave1) {

            // Sets the wave1 variable to false so that it doesn't spawn again
            wave1 = false;

            // Adds two Walker entities to entities array list as the first wave of enemies
            entities.add(new Walker(530, 100, collision, p, this));
            entities.add(new Walker(430, 120, collision, p, this));
        }
        else if (!wave2 && hud.getEnemyCount() == 8) {

            // Sets to true so that the entities does not spawn again
            wave2 = true;

            // Adds 2 Walkers, 1 Runner, and a potion
            entities.add(new Walker(290, 70, collision, p, this));
            entities.add(new Walker(250, 40, collision, p, this));
            entities.add(new Potion(290, 50, collision, p));
            entities.add(new Runner(70, 290, collision, p, this));
        }
        else if (!wave3 && hud.getEnemyCount() == 5) {

            // Sets wave3 to true so that the enemies won't spawn again
            wave3 = true;

            // Adds 2 Walkers and 2 Runners to the entities array list
            entities.add(new Walker(290, 70, collision, p, this));
            entities.add(new Walker(250, 40, collision, p, this));
            entities.add(new Runner(70, 290, collision, p, this));
            entities.add(new Runner(700, 570, collision, p, this));
        }
        else if (!waveFinal && hud.getEnemyCount() == 1) {

            // Stops current music and sets to new audio file
            music.stop();
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/8Bit The Hero.mp3"));
            music.setVolume(0.3f);
            music.play();
            waveFinal = true;

            // Spawns the final boss
            entities.add(new Slime(300, 250, collision, p, this));
        }
    }

    /**
     * Renders the playable screen
     * @param deltaTime the amount of time
     */
    @Override
    public void render(float deltaTime) {

        if (pause) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                resume();
                music.play();
            }
        } else
            update();

        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.begin();
        super.render(gameCam, game.batch);

        if (pause)
            game.batch.draw(text_pause, 200, 200, 400, 400);

        game.batch.end();

        // Game will stop for 2 seconds before proceeding to LoseScreen
        if (p.health.isDead() && finalUpdate) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.setScreen(new LoseScreen(game));
            dispose();
        }

        // Game will stop for 2 seconds before proceeding to WinScreen
        if (hud.getEnemyCount() == 0 && finalUpdate) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.setScreen(new WinScreen(game));
            dispose();
        }

        // Let the game do two more loops to render last image changes.
        // Game ends when either player dies or all enemies die.
        if ((p.health.isDead() || hud.getEnemyCount() == 0) && !secondToFinalUpdate)
            secondToFinalUpdate = true;
        else if ((p.health.isDead() || hud.getEnemyCount() == 0) && secondToFinalUpdate)
            finalUpdate = true;
    }

    /**
     * Resizes the screen
     * @param width amount of width to change
     * @param height amount of height to change
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    /**
     * Getter method for pixel width of screen
     * @return pixel amount for width of screen
     */
    // FOUND HERE: https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
    public int getWidth() {
        int mapWidth = mapProp.get("width", Integer.class);
        int tileWidth = mapProp.get("tilewidth", Integer.class);
        return mapWidth * tileWidth;
    }

    /**
     * Getter method for pixel height of screen
     * @return pixel amount for height of screen
     */
    // FOUND HERE: https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
    public int getHeight() {
        int mapHeight = mapProp.get("height", Integer.class);
        int tileHeight = mapProp.get("tileheight", Integer.class);
        return mapHeight * tileHeight;
    }

    /**
     * Getter method to get tile layer
     * @return number representing layer
     */
    public int getLayer() {
        return 0;
    }

    /**
     * Method to pause the game
     */
    @Override
    public void pause() {
        pause = true;
    }

    /**
     * Method to resume the game
     */
    @Override
    public void resume() {
        pause = false;
    }

    /**
     * Method to hide the play screen
     */
    @Override
    public void hide() {

    }

    /**
     * Method to dispose the play screen assets
     */
    @Override
    public void dispose() {
        map.dispose();
        music.dispose();
        text_pause.dispose();
    }

    /**
     * Generates an array of the tiled map
     * @return array of the map
     */
    public int[][] devPath() {
        Iterator<Entity> iterator = entities.iterator();
        int playerX = (int) p.getX() / 32;
        int playerY = (int) p.getY() / 32;

        int loc[][] = new int[collision.getWidth()][collision.getHeight()]; // array generated from map blocks
        for (int i = 0; i < collision.getHeight(); i++) {
            for (int j = 0; j < collision.getWidth(); j++) {
                if (isCellBlocked(i, j))
                    loc[i][j] = 2; // sets all blocked locations on array map to 2
                else
                    loc[i][j] = 0; // sets all accessible spaces to 0
            }
        }

        for (Entity ent : entities) {
            int AIX = (int) ent.getX() / 32;
            int AIY = (int) ent.getY() / 32;
            loc[AIX][AIY] = 4;
        }
        loc[playerX][playerY] = 3;

        return loc;
    }

    private boolean isCellBlocked(int x, int y) {
        TiledMapTileLayer.Cell cell = collision.getCell(x, y);
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }
}

