package src.game;

import src.game.map.Floor;
import src.game.map.Tile;
import src.game.map.Wall;
import src.game.mechanics.*;
import src.game.movable.Actor;
import src.game.movable.Mob;
import src.game.movable.Player;
import src.game.movable.Zombie;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Foster on 11/30/2015.
 */

public class Level extends Drawable {
    public final static short TILE_WIDTH = 32;
    public final static short TILE_HEIGHT = 32;
    int gridWidth;
    int gridHeight;
    boolean floorGrid[][];
    Box boxGrid[][];
    Tile tileGrid[][];

    ArrayList<Actor> actors;
    Player player;


    public Level(Camera camera){
        actors = new ArrayList<>();
        player = new Player(64,15*32);
        actors.add(player);
        loadMap("src/assets/map.txt");
        camera.setOffset(player.getWidth()/2, player.getHeight()/2);
        camera.getPosition().setY(height - camera.getHeight());

        for(int i = 1; i < actors.size(); i++)
            contents.add(actors.get(i));

        contents.add(player);
    }

    public Level(Camera camera, String saveFile){
        actors = new ArrayList<>();
        loadMap(saveFile);

        camera.setOffset(player.getWidth()/2, player.getHeight()/2);
        camera.getPosition().setY(height - camera.getHeight());

        for(int i = 1; i < actors.size(); i++)
            contents.add(actors.get(i));

        contents.add(player);
    }

    public void update(Camera camera){
        handleInput();
        //handleAttacks();
        moveActors();
        resolveCollisions();
        camera.update(player.getPosition().getX(), player.getPosition().getY());

    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Only draw tiles that camera can see
        int cameraX = (int) camera.getPosition().getX() / TILE_WIDTH;
        int cameraY = (int) camera.getPosition().getY() / TILE_HEIGHT;
        int maxX = cameraX + (camera.getWidth() / TILE_WIDTH) + 1;
        int maxY = cameraY + (camera.getHeight() / TILE_HEIGHT) + 1;
        maxX = (maxX < tileGrid.length) ? maxX : tileGrid.length - 1;
        maxY = (maxY < tileGrid[0].length) ? maxY : tileGrid[0].length - 1;

        //Draw Map
        for (int x = cameraX; x <= maxX; x++){
            for (int y = cameraY; y <= maxY; y++) {
                tileGrid[x][y].draw(canvas, camera);
            }
        }

        //Draw Actors
        for (Actor actor: actors) {
            actor.draw(canvas, camera);
        }

        //Redraw Player
        player.draw(canvas, camera);
    }

    public void commandPlayer(Direction direction){ player.command(direction);}
    public void stopCommandPlayer(Direction direction){ player.stopCommand(direction);}
    public void addZombie(int x, int y){

        Actor zombie = new Zombie(x, y, player);

        int[][][] surroundingTiles = zombie.getSurroundingTiles();
        boolean collision = false;
        width = zombie.getTileWidth();
        height = zombie.getTileHeight();


        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++){
                //If maps to solid, add boolean to map
                if (surroundingTiles[i][j][0] > gridWidth - 1 || surroundingTiles[i][j][1] > gridHeight - 1 || floorGrid[surroundingTiles[i][j][0]][surroundingTiles[i][j][1]])
                    collision = true;
            }
        }

        if (!collision) {
            actors.add(zombie);
            contents.add(zombie);
        }



    }
    public void addTile(int x, int y) {
        x /= 32;
        y /= 32;

        if (floorGrid[x][y]) {
            return;
        }
        Floor floor = new Floor(x, y);

        for (Actor actor : actors){
            if (floor.getSpriteBox().intersects(actor.getSpriteBox())) {
                return;
            }
        }

        contents.add(floor);
        tileGrid[x][y] = floor;
        boxGrid[x][y] = floor.getSpriteBox();
        floorGrid[x][y] = true;
    }

    private void handleInput(){
        for (Actor actor: actors){
            actor.handleInput();
        }
    }
    private void handleAttacks(){
        for (Actor actor: actors){
            //actor.handleAttacks();
        }
    }
    private void moveActors(){
        for (Actor actor: actors)
            actor.move();
    }
    private void resolveCollisions(){

        int[][][] surroundingTiles;
        //DOES NOT CORRESPOND TO DIMENSIONS OF ARRAY (Must add 1)
        int width;
        int height;
        boolean collisions[][];
        int totalCollisions;

            for (Actor actor : actors) {
                width = actor.getTileWidth();
                height = actor.getTileHeight();
                totalCollisions = 0;

                //Get and Reduce surroundingTiles if possible
                surroundingTiles = actor.getSurroundingTiles();
                if(surroundingTiles[width][0][0] == surroundingTiles[width-1][0][0] && surroundingTiles[width][0][1] == surroundingTiles[width-1][0][1])
                    width--;

                if(surroundingTiles[0][height][0] == surroundingTiles[0][height-1][0] && surroundingTiles[0][height][1] == surroundingTiles[0][height-1][1])
                    height--;

                //Construct collision map
                collisions = new boolean[width+1][height+1];
                boolean collision = false;
                for (int x = 0; x <= width; x++) {
                    for (int y = 0; y <= height; y++) {
                        //If maps to solid, add boolean to map
                        if (floorGrid[surroundingTiles[x][y][0]][surroundingTiles[x][y][1]]) {
                            collisions[x][y] = collision = true;
                            totalCollisions++;
                        }
                    }
                }

                //If no collision, continue
                if(!collision)
                    continue;


                Position position;

                //If one block collision
                if(totalCollisions == 1){
                Box box = null;
                    for(int x = 0; x <= width; x++)
                        for(int y = 0; y <= height; y++){
                            if(collisions[x][y]) {
                                box = boxGrid[surroundingTiles[x][y][0]][surroundingTiles[x][y][1]];
                                x = width+1;
                                break;
                            }
                        }
                    position = box.resolveSingleTileCollision(actor.getSpriteBox(), actor.getVelocity());

                    if(actor.position.getX() == position.getX())
                        actor.getVelocity().setX(0);

                    if(actor.position.getY() > position.getY())
                        actor.grounded = true;


                    actor.getPosition().set(position);


                } else {

                    //MULTIPLE COLLISION
                    int verticalEdge = -1;
                    int horizontalEdge = -1;
                    int acc = 0;

                    //If falling, check for floor (By looking for two adjacent blocks) and record edge
                    if (actor.getVelocity().getY() > 0) {
                        for (int y = 0; y <= height; y++) {
                            acc = 0;
                            for (int x = 0; x <= width; x++) {
                                if (collisions[x][y]) acc++;
                            }
                            if (acc > 1) {
                                horizontalEdge = surroundingTiles[0][y][1] * 32;
                                break;
                            }
                        }
                        //IF RISING
                    } else if (actor.getVelocity().getY() < 0) {
                        for (int y = height; y >= 0; y--) {
                            acc = 0;
                            for (int x = 0; x <= width; x++) {
                                if (collisions[x][y]) acc++;
                            }
                            if (acc > 1) {
                                horizontalEdge = surroundingTiles[0][y][1] * 32 + 31;
                                break;
                            }
                        }
                    }


                    //If move right, check for wall
                    if (actor.getVelocity().getX() > 0) {
                        for (int x = 0; x <= width; x++) {
                            acc = 0;
                            for (int y = 0; y <= height; y++) {
                                if (collisions[x][y]) acc++;
                            }
                            if (acc > 1) {
                                verticalEdge = surroundingTiles[x][0][0] * 32;
                                break;
                            }
                        }
                        //IF RISING
                    } else if (actor.getVelocity().getX() < 0) {
                        for (int x = width; x >= 0; x--) {
                            acc = 0;

                            for (int y = 0; y <= height; y++) {
                                if (collisions[x][y]) acc++;
                            }

                            if (acc > 1) {
                                verticalEdge = surroundingTiles[x][0][0] * 32 + 31;
                                break;
                            }
                        }
                    }

                    if (verticalEdge != -1) {

                        if (actor.getVelocity().getX() > 0) {
                            actor.getPosition().setX(verticalEdge - actor.getWidth());
                        } else
                            actor.getPosition().setX(verticalEdge + 1);

                        actor.getVelocity().setX(0);
                        actor.atWall = true;
                    }

                    if (horizontalEdge != -1){
                        if (actor.getVelocity().getY() > 0) {
                            actor.getPosition().setY(horizontalEdge - actor.getHeight());
                            actor.grounded = true;

                                //actor.getVelocity().setY(0);
                        } else {
                            actor.getPosition().setY(horizontalEdge + 1);

                                //actor.getVelocity().setY(0);
                        }

                    }
                }


            }

    }

    private void loadMap(String fileMap){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileMap));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(scanner.hasNext("savefile"))
            loadActors(scanner);

        gridWidth = scanner.nextInt();
        gridHeight = scanner.nextInt();
        floorGrid = new boolean[gridWidth][gridHeight];
        boxGrid = new Box[gridWidth][gridHeight];
        tileGrid = new Tile[gridWidth][gridHeight];

        // Set pixel height based on generic Wall Dimensions
        width = gridWidth * new Wall(0,0).width;
        height = gridHeight * new Wall(0,0).height;

        scanner.nextLine();

        String line;
        Tile tile = null;
        Mob mob = null;
        for(int y = 0; y < gridHeight; y++) {
            line = scanner.nextLine();
            for(int x = 0; x < gridWidth; x++){

                //Parse each character
                switch(line.charAt(x)){
                    case '.':
                        tile = new Wall(x,y);
                        contents.add(tile);
                        floorGrid[x][y] = false;
                        tileGrid[x][y] = tile;
                        boxGrid[x][y] = tile.getSpriteBox();
                        break;

                    case '#':
                        tile = new Floor(x,y);
                        contents.add(tile);
                        floorGrid[x][y] = true;
                        tileGrid[x][y] = tile;
                        boxGrid[x][y] = tile.getSpriteBox();
                        break;

                    case'Z':
                        mob = new Zombie(x*TILE_WIDTH, y*TILE_HEIGHT, player);
                        actors.add(mob);
                        tile = new Wall(x,y);
                        contents.add(tile);
                        floorGrid[x][y] = false;
                        tileGrid[x][y] = tile;
                        boxGrid[x][y] = tile.getSpriteBox();
                        break;
                    default:
                        break;
                }

            }
        }
    }
    private void loadActors(Scanner scanner){
        scanner.nextLine();
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        char d = (char) scanner.next().charAt(0);
        player = new Player(x,y);
        player.setDirection((d=='L') ? Direction.LEFT : Direction.RIGHT);

        actors.add(player);
        Actor actor;
        while (!scanner.hasNext("end")) {
            x = scanner.nextInt();
            y = scanner.nextInt();
            d = scanner.next().charAt(0);
            actor = new Zombie(x, y, player);
            actor.setDirection((d=='L') ? Direction.LEFT : Direction.RIGHT);
            actors.add(actor);
        }

        scanner.nextLine();
    }
    public void save(String saveFile){
        try {
            PrintWriter writer = new PrintWriter(saveFile, "UTF-8");
            writer.println("savefile");

            //Save actors
            for(int i = 0; i < actors.size(); i++){
                writer.print((int) actors.get(i).getPosition().getX() + " ");
                writer.print((int) actors.get(i).getPosition().getY() + " ");
                writer.print((actors.get(i).getDirection() == Direction.LEFT) ? 'L' : 'R');
                writer.print(" ");
            }

            writer.println("end");

            //Save map
            writer.println(gridWidth + " " + gridHeight);

            Tile tile;
            for(int y = 0; y < gridHeight; y++){
                for(int x = 0; x < gridWidth; x++){
                    tile = tileGrid[x][y];
                    if(tile instanceof Floor)
                        writer.print('#');
                    if(tile instanceof Wall)
                        writer.print('.');
                }
                writer.println();
            }


            writer.close();
        } catch (Exception exc){}
    }
}
