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
    private Music music;
    private boolean pause = false;
    private boolean finalUpdate = false;
    private boolean secondToFinalUpdate = false;
    private Texture text_pause;
    private Sprite sprite_pause;
    private TmxMapLoader mapLoader;
    private boolean wave1, wave2, wave3, waveFinal;

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
        sprite_pause = new Sprite(text_pause);
    }

    @Override
    public void show() {

    }

    public void handleInput(float deltaTime) {

    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
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
    }

    private void spawnWaves() {
        if (wave1) {
            wave1 = false;
            entities.add(new Walker(530, 100, collision, p, this));
            entities.add(new Walker(430, 120, collision, p, this));
        }
        else if (!wave2 && hud.getEnemyCount() == 8) {
            wave2 = true;
            entities.add(new Walker(290, 70, collision, p, this));
            entities.add(new Walker(250, 40, collision, p, this));
            entities.add(new Potion(290, 50, collision, p));
            entities.add(new Runner(70, 290, collision, p, this));
        }
        else if (!wave3 && hud.getEnemyCount() == 5) {
            wave3 = true;
            entities.add(new Walker(290, 70, collision, p, this));
            entities.add(new Walker(250, 40, collision, p, this));
            entities.add(new Runner(70, 550, collision, p, this));
            entities.add(new Runner(700, 500, collision, p, this));
        }
        else if (!waveFinal && hud.getEnemyCount() == 1) {
            music.stop();
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/8Bit The Hero.mp3"));
            music.setVolume(0.3f);
            music.play();
            waveFinal = true;
            entities.add(new Slime(300, 250, collision, p, this));
        }
    }

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


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    /**
    *@param mapWidth - holds the map property of width
    *@param mapWidth - holds the tile property of width
    *@return mapWidth * tileWidth
    **/
    
    // FOUND HERE: https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
    public int getWidth() {
        int mapWidth = mapProp.get("width", Integer.class);
        int tileWidth = mapProp.get("tilewidth", Integer.class);
        return mapWidth * tileWidth;
    }
   
    /**
    *@param mapHeight - holds the map property of height
    *@param tileHeight - holds the tile property of height
    *@return map height * tileHeight
    **/
    
    // FOUND HERE: https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
    public int getHeight() {
        int mapHeight = mapProp.get("height", Integer.class);
        int tileHeight = mapProp.get("tileheight", Integer.class);
        return mapHeight * tileHeight;
    }

    public int getLayer() {
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

    /**
    *@param playerX - holds x parameter of player location
    *@param playerY - holds y parameter of player location
    *@param ent - iterator array for  entities type
    *@param AIX - holds x parameter of AI for iterative placement
    *@param AIY - holds y parameter of AI for iterative placement
    *@return loc[][] the array that contains the final map with all player and AI listed for pathfinder method to develop logic
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
    
    /**
    *@parameter x - current x location being cheked for tile property
    *@parameter y - current y location being checked for tile property
    *@return cell values
    */

    private boolean isCellBlocked(int x, int y) {
        TiledMapTileLayer.Cell cell = collision.getCell(x, y);
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }
}

