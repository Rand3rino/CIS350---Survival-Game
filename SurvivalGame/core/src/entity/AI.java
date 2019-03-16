package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameMap;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.math.Vector2;
import Logic.PathFinder;

public class AI extends Entity {

    PathFinder path;
    private static final int speed = 1;
    Texture image;
    Vector2 vector = new Vector2();
    Entity player;

    public AI (float x, float y, TiledMapTileLayer map, Entity e){
        super(x,y,EntityType.COMPUTER, map, e);
        player = e;
        image = new Texture ("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\DeepSeaKingWalk.png");
        path = new PathFinder(map);
    }

    public void update (float deltaTime) {
        Timer timer = new Timer();
        for (int i = 0; i < 5; i++) {
            int move = path.minDistance((int) getX(), (int) getY(), player);

            if (move == 0)
                moveX(-speed);
            else if (move == 1)
                moveX(speed);
            else if (move == 2)
                moveY(-speed);
            else if (move == 3)
                moveY(speed);
            timer.delay(50);
            // AI can move in a square **/
//        if (pos.x  < 400 && pos.y == 300)
//            moveX(speed);
//        else if (pos.x == 400 && pos.y < 400)
//            moveY(speed);
//        else if (pos.x > 300 && pos.y == 400)
//            moveX(-speed);
//        else
//            moveY(-speed);

        }
    }

    // method "move to this point" Using tiledgamemap
    public Vector2 playerLocation() {
        vector.x = player.getX();
        vector.y = player.getY();
        return vector;

    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

}