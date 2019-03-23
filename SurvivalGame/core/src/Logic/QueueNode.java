package Logic;

public class QueueNode {
    private int dist;

    private Point current;

    public QueueNode(Point p, int distance){
        dist = distance;
        current = p;
    }

    public void changePoint(Point currPoint){
        current = currPoint;
    }

    public int getDist() {
        return dist;
    }



    public Point getCurrent(){
        return current;
    }


}
