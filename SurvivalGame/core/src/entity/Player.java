package entity;

import Logic.HealthTracking;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Collision;
import com.mygdx.game.Hud.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import sun.reflect.annotation.ExceptionProxy;

public class Player extends Entity {

    /** Directional Variables to be used in movement */
    private int left = 1, right = 1, up = 1, down = 1;

    /** Default Character Movement Speed */
    private static final int speed = 1;

    /** Enhanced Character Speed */
    private static final int sprint = 2;

    /** Maximum Value of Character's Stamina Bar */
    private static final int sprintBarMax = 100;

    /** Energy Used in Attacks */
    private static final int punchBarMax = 100;

    /** Stamina Bar */
    private int sprintBar;

    /** Attack Bar */
    private int punchBar;

    /** Punch sound */
    private Sound punchSFX;

    public HealthTracking health = new HealthTracking(this, null, 10, 10);

    // TODO
    Collision rect;

    // TODO
    private Texture image;
    private Texture left1;
    private Texture left2;
    private Texture left3;
    private Texture right1;
    private Texture right2;
    private Texture right3;
    private Texture up1;
    private Texture up2;
    private Texture up3;
    private Texture down1;
    private Texture down2;
    private Texture down3;
    private Texture punchLeft;
    private Texture punchRight;
    private Texture laydown;
    private Texture knockback1;
    private Texture knockback2;


    // TODO
    TiledMapTileLayer collision;


    /******************************************************************
     * Player Constructor
     *
     * @param x The player's starting horizontal location
     * @param y The Player's starting vertical location
     * @param map The map that the player is placed on
     * @param e An entity object for entities to track. Player will
     *          not use this variable
     *****************************************************************/
    public Player (float x, float y, TiledMapTileLayer map, Entity e){

        super(x,y,EntityType.PLAYER, map, e);
        loadTextures();
        image = down2;
        this.rect = new Collision(getX(),getY(),getWidth(),getHeight());
        this.collision = map;
        sprintBar = sprintBarMax;
        punchBar = punchBarMax;
        punchSFX= Gdx.audio.newSound(Gdx.files.internal("sounds/hits/12.ogg"));

    }

    private void loadTextures() {
        left1 = new Texture("OPM movement assets/left1.png");
        left2 = new Texture("OPM movement assets/left2.png");
        left3 = new Texture("OPM movement assets/left3.png");
        right1 = new Texture("OPM movement assets/right1.png");
        right2 = new Texture("OPM movement assets/right2.png");
        right3 = new Texture("OPM movement assets/right3.png");
        up1 = new Texture("OPM movement assets/up1.png");
        up2 = new Texture("OPM movement assets/up2.png");
        up3 = new Texture("OPM movement assets/up3.png");
        down1 = new Texture("OPM movement assets/down1.png");
        down2 = new Texture("OPM movement assets/down2.png");
        down3 = new Texture("OPM movement assets/down3.png");
        punchLeft = new Texture("OPM movement assets/punchLeft.png");
        punchRight = new Texture("OPM movement assets/punchRight.png");
        laydown = new Texture("OPM movement assets/dead.png");
        knockback1 = new Texture("OPM movement assets/knockback1.png");
        knockback2 = new Texture("OPM movement assets/knockback2.png");
    }

    /******************************************************************
     * Sprint Bar getter
     *
     * @return sprintBar Value of the character's stamina bar.
     *****************************************************************/
    public int getSprintBar() {
        return sprintBar;
    }

    /******************************************************************
     * Punch Bar getter
     *
     * @return punchBar value of the character's attack bar.
     *****************************************************************/
    public int getPunchBar() {
        return punchBar;
    }

    /******************************************************************
     * update method handles character movement as well as stamina
     * and attack bar levels.
     *
     * @param deltaTime The amount of time for each move
     *****************************************************************/
    public void update (float deltaTime){

        if (!health.isDead()) {
            // If the sprint button is not held, charge stamina till max
            if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
                if (sprintBar < sprintBarMax)
                    sprintBar++;

            // If the attack button is not held, charge the attack till max
            if (!Gdx.input.isKeyPressed(Input.Keys.SPACE))
                if (punchBar < punchBarMax)
                    punchBar++;

            // Handle if character is sprinting
            playerSprint(deltaTime);

            // Handle if character is walking
            playerWalk(deltaTime);

            // Handle if character is punching
            // TODO need deltaTime?
            playerPunch();

            Hud.changeStamina(sprintBar);
            Hud.changeAttack(punchBar);
            Hud.changeHealth(health.getHealth());
        }
        else {
            image = laydown;
        }
    }

    /******************************************************************
     * playerPunch will handle if player presses the attack button
     *****************************************************************/
    private void playerPunch() {

        // Perform a punch upon button press if attack bar is full
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (punchBar == punchBarMax && !ladder(getX(),getY())) {

                // Update sprite image.
                imgPunch();
                punchSFX.play(0.15f);

                // Reset attack bar
                punchBar = 0;
            }
    }

    /******************************************************************
     * playerSprint will handle if player presses the sprint button
     * while selecting a direction
     *****************************************************************/
    private void playerSprint(float deltaTime) {

        // Run Left
        if (Gdx.input.isKeyPressed(Input.Keys.A)
                && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if(!collidesLeft() && sprintBar > 0) {
                moveX(-speed * sprint * deltaTime);
                rect.move(getX(), getY());
                sprintBar--;
            }
            imgLeft();
        }

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
            if(ladder(getX(),getY())){
                imgUp();
            } else {
                imgLeft();
            }
        }

        // Walk Right
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            if(!collidesRight()) {
                moveX(speed * deltaTime);
                rect.move(getX(), getY());
            }
            if(ladder(getX(),getY())){
                imgUp();
            } else {
                imgRight();
            }
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
            if(ladder(getX(),getY())){
                imgUp();
            } else {
                imgDown();
            }
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
        if (image.equals(left1) || image.equals(left2) || image.equals(left3))
            //TODO some way to pause. Animation is too quick
            image = punchLeft;
        else if (image.equals(right1) || image.equals(right2) || image.equals(right3))
            image = punchRight;

//        // TODO remove after test
//        Hud.decrementEnemy();
//        hit(1);
    }

    private void imgLeft(){

        if (left >= 30 )
            image = left1;
        else if (left >= 20)
            image = left2;
        else if (left >= 10)
            image = left3;
        else if (left >= 0)
            image = left2;
        left++;
        left = left % 40;
    }

    private void imgRight(){

        if (right >= 30)
            image = right1;
        else if (right >= 20)
            image = right2;
        else if(right >= 10)
            image = right3;
        else if(right >= 0)
            image = right2;
        right++;
        right = right % 40;
    }

    private void imgDown(){
        if (down >= 30)
            image = down1;
        else if (down >= 20)
            image = down2;
        else if (down >= 10)
            image = down3;
        else if (down >= 0)
            image = down2;
        down++;
        down = down % 40;
    }

    private void imgUp() {
        if (up >= 30)
            image = up1;
        else if (up >= 20)
            image = up2;
        else if (up >= 10)
            image = up3;
        else if (up >= 0)
            image = up2;
        up++;
        up = up % 40;
    }


    public void hit(int damage) {
        health.decreaseHealth(damage);
    }

    // TODO vertical knockback
    public void knockback(float x, float y) {
        if (x > getX()) {
            // knockback left
            pos = new Vector2(x - 32, getY());
            image = knockback2;
        } else {
            // knockback right
            pos = new Vector2(x + 32, getY());
            image = knockback1;
        }
    }

    private void heal(int potion) {
        health.buffHealth(potion);
    }

}