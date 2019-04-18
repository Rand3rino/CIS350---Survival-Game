package entity;

import Logic.HealthTracking;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Collision;

/**
 * Entity type that represents a potion to heal the player character
 * @author Isfar Baset
 */
public class Potion extends Entity {

    /** Tile layer that contains collision properties */
    TiledMapTileLayer collision;

    /** Collision rectangle */
    Collision rect;

    /** The player entity */
    Player player;

    /** Health tracking variable */
    public HealthTracking health = new HealthTracking(this, 1, 1);

    /** Texture variable representing an image */
    public Texture image;

    /** Texture variable representing the potion */
    Texture potion;

    /******************************************************************
     * Potion Constructor
     *
     * @param x The potion's starting horizontal location
     * @param y The potion's starting vertical location
     * @param map The map that the potion is placed on
     * @param e An entity object for entities to track
     *****************************************************************/
    public Potion (float x, float y, TiledMapTileLayer map, Player e){

        super(x,y,EntityType.POTION, map, e);
        loadTextures();
        image = potion;
        player = e;
        this.rect = new Collision(getX(),getY(),getWidth(),getHeight());
        this.collision = map;
    }

    private void loadTextures() {
        potion = new Texture("PinkPotion.png");
    }

    /**
     * Renders the potion to be added to the batch
     * @param batch Assets of entities
     */
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    /**
     * Updates the potion when punched
     * @param deltaTime amount of time
     */
    public void update(float deltaTime){
        if (player.punchArea.doesCollide(rect)){
        this.killed();
        player.health.buffHealth(2);
        }
    }
}