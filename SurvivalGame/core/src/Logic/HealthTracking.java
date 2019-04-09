package Logic;

import entity.Entity;

public class HealthTracking {
    private int health;
    private int totalHealth;
    Entity target;
    public HealthTracking(Entity e, int currHealth, int tHealth){
        target = e;
        health = currHealth;
        totalHealth = tHealth;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int damage){
        if (health - damage > 0) {
            health -= damage;
        }
        else
            health = 0;
    }

    public void buffHealth(int potion){
        if (health + potion < totalHealth) {
            health += potion;
        }
        else
            health = totalHealth;
    }

    public boolean isDead(){
        if (health == 0){
            return true;
        }
        else
            return false;
    }
}
