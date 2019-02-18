package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMap;

public class Player extends Entity  {
    private static final int speed = 1;
    private Texture image;

   private int upI = 1, downI = 1, leftI = 1, rightI = 1, lastMove;
    public Player (float x, float y, GameMap map){
            super(x,y,EntityType.PLAYER, map);
            image = new Texture ("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\down3.png");
    }
    public void update (float deltaTime){
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
        moveX (-speed * deltaTime);
        rightI = 1;
        upI = 1;
        downI = 1;
        moveLeft(leftI);
            if (leftI == 2){
                leftI = 1;
            }
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            moveX (speed * deltaTime);
            leftI = 1;
            upI = 1;
            downI = 1;
            moveRight(rightI);
            if (rightI == 2){
                rightI = 1;
            }
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)){
            moveY (speed *deltaTime);
            moveUp(upI);
            rightI = 1;
            leftI = 1;
            downI = 1;
            if (upI == 2){
                upI = 1;
            }
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)){
            moveY (-speed *deltaTime);
            moveDown(downI);
            rightI = 1;
            upI = 1;
            leftI = 1;
            downI++;
            lastMove = 4;
        }
        else{
            //down standstill
            if (lastMove == 4){
                image = new Texture ("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\down1.png");
            }
        }
    }

    public Vector2 playerLocation(){
        Vector2 loc = new Vector2();
        loc.set(this.getX(), this.getY());
        return loc;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    private void moveLeft(int i){
        if (i == 1){
            image = new Texture("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\left1.png");
        }
        else{
            image = new Texture("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\left2.png");
        }
    }

    private void moveRight(int i){
        if (i == 1){
            image = new Texture("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\right1.png");
        }
        else{
            image = new Texture("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\right2.png");
        }
    }

    private void moveUp(int i){
        if (i == 1){
            image = new Texture("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\up1.png");
        }
        else{
            image = new Texture("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\up2.png");
        }
    }

    private void moveDown(int i){

        if ((i % 2) == 1 ){
            image = new Texture ("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\down1.png");
        }
        else{
            image = new Texture ("C:\\Users\\Angel\\SurvivalGame\\core\\assets\\playerMoveAssets\\down2.png");
        }

    }
    }