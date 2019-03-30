package entity;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.HashMap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;


public class Potion extends ApplicationAdapter {

    /** A HashMap to store multiple sprites */
    static final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    static TextureAtlas textureAtlas;

    Sprite potion;

    SpriteBatch batch;

    @Override
    public void create() {
        textureAtlas = new TextureAtlas("core/assets/PinkPotion.png");

        potion = textureAtlas.createSprite("potion");

        batch = new SpriteBatch();

        addSprites();
    }


    @Override
    public void render() {


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        drawSprite("potion", 100, 100);

        batch.end();
    }

    private void drawSprite(String name, float x, float y) {
        Sprite sprite = sprites.get(name);

        sprite.setPosition(x, y);

        sprite.draw(batch);
    }

    public static void addSprites() {
        Array<AtlasRegion> regions = textureAtlas.getRegions();

        for (AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);

            sprites.put(region.name, sprite);
        }
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();

        sprites.clear();
    }



}