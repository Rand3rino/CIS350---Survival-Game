package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameMap;

public class Player extends Entity  {
    private static final int speed = 10;
    Texture image;

    public Player (float x, float y, GameMap map){
            super(x,y,EntityType.PLAYER, map);
            image = new Texture ("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\Battle_OPM_single.png");
    }
    public void update (float deltaTime){
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
        moveX (-speed * deltaTime);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            moveX (speed * deltaTime);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            moveY (speed *deltaTime);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            moveY (-speed *deltaTime);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }
}
