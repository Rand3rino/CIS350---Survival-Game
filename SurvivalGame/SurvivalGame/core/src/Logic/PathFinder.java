package Logic;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import entity.Entity;
import java.util.*;
import java.lang.Math;

public class PathFinder {

    TiledMapTileLayer map;
    Queue<QueueNode> q = new LinkedList<QueueNode>();
    public PathFinder(TiledMapTileLayer inMap) {
        map = inMap;
    }
    long time = System.currentTimeMillis();
    double second = 1;
    public int minDistance(int AIPosX, int AIPosY, Entity e) {

        //variables are divided by 32 because float X and Y positions are provided in pixel quantities
        //each tile on map is 32 pixels


        int playerX = (int) e.getX() / 32;
        int playerY = (int) e.getY() / 32;
        AIPosX /= 32;
        AIPosY /= 32;
        int loc[][] = new int[map.getWidth()][map.getHeight()]; // array generated from map blocks
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (isCellBlocked(i, j)) {
                    loc[i][j] = 2; //sets all blocked locations on array map to 2
                } else
                    loc[i][j] = 0; // sets all accessible spaces to 0
            }
        }
        //places player location on map array using a 3
        loc[playerX][playerY] = 3;
        //places AI location on map array using a 4
        loc[AIPosX][AIPosY] = 4;
        return find(new Point (AIPosX, AIPosY), new Point (playerX, playerY), loc);
    }

/****
        for (int i = 0; i < map.getHeight() ; i++){
            for (int j = 0 ; j < map.getWidth(); j++){
                System.out.print(loc[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
****/



    // same is blocked method from player entity but adjusted to read tile data from x and y coordinates
    private boolean isCellBlocked(int x, int y) {
        TiledMapTileLayer.Cell cell = map.getCell(x, y);
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }




    private boolean isValidCell(int row, int col){
        if (row < map.getHeight() && row >= 0 && col < map.getWidth() && col >= 0){
            return true;
        }
        else{
            return false;
        }
    }

    private int find(Point src, Point dest, int[][] loc) {
        Point nextMove;
    if (Math.abs(dest.getX() - src.getX()) > Math.abs(dest.getY() - src.getY())){
        if (dest.getX() > src.getX()){
            if (isValidCell(src.getX() + 1, src.getY()) && !isCellBlocked(src.getX() + 1, src.getY())){
                return 1;
            }
            if (isValidCell(src.getX() + 1, src.getY()) && isCellBlocked(src.getX() + 1, src.getY())){
                if (isValidCell(src.getX(), src.getY() + 1) && !isCellBlocked(src.getX(), src.getY() + 1)) {
                    return 3;
                }
                if (isValidCell(src.getX(), src.getY() - 1) && !isCellBlocked(src.getX(), src.getY() + 1)){
                    return 1;
                }
            }
        }
        else{
            if (isValidCell(src.getX() - 1, src.getY()) && !isCellBlocked(src.getX() - 1, src.getY())){
                return 0;
            }
            if (isValidCell(src.getX() - 1, src.getY()) && isCellBlocked(src.getX() - 1, src.getY())){
                if (isValidCell(src.getX(), src.getY() + 1) && !isCellBlocked(src.getX(), src.getY() + 1)) {
                    return 3;
                }
                if (isValidCell(src.getX(), src.getY() - 1) && !isCellBlocked(src.getX(), src.getY() + 1)){
                    return 2;
                }
            }
        }
    }



    else{
        if (dest.getY() > src.getY()){

            if (isValidCell(src.getX(), src.getY() + 1) && !isCellBlocked(src.getX(), src.getY() + 1)){
                return 3;
            }
            if (isValidCell(src.getX(), src.getY() + 1) && isCellBlocked(src.getX(), src.getY() + 1)){
                if (isValidCell(src.getX() + 1, src.getY()) && !isCellBlocked(src.getX(), src.getY() + 1)) {
                    return 1;
                }
                if (isValidCell(src.getX() - 1, src.getY() ) && !isCellBlocked(src.getX(), src.getY() + 1)){
                    return 0;
                }
            }
        }
        else{
            if (isValidCell(src.getX(), src.getY() - 1) && !isCellBlocked(src.getX(), src.getY() - 1)){
                return 2;
            }
            if (isValidCell(src.getX(), src.getY() -1) && isCellBlocked(src.getX(), src.getY() - 1)){
                if (isValidCell(src.getX() + 1, src.getY()) && !isCellBlocked(src.getX(), src.getY() + 1)) {
                    return 1;
                }
                if (isValidCell(src.getX() - 1, src.getY() ) && !isCellBlocked(src.getX(), src.getY() + 1)){
                    return 0;
                }
            }
        }
    }
    return -1;
    }
}