package src.game;

import src.game.mechanics.Position;
import src.game.mechanics.SpriteBox;

/**
 * Created by Foster on 11/28/2015.
 */
public class Camera {
    private int offsetX;
    private int offsetY;
    protected Position position;
    protected int width;
    protected int height;
    private int boundX;
    private int boundY;
    public SpriteBox spriteBox;

    public Camera(Position position, int width, int height){
        this.position = position;
        this.width = width;
        this.height = height;
        spriteBox = new SpriteBox(width, height, position);

    }

    public void update(double x, double y){
        move(x + offsetX,y + offsetY);
    }

    public void move(double x, double y){
        //Check if in bounds
        if( x >= 0 &&  x <= boundX)
            position.setX(x);

        if( y >= 0 &&  y <= boundY)
            position.setY(y);
    }

    public void setBounds(int x, int y){
        boundX = x - width;
        boundY = y - height;
    }
    public void setOffset(int offsetX, int offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Position getPosition(){
        return position;
    }
    public void setPosition(Position position){
        this.position = position;
    }
    public void setPosition(int x, int y){
        this.position.setX(x);
        this.position.setY(y);
    }
    public void setPosition(double x, double y){
        this.position.setX(x);
        this.position.setY(y);
    }
    public int getWidth(){ return width;};
    public int getHeight(){ return height;};

}
