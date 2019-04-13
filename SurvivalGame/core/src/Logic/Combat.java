package Logic;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import entity.Entity;
import entity.Player;

public class Combat{

//    TiledMapTileLayer map;
//    Player player;

    public Combat () {
//        this.map = map;
//        this.player = player;
    }

    public boolean inCombat (TiledMapTileLayer map, int[][] loc) {
        for (int i = 0; i < map.getHeight(); i++)
            for (int j = 0; j < map.getWidth(); j++)
                if (loc[i][j] == 3)
                    for (int h = -1; h < 2; h++)
                        for (int w = -1; w < 2; w++)
                            if (i+h>=0 && i+h < map.getHeight() && j+w>=0 && j+w < map.getWidth())
                                if (loc[i+1][j+w] == 4)
                                    return true;
        return false;
    }


    public boolean inCombat (Entity ai, Entity player) {
        Vector2 aiPosition = new Vector2();
        Vector2 playerPosition = new Vector2();
        aiPosition.set(ai.getX(),ai.getY());
        playerPosition.set(player.getX(),player.getY());

        // If the distance between two vcctors are within one tile, combat is true.
        if (aiPosition.dst(playerPosition) <= 27.5) {
            return true;
        }
        return false;

    }

    public Vector2 knockback (Entity e1, Entity e2) {

        Vector2 newPosition = new Vector2();

        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();

        // TODO must not be diagonal. this is a rough direction check
        if (x1 < x2 && (y1 <= y2 + 7 || y1 >= y2 - 7)) {
            newPosition.x = x2 + 15;
            newPosition.y = y2;
        }
        else if (x1 > x2 && (y1 <= y2 + 7 || y1 >= y2 - 7)) {
            newPosition.x = x2 - 15;
            newPosition.y = y2;
        }
        else if (y1 > y2 && (x1 <= x2 + 7 || x1 >= x2 - 7)) {
            newPosition.x = x2;
            newPosition.y = y2 - 15;
        }
        else {
            newPosition.x = x2;
            newPosition.y = y2 + 15;
        }

        return newPosition;
    }
}
