package src.game.movable;

import src.game.mechanics.Direction;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * Created by Foster on 12/4/2015.
 */
public abstract class Mob extends Actor {
    Player target;
    Brain brain;

    boolean btnUp = false;
    boolean btnDown = false;
    boolean btnLeft = false;
    boolean btnRight = false;

    public Mob(int x, int y, int width, int height, int health, Image[] images, Player target) {
        super(x, y, width, height, health, images);
        this.target = target;
        alive = true;
        brain = new Brain();
        brain.start();
    }

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

    private class Brain implements Runnable {
        private Thread t;
        private boolean aware;
        private Random random;

        Brain(){ }
        public void run() {


            while(alive) {
                try {
                    if (!aware) {

                        if (position.distanceTo(target.getPosition()) <= 400)
                            aware = true;
                    } else {
                        //Aware
                        if (target.getPosition().getX() < position.getX()) {
                            btnLeft = true;
                            btnRight = false;


                        } else if (target.getPosition().getX() > position.getX()) {
                            btnLeft = false;
                            btnRight = true;
                        }

                        if(atWall)
                            btnUp = true;
                        else
                            btnUp = false;

                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }


                try {
                    // Let the thread sleep for a while.
                    Thread.sleep(250+random.nextInt(250));
                } catch (InterruptedException e) {

                }
            }

        }

        public void start ()
        {
            if (t == null)
            {
                random = new Random();
                t = new Thread (this);
                t.setDaemon(true);
                t.start ();
            }
        }

    }


}
