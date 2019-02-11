package entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMap;
public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;
    protected GameMap map;
    protected boolean grounded;

    public Entity(float x, float y, EntityType type, GameMap map) {
        this.type = type;
        this.map = map;
        this.pos = new Vector2(x, y);
    }

    public void update(float deltaTime) {

    }

    public abstract void render(SpriteBatch batch);

    protected void moveX (float amount){
        float newX = pos.x + amount;
       // if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight())){
            this.pos.x = newX;
        //}
    }

    protected void moveY (float amount) {
        float newY = pos.y + amount;
       // if (!map.doesRectCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
            this.pos.y = newY;
        //}
    }

    public Vector2 getPos() {
        return pos;
    }

    public float getX(){
        return pos.x;
    }

    public float getY(){
        return pos.y;
    }

    public EntityType getType(){
        return type;
    }

   // public boolean isGrounded(){
       // return grounded;
  //  }


    public int getHeight(){
        return type.getHeight();
    }

    public int getWidth(){
        return type.getHeight();
    }
}