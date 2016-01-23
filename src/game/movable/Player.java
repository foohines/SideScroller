package src.game.movable;


import javafx.scene.input.KeyCode;
import src.game.Game;
import src.game.mechanics.Direction;
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
            accelerate(-.4, 0);
            direction = Direction.LEFT;
            sprite = images[1];

        }
        if(btnRight){
            accelerate(.4, 0);
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

    public void keyEvent(KeyCode code, boolean down){
        switch(code){
            case UP:
            case W:
                btnUp = down;
                break;
            case DOWN:
            case S:
                btnDown = down;
                break;
            case LEFT:
            case A:
                btnLeft = down;
                break;
            case RIGHT:
            case D:
                btnRight = down;
                break;
        }
    }
    public void mouseClick(double x, double y, Direction button){

    }

    private static Image[] getImages(){
        return new Image[]{new Image("src/assets/Right.png"), new Image("src/assets/Left.png")};
    }



}

