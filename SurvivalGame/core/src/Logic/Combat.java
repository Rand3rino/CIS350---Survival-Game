package Logic;

import com.badlogic.gdx.math.Vector2;
import entity.Entity;

/**
 * Combat Class
 * Checks if two characters are within punching distance
 */
public class Combat{

    /**
     * inCombat
     * checks if the AI and the Player are within punching distance
     * @param ai the AI character
     * @param player the player Character
     * @return True or False if they are close
     */
    public boolean inCombat (Entity ai, Entity player) {
        Vector2 aiPosition = new Vector2();
        Vector2 playerPosition = new Vector2();
        aiPosition.set(ai.getX(),ai.getY());
        playerPosition.set(player.getX(),player.getY());

        // If the distance between two vectors are within one tile, combat is true.
        if (aiPosition.dst(playerPosition) <= 27.5) {
            return true;
        }
        return false;

    }
}
