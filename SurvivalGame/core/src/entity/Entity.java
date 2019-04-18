package entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Creates the entities of the game
 * @author Ramell Collins, Edited by Scott and Radny
 */
public abstract class Entity {

    /** 2D vector representing position*/
    protected Vector2 pos;

    /** Type of Entity */
    protected EntityType type;

    /** Collision tiled map layer */
    protected TiledMapTileLayer map;

    /** Player entity */
    protected Entity player;

    /** Boolean variable representing if the entity is dead */
    protected Boolean dead = false;

    /**
     * Constructor for Entity Class
     * @param x positional variable on the x axis
     * @param y positional variable on the y axis
     * @param type type of entity
     * @param map collision tiled map layer
     * @param e the player entity
     */
    public Entity(float x, float y, EntityType type, TiledMapTileLayer map, Entity e) {
        this.type = type;
        this.map = map;
        this.pos = new Vector2(x, y);
        this.player = e;
    }

    /**
     * update the entity
     * @param deltaTime amount of time
     */
    public void update(float deltaTime) {

    }


    /**
     * Method to move the entity on the x axis
     * @param amount new position on the x axis
     */
    protected void moveX(float amount) {
        float newX = pos.x + amount;
        this.pos.x = newX;

    }

    /**
     * Method to move the entity on the y axis
     * @param amount new position on the y axis
     */
    protected void moveY(float amount) {
        float newY = pos.y + amount;
        this.pos.y = newY;

    }

    /**
     * Method to check whether the entity is on a ladder
     * @param x current position on the x axis
     * @param y current position on the y axis
     * @return true or false
     */
    protected boolean ladder(float x, float y) {

        TiledMapTileLayer.Cell ladder = map.getCell((int) (x / map.getTileWidth()), (int) (y / map.getTileHeight()));
        return ladder != null && ladder.getTile() != null && ladder.getTile().getProperties().containsKey("ladder");
    }

    /**
     * Renders the entities
     * @param batch Assets of the entity
     */
    public void render(SpriteBatch batch) {

    }

    /**
     * Getter method for entities current position
     * @return 2D vector position
     */
    public Vector2 getPos() {
        return pos;
    }

    /**
     * Getter method for entities current position on the x axis
     * @return current position on the x axis
     */
    public float getX() {
        return pos.x;
    }

    /**
     * Getter method for entities current position on the y axis
     *
     * @return current position on y axis
     */
    public float getY() {
        return pos.y;
    }

    /**
     * Getter method for the entities type
     *
     * @return the type of entity
     */
    public EntityType getType() {
        return type;
    }

    /**
     * Getter method to get the height of an entity
     *
     * @return height of the entity
     */
    public int getHeight() {
        return type.getHeight();
    }

    /**
     * Getter method to get the width of an entity
     *
     * @return width of the entity
     */
    public int getWidth() {
        return type.getHeight();
    }

    /** sets the dead variable to true */
    public void killed() {
        dead = true;
    }
    /** @return the boolean value of dead variable */
    public boolean isDead() {
        return dead;
    }


}