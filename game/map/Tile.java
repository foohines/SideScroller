package game.map;

import game.Camera;
import game.Drawable;
import game.Game;
import game.Level;
import game.mechanics.Position;
import game.mechanics.SpriteBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by Foster on 11/30/2015.
 */
public class Tile extends Drawable {
    protected boolean solid;
    private SpriteBox spriteBox;

    public Tile(int x, int y, Image image){
        super();
        width = Level.TILE_WIDTH;
        height = Level.TILE_HEIGHT;
        position = new Position(x * Level.TILE_WIDTH, y * Level.TILE_HEIGHT);
        sprite = image;
        spriteBox = new SpriteBox(width, height, position);
    }


    public SpriteBox getSpriteBox() {
        return spriteBox;
    }
}
