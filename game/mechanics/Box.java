package game.mechanics;

/**
 * Created by Foster on 12/1/2015.
 */
public abstract class Box{
    protected int width;
    protected int height;

    public abstract int getX1();
    public abstract int getX2();
    public abstract int getY1();
    public abstract int getY2();
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}

    public boolean intersects(Box box){
        return (this.getX1() <= box.getX2() && this.getX2() >= box.getX1() && this.getY1() <= box.getY2() && this.getY2() >= box.getY1());
    }

    public Position moveOtherBoxTo(Box playerBox){
        int x = 0;
        int y = 0;

        if(playerBox.getX1() < this.getX1())
            x = this.getX1() - playerBox.getWidth();
        else
            x = this.getX2() + 1;

        if(playerBox.getY1() < this.getY1())
            y = this.getY1() - playerBox.getHeight();
        else
            y = this.getY2() + 1;

        if(Math.abs(playerBox.getY1() - y) > Math.abs(playerBox.getX1() - x))
            y = playerBox.getY1();
        else
            x = playerBox.getX1();


        return new Position(x, y);

    }

}
