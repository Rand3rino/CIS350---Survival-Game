package entity;


import Logic.HealthTracking;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Collision;



public class Potion extends Entity {

    TiledMapTileLayer collision;
    Collision rect;
    Player player;

    public HealthTracking health = new HealthTracking(this, null, 10, 10);

    public Texture image;

    Texture potion;

    SpriteBatch batch;

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

    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    public void update(float deltaTime){
        if (player.punchArea.doesCollide(rect)){
        this.killed();
        player.health.buffHealth(2);
        }
    }


   /**** // Checks if the player is colliding with the potion
    public boolean PotionCollide() {
        for(float step = 0; step < getHeight(); step += map.getTileHeight() / 2)
            if(isPotion(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    private boolean isPotion(float x, float y) {

        TiledMapTileLayer.Cell cell = map.getCell((int) (x / map.getTileWidth()), (int) (y / map.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("potion");
    }
    *****/



}