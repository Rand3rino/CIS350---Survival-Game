package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import Logic.PathFinder;

public class Runner extends Entity {
    private int left = 1, right = 1, up = 1, down = 1;

    PathFinder path;
    private static final int speed = 2;
    Texture image;
    Vector2 vector = new Vector2();
    Entity player;
    long time = System.currentTimeMillis();
    long start;

    public Runner (float x, float y, TiledMapTileLayer map, Entity e){
        super(x,y,EntityType.COMPUTER, map, e);
        player = e;
        image = new Texture ("vaccine man movement assets/down1.png");
        path = new PathFinder(map);
        start = time;
    }

    public void update (float deltaTime) {


        int move = path.minDistance((int) getX(), (int) getY(), player);

        if (move == 0) {
            moveX(-speed);
            if(ladder((int)getX(), (int)getY()))
                imgUp();
            else
                imgLeft();
        }
        else if (move == 1) {
            moveX(speed);
            if(ladder((int)getX(), (int)getY()))
                imgUp();
            else
                imgRight();
        }
        else if (move == 2) {
            moveY(-speed);
            if(ladder((int)getX(), (int)getY()))
                imgUp();
            else
                imgDown();
        }
        else if (move == 3) {
            moveY(speed);
            imgUp();
        }
    }



    //if locked in combat
    // AI can move in a square **/
//        if (pos.x  < 400 && pos.y == 300)
//            moveX(speed);
//        else if (pos.x == 400 && pos.y < 400)
//            moveY(speed);
//        else if (pos.x > 300 && pos.y == 400)
//            moveX(-speed);
//        else
//            moveY(-speed);




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

    private void imgLeft(){

        if (left >= 30 ){
            image = new Texture("vaccine man movement assets/left1.png");
        }
        else if (left >= 20){
            image =  new Texture("vaccine man movement assets/left2.png");
        }
        else if (left >= 10){
            image = new Texture("vaccine man movement assets/left3.png");
        }
        else if (left >= 0){
            image =  new Texture("vaccine man movement assets/left2.png");
        }
        left++;
        left = left % 40;
    }

    /**
     *
     */
    private void imgRight(){
        if (right >= 30){
            image = new Texture("vaccine man movement assets/right1.png");
        }
        else if(right >= 20){
            image =  new Texture("vaccine man movement assets/right2.png");
        }
        else if(right >= 10){
            image =  new Texture("vaccine man movement assets/right3.png");
        }
        else if(right >= 0){
            image =  new Texture("vaccine man movement assets/right2.png");
        }
        right++;
        right = right % 40;
    }

    /**
     *
     */
    private void imgDown(){
        if (down >= 30){
            image =  new Texture("vaccine man movement assets/down1.png");
        }
        else if (down >= 20) {
            image = new Texture("vaccine man movement assets/down2.png");
        }
        else if (down >= 10){
            image =  new Texture("vaccine man movement assets/down3.png");
        }
        else if (down >= 0){
            image = new Texture("vaccine man movement assets/down2.png");
        }
        down++;
        down = down % 40;
    }

    private void imgUp() {
        if (up >= 30){
            image =  new Texture("vaccine man movement assets/up1.png");
        }
        else if (up >= 20){
            image = new Texture ("vaccine man movement assets/up2.png");
        }
        else if (up >= 10){
            image =  new Texture("vaccine man movement assets/up3.png");
        }
        else if (up >= 0){
            image =  new Texture("vaccine man movement assets/up2.png");
        }
        up++;
        up = up % 40;
    }



}