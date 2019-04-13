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

public class WinScreen implements Screen {

    private Viewport gamePort;
    private Stage stage;
    private Game game;
    private Music music;
    private Sound sfx;

    public WinScreen(Game game) {
        this.game = game;
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(gamePort, ((SurvivalGame) game).batch);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/8bitvictory.ogg"));
        music.setVolume(0.1f);
        music.setLooping(true);

        sfx = Gdx.audio.newSound(Gdx.files.internal("sounds/hits/swish_2.wav"));

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);


        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("Winner Winner, Discount Dinner", font);
        Label playAgainLabel = new Label("Press ENTER to Play Again", font);

        table.add(titleLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(50f);
        table.row();

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        music.play();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            sfx.play();
            game.setScreen(new PlayScreen((SurvivalGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        music.dispose();
        sfx.dispose();
    }
}