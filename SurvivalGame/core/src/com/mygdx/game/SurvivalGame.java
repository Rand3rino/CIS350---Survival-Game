package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Screens.HomeScreen;

/**
 * Sets up the basic game requirements
 * @author Scott Nguyen
 */
public class SurvivalGame extends Game {

	/** Width of the window when game is running */
	public static final int WIDTH = 736;

	/** Height of the window */
	public static final int HEIGHT = 736;

	/** Assets to be created */
	public SpriteBatch batch;

	/** Camera of the game */
	OrthographicCamera camera;


	/**
	 * Creates the window and sets the camera
	 */
	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		setScreen(new HomeScreen(this));
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
	}

	/** Renders the game */
	@Override
	public void render () { super.render(); }

	/** Disposes of the game assets */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
