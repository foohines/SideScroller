package src.game.mechanics;

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

    public Position resolveSingleTileCollision(Box playerBox, Velocity velocity){
        int x = playerBox.getX1();
        int y = playerBox.getY1();

        boolean changeX = false;
        boolean changeY = false;

        if(playerBox.getX1() < this.getX1() ) {
            if (velocity.getX() > 0) {
                x = this.getX1() - playerBox.getWidth();
                changeX = true;

            }
        } else {
            if (velocity.getX() < 0) {
                x = this.getX2() + 1;
                changeX = true;
            }
        }

        if(playerBox.getY1() < this.getY1()) {
            if(velocity.getY() > 0) {
                y = this.getY1() - playerBox.getHeight();
                changeY = true;
            }
        } else {
            if(velocity.getY() < 0) {
                y = this.getY2() + 1;
                changeY = true;
            }
        }

        if(changeX && changeY) {
            if (Math.abs(playerBox.getY1() - y) > Math.abs(playerBox.getX1() - x))
                y = playerBox.getY1();
            else
                x = playerBox.getX1();
        }

        return new Position(x, y);

    }

}
