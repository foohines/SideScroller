package src.game.map;

import src.game.mechanics.Position;
import javafx.scene.image.Image;

/**
 * Created by Foster on 11/30/2015.
 */
public class Wall extends Tile{
    public Wall(int x, int y, Image image) {
        super(x, y, image);
        solid = true;

    }

    public Wall(int x, int y){
        super(x, y, new Image("src/assets/RedSine.png"));
        solid = true;
    }
    public Wall(Position position, Image image) {
        super((int) position.getX(), (int) position.getY() , image);
        solid = true;
    }

    public Wall(Position position){
        super((int) position.getX(), (int) position.getY(), new Image("src/assets/defaultWall.png"));
        solid = true;
    }
}
