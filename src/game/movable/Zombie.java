package src.game.movable;

import src.game.Game;
import src.game.mechanics.Direction;
import javafx.scene.image.Image;

/**
 * Created by Foster on 12/4/2015.
 */
public class Zombie extends Mob{
    public Zombie(int x, int y, Player target){

        super(x, y, 64, 128, 100, getImages(), target);
        direction = Direction.LEFT;
    }

    public void handleInput(){
        accelerate(0, Game.GRAVITY);
        if(grounded){
            accelerate((this.getVelocity().getX()*(-.05)), 0);
        }

        if(btnLeft) {
            accelerate(-.2, 0);
            direction = Direction.LEFT;
        }
        if(btnRight) {
            accelerate(.2, 0);
            direction = Direction.RIGHT;
        }
        if(btnUp&&grounded) {
            accelerate(0, -30);
        }

    }

    private static Image[] getImages(){
        return new Image[]{ new Image("src/assets/Zombie_R.png"), new Image("src/assets/Zombie.png")};
    }
}
