package src.game.mechanics;

/**
 * Created by Foster on 12/1/2015.
 */
public class SpriteBox extends Box{
    private Position position;

    public SpriteBox(int width, int height, Position position){
        this.width = width;
        this.height = height;
        this.position = position;

    }

    public SpriteBox(int width, int height, int x, int y){
        this.width = width;
        this.height = height;
        this.position = new Position(x, y);
    }

    public int getX1(){
        return (int) position.getX();
    }
    public int getX2() {
        return (int) position.getX() + width - 1;
    }
    public int getY1(){
        return (int) position.getY();
    }
    public int getY2() {return (int) position.getY() + height - 1; }


}
