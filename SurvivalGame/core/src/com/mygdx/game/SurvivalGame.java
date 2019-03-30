package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.HomeScreen;
import com.mygdx.game.Screens.PlayScreen;

public class SurvivalGame extends Game {
	public static final int WIDTH = 736;
	public static final int HEIGHT = 736;
	public SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;


	@Override
	public void create () {

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		// TODO start on homescreen for actual product
		setScreen(new HomeScreen(this));
//		setScreen(new PlayScreen(this));
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
	}

	@Override
	public void render () { super.render(); }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
