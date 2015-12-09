package game.map;

import game.mechanics.Position;
import javafx.scene.image.Image;

/**
 * Created by Foster on 11/30/2015.
 */
public class Floor extends Tile {
    public Floor(int x, int y, Image image) {
        super(x, y, image);
        solid = true;
    }

    public Floor(int x, int y){
        super(x, y, new Image("assets/defaultFloor.png"));
        solid = true;
    }
    public Floor(Position position, Image image) {
        super((int) position.getX(), (int) position.getY() , image);
        solid = true;
    }

    public Floor(Position position){
        super((int) position.getX(), (int) position.getY(), new Image("assets/defaultFloor.png"));
        solid = true;
    }
}
