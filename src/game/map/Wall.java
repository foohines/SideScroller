package src.game.map;

import src.game.mechanics.Position;
import javafx.scene.image.Image;

/**
 * Created by Foster on 11/30/2015.
 */
public class Wall extends Tile{
    public Wall(int x, int y, String filePath) {
        super(x, y, filePath);
        solid = true;

    }

    public Wall(int x, int y){
        super(x, y, "src/assets/RedSine.png");
        solid = true;
    }
    public Wall(Position position, String filePath) {
        super((int) position.getX(), (int) position.getY() , filePath);
        solid = true;
    }

    public Wall(Position position){
        super((int) position.getX(), (int) position.getY(), "src/assets/defaultWall.png");
        solid = true;
    }
}
