package Logic;
import com.mygdx.game.GameMap;

public class SpawnRules {
    int currEnemies;
    GameMap currMap;

    public SpawnRules (int enemyCount, GameMap map){
        currEnemies = enemyCount;
        currMap = map;
    }
}
