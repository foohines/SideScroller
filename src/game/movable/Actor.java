package src.game.movable;

import src.game.Camera;
import src.game.Drawable;
import src.game.Level;
import src.game.mechanics.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

/**
 * Created by Foster on 12/1/2015.
 */
public abstract class Actor extends Drawable {

    public Position previousPosition;
    private Velocity velocity;
    private  Velocity maxVelocity;
    private HitBox hitBox;
    private SpriteBox spriteBox;
    private int surroundingTiles[][][];
    private int tileHeight;
    private int tileWidth;
    Image images[];

    private int maxHealth;
    private int health;
    Direction direction;
    protected boolean flying = false;
    public boolean grounded;
    public boolean atWall;
    protected boolean alive;

    public Actor(int x, int y, int width, int height, int health, Image images[]) {
        velocity = new Velocity(0,0);
        maxVelocity = new Velocity(10, 15);
        position = new Position(x, y);
        this.width = width;
        this.height = height;
        tileWidth = width/Level.TILE_WIDTH;
        tileHeight = height/Level.TILE_HEIGHT;

        hitBox = new HitBox(width, height, position, 10);
        spriteBox = new SpriteBox(width, height, position);
        surroundingTiles = new int[tileWidth+1][tileHeight+1][2]; //Only works for heros that are multiples of tiles

        this.images = images;
        this.sprite = images[0];
        this.health = health;
        maxHealth = health;
        boolean isAlive;


    }
    @Override
    public void draw(Canvas canvas, Camera camera){
        switch (direction) {
            case LEFT:
                sprite = images[1];
                break;
            case RIGHT:
                sprite = images[0];
                break;
        }

        super.draw(canvas, camera);
    }


    public abstract void  handleInput();
    //public void handleAttacks()

    public final void move(){
        previousPosition = position;
        position.move(velocity.getX(), velocity.getY());
        grounded = false;
        atWall = false;

    }

    public void accelerate(Velocity velocity){
        this.velocity.accelerate(velocity, maxVelocity);
    }
    public void accelerate(double dx, double dy){
        velocity.accelerate(dx,dy, maxVelocity);
    }

    public int[][][] getSurroundingTiles(){

        //Complicated shit that took me 2 hours to come up with

        for(int x = 0; x <= tileWidth; x++) {
            for (int y = 0; y <= tileHeight; y++) {
                surroundingTiles[x][y][0] = (int) (position.getX() + x * (Level.TILE_WIDTH) - ((x < tileWidth) ? 0 : 1)) / Level.TILE_WIDTH;
                surroundingTiles[x][y][1] = (int) (position.getY() + y * (Level.TILE_HEIGHT) - ((y < tileHeight) ? 0 : 1)) / Level.TILE_HEIGHT;
            }
        }

        return surroundingTiles;
    }

    public int getTileWidth() {
        return tileWidth;
    }
    public int getTileHeight() {
        return tileHeight;
    }
    public HitBox getHitBox() {
        return hitBox;
    }
    public SpriteBox getSpriteBox() {
        return spriteBox;
    }
    public Velocity getVelocity() {
        return velocity;
    }
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPosition(double x, double y){
        position.set(x,y);
    }


}
