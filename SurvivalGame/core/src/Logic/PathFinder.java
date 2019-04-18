package Logic;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.lang.Math;

/**
 * Pathfinder Class
 *
 * This is used by the computer entities to find the shortest path to the
 * player.
 *
 * @author Ramell Collins and Scott Nguyen
 * @version 1.0
 */
public class PathFinder {

    /**
     * Map layer
     * Details points on the map and includes collision areas and character locations
     */
    TiledMapTileLayer mapIn;

    /**
     * PathFinder Constructor
     * Sets the pathfinder's map to the current game's map.
     * @param map Map layer with collision areas and character locations
     */
    public PathFinder(TiledMapTileLayer map) {
        mapIn = map;
    }

    /**
     * isValidCell method
     * Checks if a cell on the map can be traversed
     * @param row Row location of the cell
     * @param col Column location of the cell
     * @return True or False if cell is valid
     */
    private boolean isValidCell(int row, int col) {
        if (row < mapIn.getHeight() && row >= 0 && col < mapIn.getWidth() && col >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * find Method
     * @param src the source cell's location
     * @param dest the destination cell's location
     * @param loc 2d array of the map layer
     * @return TODO
     */
    public int find(Point src, Point dest, int[][] loc) {
        int AIX = src.getX() / 32;
        int AIY = src.getY() / 32;
        int playerX = dest.getX() / 32;
        int playerY = dest.getY() / 32;

        if (playerY > 12 && AIY < 12) {
            if (AIX < 11) {
                playerX = 4;
                playerY = 12;
            } else {
                playerX = 16;
                playerY = 12;
            }
        } else if (AIY > 11 && AIY < 16 && playerY > 16) {
            if (AIX < 11) {
                playerX = 4;
                playerY = 16;
            } else {
                playerX = 16;
                playerY = 16;
            }
        } else if (playerY < 16 && AIY > 16) {
            if (AIX < 11) {
                playerX = 4;
                playerY = 16;
            } else {
                playerX = 16;
                playerY = 16;
            }
        } else if (AIY > 11 && AIY < 16 && playerY < 12) {
            if (AIX < 11) {
                playerX = 4;
                playerY = 11;
            } else {
                playerX = 16;
                playerY = 11;
            }
        }
        //to read current player x and y location for testing

        if (Math.abs(playerX - AIX) > Math.abs(playerY - AIY)) {
            if (playerX > AIX) {
                if (isValidCell(AIX + 1, AIY) && !isCellBlocked(AIX + 1, AIY)) {
                    if (loc[AIX + 1][AIY] != 4)
                        return 1;
                }
                if (isValidCell(AIX + 1, AIY) && isCellBlocked(AIX + 1, AIY)) {
                    if (isValidCell(AIX, AIY + 1) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX][AIY + 1] != 4)
                            return 3;
                    }
                    if (isValidCell(AIX, AIY - 1) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX + 1][AIY] != 4)
                            return 1;
                    }
                }
            } else {
                if (isValidCell(AIX - 1, AIY) && !isCellBlocked(AIX - 1, AIY)) {
                    if (loc[AIX - 1][AIY] != 4)
                        return 0;
                }
                if (isValidCell(AIX - 1, AIY) && isCellBlocked(AIX - 1, AIY)) {
                    if (isValidCell(AIX, AIY + 1) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX][AIY + 1] != 4)
                            return 3;
                    }
                    if (isValidCell(AIX, AIY - 1) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX][AIY - 1] != 4)
                            return 2;
                    }
                }
            }
        } else {
            if (playerY > AIY) {

                if (isValidCell(AIX, AIY + 1) && !isCellBlocked(AIX, AIY + 1)) {
                    if (loc[AIX][AIY + 1] != 4)
                        return 3;
                }
                if (isValidCell(AIX, AIY + 1) && isCellBlocked(AIX, AIY + 1)) {
                    if (isValidCell(AIX + 1, AIY) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX + 1][AIY] != 4)
                            return 1;
                    }
                    if (isValidCell(AIX - 1, AIY) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX - 1][AIY] != 4)
                            return 0;
                    }
                }
            } else {
                if (isValidCell(AIX, AIY - 1) && !isCellBlocked(AIX, AIY - 1)) {
                    if (loc[AIX][AIY - 1] != 4)
                        return 2;
                }
                if (isValidCell(AIX, AIY - 1) && isCellBlocked(AIX, AIY - 1)) {
                    if (isValidCell(AIX + 1, AIY) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX + 1][AIY] != 4)
                            return 1;
                    }
                    if (isValidCell(AIX - 1, AIY) && !isCellBlocked(AIX, AIY + 1)) {
                        if (loc[AIX - 1][AIY] != 4)
                            return 0;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * isValidCell method
     * Checks if a cell on the map cannot be traversed
     * @param x horizontal location of the cell
     * @param y vertical location of the cell
     * @return True of False if the cell cannot be traversed
     */
    private boolean isCellBlocked(int x, int y) {
        TiledMapTileLayer.Cell cell = mapIn.getCell(x, y);
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }
}