package src.game.mechanics;

/**
 * Created by Foster on 12/1/2015.
 */
public class Velocity {
    private double x;
    private double y;

    public Velocity() {
        x = 0;
        y = 0;
    }
    public Velocity(int x, int y){
        this.x = (double) x;
        this.y = (double) y;
    }
    public Velocity(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() { return y; }
    public void setX(double x){this.x = (double) x;}
    public void setY(double y){this.y = (double) y;}

    public void accelerate(Velocity delta, Velocity max){
        //If greater than max, set as max else set += delta

        //X
        if(abs(this.x+ delta.getX()) > max.x)
            this.x = (this.x > 0) ? max.x : -max.x;
        else
            this.x += delta.getX();
        //Y
        if(abs(this.y+ delta.getY()) > max.y)
            this.y = (this.y > 0) ? max.y : -max.y;
        else
            this.y += delta.getY();

    }
    public void accelerate(double dx, double dy, Velocity max){
        //If greater than max, set as max else set += delta

        //X
        if(abs(this.x+ dx) > max.x)
            this.x = (this.x > 0) ? max.x : -max.x;
        else
            this.x += dx;
        //Y
        if(abs(this.y+ dy) > max.y)
            this.y = (this.y > 0) ? max.y : -max.y;
        else
            this.y += dy;

    }

    private double abs(double x){
        return (x >= 0) ? x : -x;
    }
}
