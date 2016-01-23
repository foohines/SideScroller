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
                        pause();
                    }
                });
        btnSave.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        save();
                    }
                });
        btnPlay.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        play();
                    }
                });
        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) { quit();
                    }
                });
        btnLoad.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        load();
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
                        game.keyEvent(event.getCode(), true);
                    }


                });


        //KeyReleased
        panePlay.addEventHandler(KeyEvent.KEY_RELEASED,
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent event) {
                        game.keyEvent(event.getCode(), false);
                    }

                });
    }

    public void pause(){
        if(playing) {
            playing = false;
            pauseMenu.visibleProperty().set(true);
        }

    }
    public void play(){
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
    public void load(){
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
    public void save(){
        if(game != null) {
            game.saveLevel("src/assets/saveFile.txt");
            btnSave.setText("Saved");
        }


    }
    public void quit(){
        Stage stage = (Stage) panePlay.getScene().getWindow();
        stage.close();
    }



}