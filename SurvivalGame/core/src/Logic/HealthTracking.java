package Logic;

import entity.Entity;

/**
 * HealthTracking Class
 *
 * This class manages characters' health. Created when a
 * character is created and will add health when a player
 * heals or decrease health when a character is hit. This
 * also knows when a character dies.
 *
 * @author Ramell Collins
 * @version 1.0
 */
public class HealthTracking {

    private int health;
    private int totalHealth;

    /**
     * The entity that the health tracking applies to
     */
    Entity target;

    /**
     * HealthTracking Constructor
     * Save the character's health information
     * @param e the character
     * @param currHealth their starting health
     * @param tHealth their maximum health
     */
    public HealthTracking(Entity e, int currHealth, int tHealth) {
        target = e;
        health = currHealth;
        totalHealth = tHealth;
    }

    /**
     * health Getter
     * @return health the character's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * decreaseHealth
     * Decrement the character's heatlh by the amount of damage taken
     * @param damage the amount of damage taken
     */
    public void decreaseHealth(int damage) {
        if (health - damage > 0) {
            health -= damage;
        } else
            health = 0;
    }

    /**
     * buffHealth
     * Increases the character's health by the amount of health healed
     * @param potion the amount of health healed
     */
    public void buffHealth(int potion) {
        if (health + potion < totalHealth) {
            health += potion;
        } else
            health = totalHealth;
    }

    /**
     * isDead
     * checks if the character is dead
     * @return True or False if the character is dead
     */
    public boolean isDead() {
        if (health == 0) {
            return true;
        } else
            return false;
    }
}
