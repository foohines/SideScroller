package game.movable;


import game.Game;
import game.mechanics.Direction;
import javafx.scene.image.Image;

/**
 * Created by Foster on 12/3/2015.
 */
public class Player extends Actor{
    boolean btnUp;
    boolean btnDown;
    boolean btnLeft;
    boolean btnRight;





    public Player(int x, int y) {
        super(x, y, 64, 128, 100, getImages());
        direction = Direction.RIGHT;
    }

    public void handleInput(){
        accelerate(0, Game.GRAVITY);
        if(grounded){
            accelerate((this.getVelocity().getX()*(-.05)), 0);
        }
        if(btnLeft) {
            accelerate(-.5, 0);
            direction = Direction.LEFT;
            sprite = images[1];

        }
        if(btnRight){
            accelerate(.5, 0);
            sprite = images[0];
            direction = Direction.RIGHT;
        }

        if(btnLeft && btnRight){
            if(getVelocity().getX() > 0){
                sprite = images[0];
                direction = Direction.RIGHT;
            } else {
                sprite = images[1];
                direction = Direction.LEFT;
            }

        }

        if(btnUp&&grounded) {
            accelerate(0, -30);
        }


    }
    //public void handleAttacks(){}

    public void command(Direction direction){
        switch(direction){
            case UP:
                btnUp = true;
                break;
            case DOWN:
                btnDown = true;
                break;
            case LEFT:
                btnLeft = true;
                break;
            case RIGHT:
                btnRight = true;
                break;
        }
    }
    public void stopCommand(Direction direction){
        switch(direction){
            case UP:
                btnUp = false;
                break;
            case DOWN:
                btnDown = false;
                break;
            case LEFT:
                btnLeft = false;
                break;
            case RIGHT:
                btnRight = false;
                break;
        }
    }

    private static Image[] getImages(){
        return new Image[]{new Image("assets/Right.png"), new Image("assets/Left.png")};
    }



}

