package entity;


public enum EntityType {
    PLAYER("player", 30,60,80),
    COMPUTER("computer", 30,60,80);


    private String id;
    private int width, height;
    private float weight;

    EntityType(String id, int width, int height, float weight) {
        this.height = height;
        this.width = width;
        this.weight = weight;
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

