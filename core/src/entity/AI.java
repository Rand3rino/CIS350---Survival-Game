package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameMap;
import com.badlogic.gdx.math.Vector2;

public class AI extends Entity {

    private static final int speed = 1;
    Texture image;
    Vector2 vector = new Vector2();

    public AI (float x, float y, GameMap map){
        super(x,y,EntityType.COMPUTER, map);
        image = new Texture ("C:\\Users\\Rand3\\Desktop\\CIS350\\core\\assets\\Battle_VM_single.png");
    }


    public void update (float deltaTime) {


        /** AI can move in a square **/
//        if (pos.x  < 400 && pos.y == 300)
//            moveX(speed);
//        else if (pos.x == 400 && pos.y < 400)
//            moveY(speed);
//        else if (pos.x > 300 && pos.y == 400)
//            moveX(-speed);
//        else
//            moveY(-speed);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

}
