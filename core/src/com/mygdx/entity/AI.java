package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameMap;

public class AI extends Entity {

    private static final int speed = 1;
    Texture image;

    public AI (float x, float y, GameMap map){
        super(x,y,EntityType.COMPUTER, map);
        image = new Texture ("C:\\Users\\Rand3\\Desktop\\CIS350\\core\\assets\\Battle_VM_single.png");
    }


    public void update (float deltaTime) {
        long t = System.currentTimeMillis();
        long end = t+500;

        System.out.println(t+"\t"+end);
//        while(System.currentTimeMillis() < end) {
//            moveX(-speed * deltaTime);
////            moveX(speed * deltaTime);
//            System.out.println(t);
//            try {
//                Thread.sleep(50);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

}
