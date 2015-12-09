package game;

import game.mechanics.Position;
import game.mechanics.SpriteBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by Foster on 11/28/2015.
 */
public abstract class Drawable {
    protected Image sprite;
    protected Position position;
    protected ArrayList<Drawable> contents;
    protected int width;
    protected int height;
    public SpriteBox spriteBox;

    public Drawable(){
        this.contents = new ArrayList<Drawable>();
    }

    public void draw(Canvas canvas, Camera camera){
        canvas.getGraphicsContext2D().drawImage(sprite, position.getX() - camera.getPosition().getX(), position.getY() - camera.getPosition().getY(), width, height);
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

    public SpriteBox getSpriteBox() {
        return spriteBox;
    }
}
