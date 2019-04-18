package entity;

import Logic.Combat;
import Logic.HealthTracking;
import Logic.Point;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import Logic.PathFinder;
import com.mygdx.game.Collision;
import com.mygdx.game.Hud.Hud;
import com.mygdx.game.Screens.PlayScreen;

/**
 * The final boss entity
 * @author Ramell Collins
 */
public class KingSlime extends Entity {

    /** Integer variable representing number of steps left */
    private int left = 1;

    /** Integer variable representing number of steps right */
    private int right = 1;

    /** Integer variable representing number of steps up */
    private int up = 1;

    /** Integer variable representing number of steps down */
    private int down = 1;

    /** PathFinder variable for AI movement */
    PathFinder path;

    /** Integer variable representing speed of character */
    private static final int speed = 1;

    /** Integer variable representing punch gauge */
    private static final int punchBarMax = 100;

    /** Integer variable representing punch bar */
    private int punchBar;

    /** Punching sound effects */
    private Sound punchSFX;

    /** Health tracking variable */
    public HealthTracking health;

    /** Combat variable */
    private Combat combat;

    /** 2D vector variable representing position */
    Vector2 vector = new Vector2();

    /** The player entity */
    Player player;

    /** Current time in milliseconds */
    long time = System.currentTimeMillis();
    long start;

    /** Collision class rectangle */
    private Collision rect;

    /** Boolean variable representing death */
    private Boolean updateDead = false;

    /** Current screen information */
    private PlayScreen screenInfo;

    /** Texture variable representing the base image */
    private Texture image;

    /** First image for animation while moving left */
    private Texture left1;

    /** Second image for animation while moving left */
    private Texture left2;

    /** Third image for animation while moving left */
    private Texture left3;

    /** First image for animation while moving right */
    private Texture right1;

    /** Second image for animation while moving right */
    private Texture right2;

    /** Third image for animation while moving right */
    private Texture right3;

    /** First image for animation while moving up */
    private Texture up1;

    /** Second image for animation while moving up */
    private Texture up2;

    /** Third image for animation while moving up */
    private Texture up3;

    /** First image for animation while moving down */
    private Texture down1;

    /** Second image for animation while moving down */
    private Texture down2;

    /** Third image for animation while moving down */
    private Texture down3;

    /** Image variable for punching left */
    private Texture punchLeft;

    /** Image variable for punching right */
    private Texture punchRight;

    /** Image variable for laying down */
    private Texture laydown;

    /** Collision layer of the tiled map */
    private TiledMapTileLayer map;

    /**
     * Constructor method for the KingSlime class
     * @param x current position on x axis
     * @param y current position on y axis
     * @param map collision layer
     * @param e Player entity
     * @param info
     */
    public KingSlime(float x, float y, TiledMapTileLayer map, Player e, PlayScreen info) {
        super(x, y, EntityType.KINGSLIME, map, e);
        player = e;
        loadTextures();
        image = down2;
        this.rect = new Collision(getX(), getY(), getWidth(), getHeight());
        health = new HealthTracking(this, 1, 1);
        path = new PathFinder(map);
        combat = new Combat();
        punchBar = punchBarMax;
        punchSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/hits/12.ogg"));
        start = time;
        screenInfo = info;
    }

    private void loadTextures() {
        left1 = new Texture("KingSlime movement assets/down1.png");
        left2 = new Texture("KingSlime movement assets/down2.png");
        left3 = new Texture("KingSlime movement assets/down3.png");
        right1 = new Texture("KingSlime movement assets/down1.png");
        right2 = new Texture("KingSlime movement assets/down2.png");
        right3 = new Texture("KingSlime movement assets/down3.png");
        up1 = new Texture("KingSlime movement assets/down1.png");
        up2 = new Texture("KingSlime movement assets/down2.png");
        up3 = new Texture("KingSlime movement assets/down3.png");
        down1 = new Texture("KingSlime movement assets/down1.png");
        down2 = new Texture("KingSlime movement assets/down2.png");
        down3 = new Texture("KingSlime movement assets/down3.png");
        punchLeft = new Texture("KingSlime movement assets/down1.png");
        punchRight = new Texture("KingSlime movement assets/down2.png");
        laydown = new Texture("KingSlime movement assets/down3.png");
    }

    /**
     * Updates the entity and handles combat with the player entity
     * @param deltaTime amount of time
     */
    public void update(float deltaTime) {

        if (punchBar < punchBarMax)
            punchBar++;

        if (player.punchArea.doesCollide(rect))
            health.decreaseHealth(1);

        if (!health.isDead() && punchBar > 25) {
            int move = path.find(new Point((int) pos.x, (int) pos.y), new Point((int) player.getX(), (int) player.getY()), screenInfo.devPath());
            if (move == 0) {
                moveX(-speed);
                rect.move(getX(), getY());
                if (ladder(getX(), getY()))
                    imgUp();
                else
                    imgLeft();
            } else if (move == 1) {
                moveX(speed);
                rect.move(getX(), getY());
                if (ladder(getX(), getY()))
                    imgUp();
                else
                    imgRight();
            } else if (move == 2) {
                moveY(-speed);
                rect.move(getX(), getY());
                if (ladder(getX(), getY()))
                    imgUp();
                else
                    imgDown();
            } else if (move == 3) {
                moveY(speed);
                rect.move(getX(), getY());
                imgUp();
            }

            if (combat.inCombat(this, player))
                playerPunch();

        } else if (health.isDead() && !updateDead) {
            image = laydown;
            updateDead = true;
            Hud.decrementEnemy();
            this.killed();
        }
    }

    /******************************************************************
     * playerPunch will handle if player presses the attack button
     *****************************************************************/
    private void playerPunch() {

        // Perform a punch upon button press if attack bar is full
        if (punchBar == punchBarMax && !ladder(getX(), getY())) {

            // Update sprite image.
            imgPunch();
            punchSFX.play(0.15f);

            // Reset attack bar
            punchBar = 0;
            player.hit(1);
            player.knockback(getX(), getY());
        }
    }

    private void imgPunch() {
        if (image.equals(left1) || image.equals(left2) || image.equals(left3))
            image = punchLeft;
        else if (image.equals(right1) || image.equals(right2) || image.equals(right3))
            image = punchRight;
    }


    /**
     * Method to "move to this point"
     * @return 2D position representing the players current position
     */
    public Vector2 playerLocation() {
        vector.x = player.getX();
        vector.y = player.getY();
        return vector;
    }

    /**
     * Renders the KingSlime entity
     * @param batch Assets of entities
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    private void imgLeft() {

        if (left >= 30)
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

    private void imgRight() {

        if (right >= 30)
            image = right1;
        else if (right >= 20)
            image = right2;
        else if (right >= 10)
            image = right3;
        else if (right >= 0)
            image = right2;
        right++;
        right = right % 40;
    }

    /**
     *
     */
    private void imgDown() {

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
}
