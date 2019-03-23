package Logic;

import entity.Entity;

public class Items {
    int healthChange;
    String img;
    Entity target;
    public Items(int healthEffect, String imgLink, Entity e){
        healthChange = healthEffect;
        img = imgLink;
        target = e;
    }
    public void collectItem(){

    }

    public void useItem (){

    }

    public void addToDock(){

    }

    public void removeFromDock(){

    }

    public int getHealthChange(){
        return healthChange;
    }
}
