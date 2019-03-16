package Logic;

public class QueueNode {
    private int dist;
    private Point first;
    private Point current;

    public QueueNode(Point p, int distance){
        dist = distance;
        first = p;
        current = p;
    }

    public void changePoint(Point currPoint){
        current = currPoint;
    }

    public int getDist() {
        return dist;
    }

    public Point getFirst() {
        return first;
    }

    public Point getCurrent(){
        return current;
    }

}
