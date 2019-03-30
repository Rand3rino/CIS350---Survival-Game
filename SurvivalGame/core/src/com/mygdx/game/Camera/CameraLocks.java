package com.mygdx.game.Camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;

public class CameraLocks {

    /** Pixels per meter, 1 tile per meter */
    public static float PPM = 32;

    public static void playerLock(Camera camera, Vector2 pos){
        Vector3 position = camera.position;
        position.x = pos.x;
        position.y = pos.y;
        camera.position.set(position);
        camera.update();
    }

    public static void interpolation(Camera camera, float playerX, float playerY){

        Vector3 position = camera.position;
        position.x = camera.position.x + (playerX * PPM - camera.position.x) * 0.1f;
        position.y = playerY * PPM;
        camera.position.set(position);
        camera.update();
    }

    public static void boundry(Camera camera, float startX, float startY, float width, float height){
        Vector3 position = camera.position;

        if(position.x < startX) {
            position.x = startX;
        }

        if(position.y < startY) {
            position.y = startY;
        }

        if(position.x > startX + width){
            position.x = startX + width;
        }

        if(position.y > startY + height) {
            position.y = startY + height;
        }

        camera.position.set(position);
        camera.update();
    }


}
