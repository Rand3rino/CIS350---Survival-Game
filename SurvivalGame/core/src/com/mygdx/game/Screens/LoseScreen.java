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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SurvivalGame;

/**
 * The Lose Screen, this is displayed when the player loses the game
 * @author Randy Nguyen, Edited by Scott Nguyen
 * @version 1.1
 */
public class LoseScreen implements Screen {

    /**
     * the viewing area for the stage
     */
    private Viewport gamePort;

    /**
     * the stage for labels to be set on
     */
    private Stage stage;

    /**
     * the current state of the game
     */
    private Game game;

    /**
     * Music that plays in background
     */
    private Music music;

    /**
     * Sound effects for ENTER key
     */
    private Sound play;

    /**
     * Constructor for the LoseScreen CLass
     * @param game current state of the game
     */
    public LoseScreen(Game game) {

        // sets current state of the game
        this.game = game;
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(gamePort, ((SurvivalGame) game).batch);

        // sets the music to be played
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/bittersweet.mp3"));

        // sets the music to loop
        music.setLooping(true);

        // sets the volume of the music
        music.setVolume(0.3f);

        // sets the sound effect
        play = Gdx.audio.newSound(Gdx.files.internal("sounds/hits/Bow.wav"));

        // sets the font for the label
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        // creates a new table for the label to lay on
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        // creates the label
        Label titleLabel = new Label("WASTED", font);
        Label playAgainLabel = new Label("Press ENTER to Play Again", font);

        table.add(titleLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(50f);
        table.row();

        stage.addActor(table);
    }

    /**
     * unused show method, needed for implementation of engine Screen class
     */
    @Override
    public void show() {

    }

    /**
     * renders the stage and labels to be viewed
     * @param delta engine variable
     */
    @Override
    public void render(float delta) {

        // plays the music
        music.play();

        // checks for key press
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {

            // plays the sound effect
            play.play();

            // sets the screen back to the play screen
            game.setScreen(new PlayScreen((SurvivalGame) game));

            // method call
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    /**
     * method to resize the screen
     * @param width new width of the screen
     * @param height new height of the screen
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * pauses the lose screen
     */
    @Override
    public void pause() {

    }

    /**
     * resume method for the lose screen
     */
    @Override
    public void resume() {

    }

    /**
     * method to hide the lose screen
     */
    @Override
    public void hide() {

    }

    /**
     * disposes of the lose screen assets
     */
    @Override
    public void dispose() {
        music.dispose();
        play.dispose();
    }
}