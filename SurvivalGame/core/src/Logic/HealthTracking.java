package Logic;

import entity.Entity;

public class HealthTracking {
    int health, totalHealth;
    Items buffs;
    Entity target;
    public HealthTracking(Entity e, Items buffs, int currHealth, int tHealth){
        target = e;
        buffs = this.buffs;
        health = currHealth;
        totalHealth = tHealth;


    }

    void decreaseHealth(int damage){
        if (health - damage > 0) {
            health -= damage;
        }
        else
            health = 0;
    }

    void buffHealth(int potion){
        if (health + potion < totalHealth) {
            health += potion;
        }
        else
            health = totalHealth;
    }

    boolean isDead(){
        if (health == 0){
            return true;
        }
        else
            return false;
    }
}
