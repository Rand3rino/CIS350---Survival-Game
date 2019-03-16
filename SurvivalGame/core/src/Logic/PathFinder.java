package Logic;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import entity.AI;
import entity.Entity;
import java.util.*;

public class PathFinder {

    TiledMapTileLayer map;

    public PathFinder(TiledMapTileLayer inMap) {
        map = inMap;
    }


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
        Point minDist = find(new Point (AIPosX, AIPosY), new Point (playerX, playerY), loc);
        if (minDist.getX() < AIPosX){
            return 0;
        }

        if (minDist.getX() > AIPosX){
            return 1;
        }

        if (minDist.getY() < AIPosY){
            return 2;
        }

        if (minDist.getY() > AIPosY){
            return 3;
        }


         for (int i = 0; i < map.getHeight() ; i++){
         for (int j = 0 ; j < map.getWidth(); j++){
         System.out.print(loc[i][j] + " ");
         }
         System.out.println();
         }
         System.out.println();
         System.out.println();



        return -1;
    }

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

   private Point find(Point src, Point dest, int[][] loc){
        int rowNum[] = {-1, 0, 0, 1};
        int colNum[] = {0, -1, 1, 0};
        Queue<QueueNode> q = new LinkedList<QueueNode>();

        // Distance of source cell is 0
        QueueNode s = new QueueNode(src, 0);
       QueueNode curr;
       q.add(s);
        while( !q.isEmpty()){
            curr = ((LinkedList<QueueNode>) q).peekFirst();
            Point pt = curr.getCurrent();

            if (pt.getX() == dest.getX() && pt.getY() == dest.getY())

                return curr.getFirst();

            ((LinkedList<QueueNode>) q).removeFirst();
            int row = pt.getX();
            int col = pt.getY();
            for (int i = 0; i < 4; i++)
            {
                if ((pt.getX() + rowNum[i]) < map.getHeight() && (pt.getX() + rowNum[i]) >= 0) {
                    row = pt.getX() + rowNum[i];
                }
                if ((pt.getY() + colNum[i]) < map.getWidth() && (pt.getY() + rowNum[i]) >= 0) {
                    col = pt.getY() + colNum[i];
                }

                // if adjacent cell is valid, has path and
                // not visited yet, enqueue it.
                if (isValidCell(row, col) && loc[row][col] != 2 && loc[row][col] != 1)
                {
                    // mark cell as visited and enqueue it
                    loc[row][col] = 1;
                    QueueNode adjCell = new QueueNode(new Point(row, col), curr.getDist() + 1);
                    ((LinkedList<QueueNode>) q).addFirst(adjCell);

                }
            }
        }
        return null;
    }
}