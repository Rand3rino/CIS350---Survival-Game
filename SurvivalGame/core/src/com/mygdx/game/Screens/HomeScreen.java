package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SurvivalGame;

/**
 * The opening screen that displays the title of the game,
 * the developer names and the course name
 * @author Randy Nguyen, Edited by Scott Nguyen
 * @version 1.1
 */

public class HomeScreen implements Screen {

    /**
     * The viewing port for the home screen
     */
    private Viewport gamePort;

    /**
     * overlay to set the gameport
     */
    private Stage stage;

    /**
     * Engine variable for the game
     */
    private Game game;

    /**
     * Sound effects
     */
    private Sound sound;

    /**
     * Music
     */
    private Music music;

    /**
     * Variable for the camera
     */
    private OrthographicCamera cam;

    /**
     * The background map
     */
    private Texture loadMap;

    /**
     * Constructor for the HomeScreen class
     * @param game the game class in the engine
     */
    public HomeScreen(Game game) {
        this.game = game;
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(gamePort, ((SurvivalGame) game).batch);

        cam = new OrthographicCamera();
        cam.position.set(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, 0);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/8Bit The Hero.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);

        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hits/1.ogg"));

        loadMap = new Texture(Gdx.files.internal("map assets/Title.png"));

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.BLACK);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("One Punch Man", font);
        Label playAgainLabel = new Label("Press ENTER to Play", font);
        Label authorLabel = new Label("Ramell Collins, Scott Nguyen, Isfar Baset, & Randy Nguyen", font);
        Label courseLabel = new Label("For CIS 350-01: Introduction to Software Engineering Winter 2019", font);
        titleLabel.setFontScale(2);
        playAgainLabel.setFontScale(1.5f);
        authorLabel.setFontScale(1.5f);
        courseLabel.setFontScale(1.5f);

        table.add(titleLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(50f);
        table.row();
        table.add(authorLabel).expandX().padTop(50f);
        table.row();
        table.add(courseLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    /**
     * Method to show the home screen
     */
    @Override
    public void show() {

    }

    /**
     * method that renders the stage and labels
     * @param delta amount of time
     */
    @Override
    public void render(float delta) {

        // plays the music
        music.play();

        // Checks if ENTER is pressed to change screens
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {

            // Plays the sound effect
            sound.play();

            // Changes the screen
            game.setScreen(new PlayScreen((SurvivalGame) game));

            // Disposes the HomeScreen variables
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws the background tiled map
        ((SurvivalGame) game).batch.begin();
        ((SurvivalGame) game).batch.draw(loadMap, 0, 0);
        ((SurvivalGame) game).batch.end();
        stage.draw();
    }

    /**
     * resizes the current screen
     * @param width new width of screen
     * @param height new height of screen
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * pauses the current screen
     */
    @Override
    public void pause() {

    }

    /**
     * resumes the home screen
     */
    @Override
    public void resume() {

    }

    /**
     * hides the home screen
     */
    @Override
    public void hide() {

    }

    /**
     * disposes of assets
     */
    @Override
    public void dispose() {
        music.dispose();
        sound.dispose();
        loadMap.dispose();
    }
}
