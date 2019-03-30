package entity;

import Logic.Combat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import Logic.PathFinder;

public class Walker extends Entity {
    private int left = 1, right = 1, up = 1, down = 1;

    PathFinder path;
    private static final int speed = 1;

    private static final int punchBarMax = 100;
    private int punchBar;

    /** Punch sound */
    private Sound punchSFX;

    Vector2 vector = new Vector2();
    Player player;
    long time = System.currentTimeMillis();
    long start;
    private Combat combat;

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

    public Walker (float x, float y, TiledMapTileLayer map, Player e){
        super(x,y,EntityType.COMPUTER, map, e);
        player = e;
        loadTextures();
        image = down2;
        path = new PathFinder(map);
        combat = new Combat();
        punchBar = punchBarMax;
        punchSFX= Gdx.audio.newSound(Gdx.files.internal("sounds/hits/12.ogg"));
        start = time;
    }

    private void loadTextures() {
        left1 = new Texture("deep sea king movement assets/left1.png");
        left2 = new Texture("deep sea king movement assets/left2.png");
        left3 = new Texture("deep sea king movement assets/left3.png");
        right1 = new Texture("deep sea king movement assets/right1.png");
        right2 = new Texture("deep sea king movement assets/right2.png");
        right3 = new Texture("deep sea king movement assets/right3.png");
        up1 = new Texture("deep sea king movement assets/up1.png");
        up2 = new Texture("deep sea king movement assets/up2.png");
        up3 = new Texture("deep sea king movement assets/up3.png");
        down1 = new Texture("deep sea king movement assets/down1.png");
        down2 = new Texture("deep sea king movement assets/down2.png");
        down3 = new Texture("deep sea king movement assets/down3.png");
//        punchLeft = new Texture("deep sea king movement assets/punchLeft.png");
//        punchRight = new Texture("deep sea king movement assets/punchRight.png");
//        laydown = new Texture("deep sea king movement assets/dead.png");
    }

    public void update (float deltaTime) {


        int move = path.minDistance((int) getX(), (int) getY(), player);

        if (move == 0) {
            moveX(-speed);
            if(ladder(getX(),getY()))
                imgUp();
            else
                imgLeft();
        }

        else if (move == 1) {
            moveX(speed);
            if(ladder(getX(),getY()))
                imgUp();
            else
                imgRight();
        }
        else if (move == 2) {
            moveY(-speed);
            if(ladder(getX(),getY()))
                imgUp();
            else
                imgDown();
        }
        else if (move == 3) {
            moveY(speed);
            imgUp();
        }

        if (combat.inCombat(this, player)) {
            playerPunch();
        }
        else if (punchBar < punchBarMax){
            punchBar++;
        }
    }



    //if locked in combat
    // AI can move in a square **/
//        if (pos.x  < 400 && pos.y == 300)
//            moveX(speed);
//        else if (pos.x == 400 && pos.y < 400)
//            moveY(speed);
//        else if (pos.x > 300 && pos.y == 400)
//            moveX(-speed);
//        else
//            moveY(-speed);

    /******************************************************************
     * playerPunch will handle if player presses the attack button
     *****************************************************************/
    private void playerPunch() {

        // Perform a punch upon button press if attack bar is full
            if (punchBar == punchBarMax && !ladder(getX(),getY())) {

                // Update sprite image.
               // imgPunch();
                punchSFX.play(0.15f);

                // Reset attack bar
                punchBar = 0;
                player.hit(1);
                player.knockback(getX(), getY());
            }
    }


    // method "move to this point" Using tiledgamemap
    public Vector2 playerLocation() {
        vector.x = player.getX();
        vector.y = player.getY();
        return vector;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    private void imgLeft(){

        if (left >= 30 )
            image = left1;
        else if (left >= 20)
            image = left2;
        else if (left >= 10)
            image =  left3;
        else if (left >= 0)
            image = left2;
        left++;
        left = left % 40;
    }

    private void imgRight(){

        if (right >= 30)
            image = right1;
        else if(right >= 20)
            image =  right2;
        else if(right >= 10)
            image = right3;
        else if(right >= 0)
            image =  right2;
        right++;
        right = right % 40;
    }

    /**
     *
     */
    private void imgDown(){

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