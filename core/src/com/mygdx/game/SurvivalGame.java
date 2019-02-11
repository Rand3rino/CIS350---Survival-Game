package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class SurvivalGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	GameMap map1;
	OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		map1 = new TiledGameMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		map1.render(camera, batch);

		if (Gdx.input.isTouched()) {
		camera.translate(- Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
		camera.update();
		}


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		map1.dispose();
		img.dispose();
	}
}
