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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SurvivalGame;

public class HomeScreen implements Screen {

    private Viewport gamePort;
    private Stage stage;
    private Game game;
    private Sound sound;
    private Music music;
    private OrthographicCamera cam;
    private Texture loadMap;

    public HomeScreen (Game game) {
        this.game = game;
        gamePort = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(gamePort, ((SurvivalGame) game).batch);

        cam = new OrthographicCamera();
        cam.position.set(SurvivalGame.WIDTH,SurvivalGame.HEIGHT, 0);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Title.mp3"));
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
        Label authorLabel = new Label ("Ramell Collins, Scott Nguyen, Isfar Baset, & Randy Nguyen", font);
        Label courseLabel = new Label ("For CIS 350-01: Introduction to Software Engineering Winter 2019", font);
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        music.play();
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            sound.play();
            game.setScreen(new PlayScreen((SurvivalGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ((SurvivalGame)game).batch.begin();
        ((SurvivalGame) game).batch.draw(loadMap,0,0);
        ((SurvivalGame) game).batch.end();
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
        sound.dispose();
        loadMap.dispose();

    }
}
