package game;

import game.mechanics.Direction;
import game.mechanics.Position;
import javafx.scene.canvas.Canvas;

/**
 * Created by Foster on 11/28/2015.
 */
public class Game {
    public final static double GRAVITY = 1;
    private Level level;
    private Camera camera;

    private boolean leftMouseDown = false;
    private boolean rightMouseDown = false;

    public Game(Canvas canvas){
        camera = new Camera(new Position(0,0), (int) canvas.getWidth(), (int) canvas.getHeight());
    }

    public void loadLevel(){
        level = new Level(camera);
        camera.setBounds(level.getWidth(), level.getHeight());
        camera.setOffset(level.player.getWidth()/2 - camera.getWidth()/2, level.player.getHeight()/2 - camera.getHeight()/2);
    }
    public void loadLevel(String saveFile){
        level = new Level(camera, saveFile);
        camera.setBounds(level.getWidth(), level.getHeight());
        camera.setOffset(level.player.getWidth()/2 - camera.getWidth()/2, level.player.getHeight()/2 - camera.getHeight()/2);
    }

    public void saveLevel(String saveFile){
        level.save(saveFile);
    }

    public void render(Canvas canvas){
        level.update(camera);
        level.draw(canvas, camera);
    }

    public void movePlayer(Direction direction){
        level.commandPlayer(direction);}
    public void stopMovePlayer(Direction direction){level.stopCommandPlayer(direction);}
    public void mouseClick(double x, double y, Direction direction){
        if(direction == Direction.LEFT)
            level.addZombie((int) (camera.getPosition().getX() + x), (int) (camera.getPosition().getY() + y));

        if(direction == Direction.RIGHT)
            level.addTile((int) (camera.getPosition().getX() + x), (int) (camera.getPosition().getY() + y));

    }


}
