package src.game;

import javafx.scene.input.KeyCode;
import src.game.mechanics.Direction;
import src.game.mechanics.GameState;
import src.game.mechanics.Position;
import javafx.scene.canvas.Canvas;

/**
 * Created by Foster on 11/28/2015.
 */
public class Game {
    public final static double GRAVITY = 1.0;
    private Level level;
    private Camera camera;
    private GameState gameState;

    private boolean leftMouseDown = false;
    private boolean rightMouseDown = false;

    public Game(Canvas canvas){
        camera = new Camera(new Position(0,0), (int) canvas.getWidth(), (int) canvas.getHeight());
        gameState = GameState.Normal;
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

    public void keyEvent(KeyCode key, boolean down){

        if(key == KeyCode.O && down){

            //Toggle Edit - O
            if(gameState == GameState.Normal)
                gameState = GameState.Edit;
            else
                gameState = GameState.Normal;

            return;
        }

        level.keyEvent(key, down);
    }

    public void mouseClick(double x, double y, Direction direction){

        switch(gameState){
            case Normal:
                level.mouseClick(x, y, direction);
                break;

            case Edit:
                if(direction == Direction.LEFT)
                    level.addZombie((int) (camera.getPosition().getX() + x), (int) (camera.getPosition().getY() + y));

                if(direction == Direction.RIGHT)
                    level.addTile((int) (camera.getPosition().getX() + x), (int) (camera.getPosition().getY() + y));
                break;
        }


    }


}
