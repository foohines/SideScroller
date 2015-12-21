package src.gui;

import src.game.Game;
import src.game.mechanics.Direction;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
    final private long FRAMES_PER_SEC = 70;
    final private long NANO_INTERVAL = 1000000000L/FRAMES_PER_SEC;

    Game game;
    boolean playing = false;
    boolean gameLoaded = false;

    @FXML
    public Canvas canvas;
    @FXML
    public Pane panePlay;
    @FXML
    public Button btnPause;
    @FXML
    public Button btnPlay;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnLoad;
    @FXML
    public Button btnQuit;
    @FXML
    public TitledPane pauseMenu;

    private AnimationTimer timer = new AnimationTimer(){
        long last = 0;

        public void handle(long now){
            if(now - last > NANO_INTERVAL){

                if(playing)
                    game.render(canvas);

                last = now;
            }
        }
    };

    public void initialize() {
        startMenuHandlers();


    }

    private void startMenuHandlers(){
        btnPause.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        onPause();
                    }
                });
        btnSave.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        onSave();
                    }
                });
        btnPlay.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        onPlay();
                    }
                });
        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        onQuit();
                    }
                });
        btnLoad.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        onLoad();
                    }
                });
    }
    private void startPlayHandlers(){
        //MouseClick
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(playing){
                            game.mouseClick(mouseEvent.getX(), mouseEvent.getY(), (mouseEvent.getButton() == MouseButton.PRIMARY) ? Direction.LEFT : Direction.RIGHT);
                        }

                    }
                });

        //KeyPressed
        panePlay.addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent event) {

                            KeyCode code = event.getCode();

                            if (code == KeyCode.W || code == KeyCode.UP) {
                                game.movePlayer(Direction.UP);
                            } else if (code == KeyCode.A || code == KeyCode.LEFT) {
                                game.movePlayer(Direction.LEFT);
                            } else if (code == KeyCode.S || code == KeyCode.DOWN) {
                                game.movePlayer(Direction.DOWN);
                            } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
                                game.movePlayer(Direction.RIGHT);
                            }

                    }


                });


        //KeyPressed
        panePlay.addEventHandler(KeyEvent.KEY_RELEASED,
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent event) {
                        KeyCode code = event.getCode();

                            if (code == KeyCode.W || code == KeyCode.UP) {
                                game.stopMovePlayer(Direction.UP);
                            } else if (code == KeyCode.A || code == KeyCode.LEFT) {
                                game.stopMovePlayer(Direction.LEFT);
                            } else if (code == KeyCode.S || code == KeyCode.DOWN) {
                                game.stopMovePlayer(Direction.DOWN);
                            } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
                                game.stopMovePlayer(Direction.RIGHT);
                            }

                    }

                });
    }

    public void onPause(){
        if(playing) {
            playing = false;
            pauseMenu.visibleProperty().set(true);
        }

    }
    public void onPlay(){
        if(game == null){
            //Load Game
            game = new Game(canvas);
            game.loadLevel();
            startPlayHandlers();
            timer.start();
            btnPlay.setText("Resume");
            gameLoaded = true;
        }

        pauseMenu.visibleProperty().set(false);
        playing = true;
        btnSave.setText("Save");
    }
    public void onLoad(){
        //Load Game
        game = new Game(canvas);
        game.loadLevel("src/assets/saveFile.txt");

        //Suggest recycling the old game
        System.gc();

        if(!gameLoaded) {
            gameLoaded = true;
            startPlayHandlers();
        }
        timer.start();
        btnPlay.setText("Resume");
        btnSave.setText("Save");
        pauseMenu.visibleProperty().set(false);
        playing = true;

    }
    public void onSave(){
        if(game != null) {
            game.saveLevel("src/assets/saveFile.txt");
            btnSave.setText("Saved");
        }


    }
    public void onQuit(){
        Stage stage = (Stage) panePlay.getScene().getWindow();
        stage.close();
    }



}