package entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;
    protected TiledMapTileLayer map;
    protected Entity player;


    public Entity(float x, float y, EntityType type, TiledMapTileLayer map, Entity e) {
        this.type = type;
        this.map = map;
        this.pos = new Vector2(x, y);
        this.player = e;
    }

    public void update(float deltaTime) {   

    }


    protected void moveX(float amount) {
        float newX = pos.x + amount;
        // if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight())){
        this.pos.x = newX;
        //}
    }

    protected void moveY(float amount) {
        float newY = pos.y + amount;
        // if (!map.doesRectCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
        this.pos.y = newY;
        //}
    }

    protected boolean ladder(float x, float y){

        TiledMapTileLayer.Cell ladder = map.getCell((int) (x / map.getTileWidth()), (int) (y / map.getTileHeight()));
        return ladder != null && ladder.getTile() != null && ladder.getTile().getProperties().containsKey("ladder");

    }
    public void render(SpriteBatch batch){

    }
    public Vector2 getPos() {
        return pos;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public EntityType getType() {
        return type;
    }

    public int getHeight() {
        return type.getHeight();
    }

    public int getWidth() {
        return type.getHeight();
    }

}