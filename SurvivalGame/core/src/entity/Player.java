package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Collision;


public class Player extends Entity {
    private int left = 1, right = 1, up = 1, down = 1;
    private static final int speed = 1;
    private static final int sprint = 2;
    private static final int sprintBarMax = 100;
    private static final int punchBarMax = 100;
    private int sprintBar;
    private int punchBar;
    Collision rect;
    Texture image;
    TiledMapTileLayer collision;


    // The only change here is the map
    public Player (float x, float y, TiledMapTileLayer map, Entity e){

        super(x,y,EntityType.PLAYER, map, e);
        image =  new Texture("core/assets/playerMoveAssets/down2.png");
        this.rect = new Collision(getX(),getY(),getWidth(),getHeight());
        this.collision = map;
        sprintBar = sprintBarMax;
        punchBar = punchBarMax;
    }
    public int getSprintBar() {
        return sprintBar;
    }

    public int getPunchBar() {
        return punchBar;
    }

    public void update (float deltaTime){
        if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            if (sprintBar < sprintBarMax)
                sprintBar++;
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (punchBar < punchBarMax)
                punchBar++;
        playerSprint(deltaTime);
        playerWalk(deltaTime);
        playerPunch();
    }

    private void playerPunch() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (punchBar == punchBarMax) {
                imgPunch();
                punchBar = 0;
            }
    }

    // Handles if players chooses to sprint in a direction.
    private void playerSprint(float deltaTime) {

        // Run Left
        if (Gdx.input.isKeyPressed(Input.Keys.A)
                && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if(!collidesLeft() && sprintBar > 0) {
                moveX(-speed * sprint * deltaTime);
                rect.move(getX(), getY());
                sprintBar--;
            }
            imgRight();
        }a

        // Run Right
        if (Gdx.input.isKeyPressed(Input.Keys.D)
                && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if(!collidesRight() && sprintBar > 0) {
                moveX(speed * sprint * deltaTime);
                rect.move(getX(), getY());
                sprintBar--;
            }
            imgRight();
        }

        // Run Up
        if (Gdx.input.isKeyPressed(Input.Keys.W)
                && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if(!collidesTop() && sprintBar > 0) {
                moveY(speed * sprint * deltaTime);
                rect.move(getX(), getY());
                sprintBar--;
            }
            imgUp();
        }

        // Run Down
        if (Gdx.input.isKeyPressed(Input.Keys.S)
                && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if(!collidesBottom() && sprintBar > 0) {
                moveY(-speed * sprint * deltaTime);
                rect.move(getX(), getY());
                sprintBar--;
            }
            imgDown();
        }
    }

    // Handle if player chooses to walk in a direction.
    private void playerWalk(float deltaTime) {

        // Walk Left
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            if(!collidesLeft()) {
                moveX(-speed * deltaTime);
                rect.move(getX(), getY());
            }
            imgLeft();
        }

        // Walk Right
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            if(!collidesRight()) {
                moveX(speed * deltaTime);
                rect.move(getX(), getY());
            }
            imgRight();
        }

        // Walk Up
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            if(!collidesTop()) {
                moveY(speed * deltaTime);
                rect.move(getX(), getY());
            }
            imgUp();
        }

        // Walk Down
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            if(!collidesBottom()) {
                moveY(-speed * deltaTime);
                rect.move(getX(), getY());
            }
            imgDown();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    // Grabs players current tile position and checks its properties
    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = map.getCell((int) (x / map.getTileWidth()), (int) (y / map.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    // Checks if the player is colliding with tile on the right
    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += map.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    // Checks if it collides on the left
    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += map.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    // Checks for collision above the player
    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += map.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;

    }

    // Checks collision below the player
    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += map.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }

    private void imgPunch() {
        image = new Texture("core/assets/playerMoveAssets/Green.PNG");
    }

    private void imgLeft(){

        if (left >= 20 ){
            image = new Texture("core/assets/playerMoveAssets/left1.png");
        }
        else if (left >= 10){
            image = new Texture("core/assets/playerMoveAssets/left2.png");
        }
        else if (left >= 0){
            image =  new Texture("core/assets/playerMoveAssets/left3.png");
         }
        left++;
        left = left % 30;
    }

    private void imgRight(){
        if (right >= 20){
            image = new Texture("core/assets/playerMoveAssets/right1.png");
        }
        else if(right >= 10){
            image =  new Texture("core/assets/playerMoveAssets/right2.png");
        }
        else if(right >= 0){
            image =  new Texture("core/assets/playerMoveAssets/right3.png");
        }
        right++;
        right = right % 30;
    }

    private void imgDown(){
        if (down >= 20){
            image =  new Texture("core/assets/playerMoveAssets/down1.png");
        }
        else if (down >= 10){
            image =  new Texture("core/assets/playerMoveAssets/down2.png");
        }
        else if (down >= 0){
            image = new Texture("core/assets/playerMoveAssets/down3.png");
        }
        down++;
        down = down % 30;
    }

    private void imgUp(){
        if (up >= 20){
            image =  new Texture("core/assets/playerMoveAssets/up1.png");
        }
        else if (up >= 10){
            image =  new Texture("core/assets/playerMoveAssets/up2.png");
        }
        else if (up >= 0){
            image =  new Texture("core/assets/playerMoveAssets/up3.png");
        }
        up++;
        up = up % 30;
    }
}
