package src.game.map;

import src.game.Drawable;
import src.game.Level;
import src.game.mechanics.Position;
import src.game.mechanics.SpriteBox;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Foster on 11/30/2015.
 */
public class Tile extends Drawable {

    static protected Map<String, Image> imageBank = new HashMap<>();

    protected boolean solid;
    private SpriteBox spriteBox;


    public Tile(int x, int y, String filepath){
        super();
        width = Level.TILE_WIDTH;
        height = Level.TILE_HEIGHT;
        position = new Position(x * Level.TILE_WIDTH, y * Level.TILE_HEIGHT);
        sprite = getImageFromBank(filepath);
        spriteBox = new SpriteBox(width, height, position);
    }

    public SpriteBox getSpriteBox() {
        return spriteBox;
    }

    private static Image getImageFromBank(String filePath){
        if(imageBank.containsKey(filePath)) {
            return imageBank.get(filePath);
        } else {
            imageBank.put(filePath, new Image(filePath));
            return imageBank.get(filePath);
        }
    }
}
