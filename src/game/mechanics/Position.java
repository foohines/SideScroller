package src.game.mechanics;

/**
 * Created by Foster on 11/28/2015.
 */
public class Position {
    private double x;
    private double y;

    public Position(int x, int y){
        this.x = (double) x;
        this.y = (double) y;
    }
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() { return y; }

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void set(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }

    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}

    public void move(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }
    public void move(Velocity velocity){
        this.x += velocity.getX();
        this.y += velocity.getY();
    }

    public double distanceTo(Position position){
       return  Math.sqrt((this.x - position.getX())*(this.x - position.getX()) + (this.y - position.getY())*(this.y - position.getY()));
    }
}
