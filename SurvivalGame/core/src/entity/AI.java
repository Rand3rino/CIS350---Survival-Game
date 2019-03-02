package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameMap;
import com.badlogic.gdx.math.Vector2;

public class AI extends Entity {

    private static final int speed = 1;
    Texture image;
    Vector2 vector = new Vector2();
    Entity player;

    public AI (float x, float y, TiledMapTileLayer map, Entity e){
        super(x,y,EntityType.COMPUTER, map, e);
        player = e;
        image = new Texture ("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\DeepSeaKingWalk.png");
    }

    public void update (float deltaTime) {

        vector = playerLocation();
        if (pos.x  < vector.x)
            moveX(speed);
        else if (pos.x > vector.x)
            moveX(-speed);
        else if (pos.y < vector.y)
            moveY(speed);
        else if (pos.y > vector.y)
            moveY(-speed);

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

    // method "move to this point" Using tiledgamemap
    public Vector2 playerLocation() {
        vector.x = player.getX();
        vector.y = player.getY();
        System.out.println(vector.x + " " + vector.y);
        return vector;

    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

}