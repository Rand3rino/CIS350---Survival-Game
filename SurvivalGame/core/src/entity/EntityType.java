package entity;

/**
 * The type of entity with set dimensions
 * @author Scott Nguyen, Edited by Ramell Collins
 * @version 1.0
 */
public enum EntityType {
    PLAYER("player", 20, 40),
    COMPUTER("computer", 20, 40),
    SLIME("slime", 50, 80),
    KINGSLIME("king slime", 100, 120),
    POTION ("potion", 20, 40);

    private String id;
    private int width, height;

    EntityType(String id, int width, int height) {
        this.height = height;
        this.width = width;
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

