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
 * The Runner Enemy type. They are super fast
 * @author Ramell Collins
 * @version 1.0
 */
public class Runner extends Entity {
    private int left = 1, right = 1, up = 1, down = 1;

    /** PathFinder variable for AI movement */
    PathFinder path;
    private static final int speed = 2;

    private static final int punchBarMax = 100;
    private int punchBar;

    /**
     * Punch sound
     */
    private Sound punchSFX;

    /** 2D positional variable */
    Vector2 vector = new Vector2();

    /** The player entity */
    Player player;

    /** Current time in game in milliseconds */
    long time = System.currentTimeMillis();

    /** Long variable representing the start of time */
    long start;

    /** Health tracking variable */
    public HealthTracking health;
    private Combat combat;

    private Collision rect;
    private boolean updateDead = false;

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
    private Texture knockdown1;
    private Texture knockdown2;
    private PlayScreen screenInfo;

    /**
     * Constructor for the Runner class
     * @param x position on the x axis
     * @param y position on the y axis
     * @param map collision tiled map layer
     * @param e the player entity
     * @param info information from the PlayScreen class
     */
    public Runner(float x, float y, TiledMapTileLayer map, Player e, PlayScreen info) {
        super(x, y, EntityType.COMPUTER, map, e);
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
        left1 = new Texture("vaccine man movement assets/left1.png");
        left2 = new Texture("vaccine man movement assets/left2.png");
        left3 = new Texture("vaccine man movement assets/left3.png");
        right1 = new Texture("vaccine man movement assets/right1.png");
        right2 = new Texture("vaccine man movement assets/right2.png");
        right3 = new Texture("vaccine man movement assets/right3.png");
        up1 = new Texture("vaccine man movement assets/up1.png");
        up2 = new Texture("vaccine man movement assets/up2.png");
        up3 = new Texture("vaccine man movement assets/up3.png");
        down1 = new Texture("vaccine man movement assets/down1.png");
        down2 = new Texture("vaccine man movement assets/down2.png");
        down3 = new Texture("vaccine man movement assets/down3.png");
        punchLeft = new Texture("vaccine man movement assets/punchLeft.png");
        punchRight = new Texture("vaccine man movement assets/punchRight.png");
        laydown = new Texture("vaccine man movement assets/dead.png");
        knockdown1 = new Texture("vaccine man movement assets/knockback1.png");
        knockdown2 = new Texture("vaccine man movement assets/knockback2.png");
    }

    /**
     * Updates the Runner entity and handles combat and movement
     * @param deltaTime amount of time
     */
    public void update(float deltaTime) {

        if (punchBar < punchBarMax)
            punchBar++;

        if (player.punchArea.doesCollide(rect))
            health.decreaseHealth(1);

        if (!health.isDead() && punchBar > 25) {
            int move = path.find(new Point((int) getX(), (int) getY()), new Point((int) player.getX(), (int) player.getY()), screenInfo.devPath());

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
            //TODO some way to pause. Animation is too quick
            image = punchLeft;
        else if (image.equals(right1) || image.equals(right2) || image.equals(right3))
            image = punchRight;
    }


    /**
     * Method to move Runner to "this" point on tiled map
     * @return 2D positional variable
     */
    public Vector2 playerLocation() {
        vector.x = player.getX();
        vector.y = player.getY();
        return vector;

    }

    /**
     * Renders the Runner class assets
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