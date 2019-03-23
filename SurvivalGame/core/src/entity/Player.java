<<<<<<< HEAD
<<<<<<< HEAD
package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Collision;
import com.mygdx.game.Hud.Hud;
import com.mygdx.game.Screens.PlayScreen;

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

    // TODO
    Collision rect;

    // TODO
    Texture image;

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
        image =  new Texture("core/assets/playerMoveAssets/down2.png");
        this.rect = new Collision(getX(),getY(),getWidth(),getHeight());
        this.collision = map;
        sprintBar = sprintBarMax;
        punchBar = punchBarMax;
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
    }

    /******************************************************************
     * playerPunch will handle if player presses the attack button
     *****************************************************************/
    private void playerPunch() {

        // Perform a punch upon button press if attack bar if full
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (punchBar == punchBarMax) {

                // Update sprite image.
                imgPunch();

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

        if (left >= 30 ){
            image = new Texture("core/assets/playerMoveAssets/left1.png");
        }
        else if (left >= 20) {
            image = new Texture( "core/assets/playerMoveAssets/left2.png");
        }
        else if (left >= 10){
            image = new Texture("core/assets/playerMoveAssets/left3.png");
        }
        else if (left >= 0){
            image = new Texture("core/assets/playerMoveAssets/left2.png");
         }
        left++;
        left = left % 40;
    }

    private void imgRight(){

        if (right >= 30){
            image = new Texture("core/assets/playerMoveAssets/right1.png");
        }
        else if (right >= 20){
            image = new Texture("core/assets/playerMoveAssets/right2.png");
        }
        else if(right >= 10){
            image = new Texture("core/assets/playerMoveAssets/right3.png");
        }
        else if(right >= 0){
            image = new Texture("core/assets/playerMoveAssets/right2.png");
        }
        right++;
        right = right % 40;
    }

    private void imgDown(){
        if (down >= 30){
            image = new Texture("core/assets/playerMoveAssets/down1.png");
        }
        else if (down >= 20) {
            image = new Texture("core/assets/playerMoveAssets/down3.png");
        }
        else if (down >= 10){
            image = new Texture("core/assets/playerMoveAssets/down2.png");
        }
        else if (down >= 0){
            image = new Texture("core/assets/playerMoveAssets/down3.png");
        }
        down++;
        down = down % 40;
    }

    private void imgUp() {
        if (up >= 30){
            image = new Texture("core/assets/playerMoveAssets/up1.png");
        }
        else if (up >= 20){
            image = new Texture ("core/assets/playerMoveAssets/up2.png");
        }
        else if (up >= 10){
            image = new Texture("core/assets/playerMoveAssets/up3.png");
        }
        else if (up >= 0){
            image = new Texture("core/assets/playerMoveAssets/up2.png");
        }
        up++;
        up = up % 40;
    }
}
=======
=======
>>>>>>> 7894b78271b1275de297072bab92053815dc178e
package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Collision;
import com.mygdx.game.Hud.Hud;
import com.mygdx.game.Screens.PlayScreen;

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

    // TODO
    Collision rect;

    // TODO
    Texture image;

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
        image =  new Texture("core/assets/playerMoveAssets/down2.png");
        this.rect = new Collision(getX(),getY(),getWidth(),getHeight());
        this.collision = map;
        sprintBar = sprintBarMax;
        punchBar = punchBarMax;
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
    }

    /******************************************************************
     * playerPunch will handle if player presses the attack button
     *****************************************************************/
    private void playerPunch() {

        // Perform a punch upon button press if attack bar if full
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            if (punchBar == punchBarMax) {

                // Update sprite image.
                imgPunch();

                // Reset attack bar
                punchBar = 0;
            }
    }

    /******************************************************************
     * playerSprint will handle if player presses the sprint button
     * while selecting a direction
     *
     * @param deltaTime Game time cycle
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

    /******************************************************************
     * playerSprint will handle if player presses a directional button
     * without the sprint button.
     *
     * @param deltaTime Game time cycle
     *****************************************************************/
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
    /**
     * @param batch
     */
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    /**
     * Grabs players current tile position and checks its properties
     * @param x
     * @param y
     * @return
     */
    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = map.getCell((int) (x / map.getTileWidth()), (int) (y / map.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    /**
     * @return
     */
    // Checks if the player is colliding with tile on the right
    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += map.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    /**
     * @return
     */
    // Checks if it collides on the left
    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += map.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    /**
     * @return
     */
    // Checks for collision above the player
    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += map.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;

    }

    /**
     * @return
     */
    // Checks collision below the player
    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += map.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }

    /******************************************************************
     * imgPunch updates to sprite to an attack image
     *****************************************************************/
    private void imgPunch() {
        image = new Texture("core/assets/playerMoveAssets/Green.PNG");
    }


    //TODO add 4th animation stop? L C R C loop


    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
    private void imgDown(){
        if (down >= 30){
            image =  new Texture("core/assets/playerMoveAssets/down1.png");
        }
        else if (down >= 20) {
            image = new Texture("core/assets/playerMoveAssets/down3.png");
        }
        else if (down >= 10){
            image =  new Texture("core/assets/playerMoveAssets/down2.png");
        }
        else if (down >= 0){
            image = new Texture("core/assets/playerMoveAssets/down3.png");
        }
        down++;
        down = down % 40;
    }

    private void imgUp() {
        if (up >= 30){
            image =  new Texture("core/assets/playerMoveAssets/up1.png");
        }
        else if (up >= 20){
            image = new Texture ("core/assets/playerMoveAssets/up3.png");
        }
        else if (up >= 10){
            image =  new Texture("core/assets/playerMoveAssets/up2.png");
        }
        else if (up >= 0){
            image =  new Texture("core/assets/playerMoveAssets/up3.png");
        }
        up++;
        up = up % 40;
    }
}
<<<<<<< HEAD
>>>>>>> 7894b78271b1275de297072bab92053815dc178e
=======
>>>>>>> 7894b78271b1275de297072bab92053815dc178e
