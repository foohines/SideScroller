package src.game.map;

import src.game.mechanics.Position;
import javafx.scene.image.Image;

/**
 * Created by Foster on 11/30/2015.
 */
public class Floor extends Tile {
    public Floor(int x, int y, String filePath) {
        super(x, y, filePath);
        solid = true;
    }

    public Floor(int x, int y){
        super(x, y, "src/assets/defaultFloor.png");
        solid = true;
    }

    public Floor(Position position, String filePath) {
        super((int) position.getX(), (int) position.getY() , filePath);
        solid = true;
    }

    public Floor(Position position){
        super((int) position.getX(), (int) position.getY(), "src/assets/defaultFloor.png");
        solid = true;
    }
}
