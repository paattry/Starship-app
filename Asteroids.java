package com.example.demo2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class Asteroids extends Application {

    BorderPane pane;
    Pane gamePane, emptyPane;
    AnchorPane ap;
    Scene GUIScene, gameScene;
    Button start, stats, exit;
    Label someAction, player1, player2, player3, score1, score2, score3, rankingTitle;
    VBox ranking;
    HBox row1, row2, row3;
    List<Meteor> meteorList;
    List<Laser> laserList;
    int counter = 0;
    double level = 1;
    boolean spacePressed = true;
    boolean shot = false;
    int[] meteorsShot = new int[60];
    int[] bulletsUsed = new int[50];

    @Override
    public void start(Stage stage) {
        stage.setTitle("Starship game");

        setStartingGUI(); // ustawia startowe gui
        setGameGUI(); // tworzy panel gry
        setGUIFunctionality(stage); // odpala gre przez wcisniecia start

        stage.setScene(GUIScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void setStartingGUI()
    {
        pane = new BorderPane();
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK,  CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPrefSize(1000, 800);
        ap = new AnchorPane();
        ap.setPrefSize(240, 800);
        ap.setBackground(new Background(new BackgroundFill(Color.GRAY,  CornerRadii.EMPTY, Insets.EMPTY)));

        //Buttons
        start = new Button("START");
        start.setLayoutX(30);
        start.setLayoutY(60);
        start.setPrefSize(180, 50);
        stats = new Button("STOP");
        stats.setLayoutX(30);
        stats.setLayoutY(150);
        stats.setPrefSize(180, 50);
        exit = new Button("RESTART");
        exit.setLayoutX(30);
        exit.setLayoutY(240);
        exit.setPrefSize(180, 50);

        someAction = new Label("Tu bedzie gra");
        someAction.setPrefSize(200, 40);
        someAction.setLayoutX(420);
        someAction.setLayoutY(380);

        //Ranking Box
        ranking = new VBox();
        ranking.setPrefSize(200, 300);
        ranking.setLayoutX(20);
        ranking.setLayoutY(400);
        ranking.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2))));

        row1 = new HBox();
        row1.setPrefSize(200, 40);
        row1.setLayoutX(0);
        row1.setLayoutY(0);
        row2 = new HBox();
        row2.setPrefSize(200, 40);
        row2.setLayoutX(0);
        row2.setLayoutY(40);
        row3 = new HBox();
        row3.setPrefSize(200, 40);
        row3.setLayoutX(0);
        row3.setLayoutY(80);

        player1 = new Label("Jacek");
        score1 = new Label("6730");
        player1.setAlignment(Pos.CENTER);
        player1.setPrefSize(100, 40);
        player1.setLayoutY(0); player1.setLayoutY(0);
        score1.setAlignment(Pos.CENTER);
        score1.setPrefSize(100, 40);
        score1.setLayoutX(100); score1.setLayoutY(0);
        row1.getChildren().addAll(player1, score1);

        player2 = new Label("Wacek");
        score2 = new Label("4560");
        player2.setAlignment(Pos.CENTER);
        player2.setPrefSize(100, 40);
        player2.setLayoutY(0); player2.setLayoutY(0);
        score2.setAlignment(Pos.CENTER);
        score2.setPrefSize(100, 40);
        score2.setLayoutX(100); score2.setLayoutY(0);
        row2.getChildren().addAll(player2, score2);

        player3 = new Label("Placek");
        score3 = new Label("2890");
        player3.setAlignment(Pos.CENTER);
        player3.setPrefSize(100, 40);
        player3.setLayoutY(0); player3.setLayoutY(0);
        score3.setAlignment(Pos.CENTER);
        score3.setPrefSize(100, 40);
        score3.setLayoutX(100); score3.setLayoutY(0);
        row3.getChildren().addAll(player3, score3);

        rankingTitle = new Label("Ranking");
        rankingTitle.setPrefSize(200, 40);
        rankingTitle.setAlignment(Pos.CENTER);

        ranking.getChildren().addAll(rankingTitle, row1, row2, row3);

        emptyPane = new Pane();
        emptyPane.getChildren().add(someAction);
        emptyPane.setPrefSize(760, 800);

        ap.getChildren().addAll(start, stats, exit, ranking);
        pane.setRight(ap);
        pane.setCenter(emptyPane);

        GUIScene = new Scene(pane, 1000, 800);
    }

    public void setGameGUI()
    {
        gamePane = new Pane();
        gamePane.setBackground(new Background(new BackgroundFill(Color.WHITE,  CornerRadii.EMPTY, Insets.EMPTY)));
        gamePane.getChildren().add(someAction);
        gameScene = new Scene(gamePane, 1000, 800);
    }

    public void setGUIFunctionality(Stage stage)
    {
        start.setOnAction(e ->
        {
            meteorList = new ArrayList<>(); // lista asteroid
            laserList = new ArrayList<>(); // lista pociskow
            stage.setScene(gameScene);

            shipMovement(); // funkcja sterujaca gra

            Runnable asteroidsTask = this::asteroidsWaterfall;
            Thread asteroidsThread = new Thread(asteroidsTask); // watek sterujacy asteroidami
            asteroidsThread.setDaemon(true);
            asteroidsThread.start();

            Runnable laserTask = this::laserBlaster;
            Thread laserThread = new Thread(laserTask); // watek sterujacy pociskami
            laserThread.setDaemon(true);
            laserThread.start();
        });
    }

    public void shipMovement()
    {
        HashMap<KeyCode, Boolean> keyPressed = new HashMap<>();

        gameScene.setOnKeyPressed(event -> keyPressed.put(event.getCode(), Boolean.TRUE));
        gameScene.setOnKeyReleased(event -> keyPressed.put(event.getCode(), Boolean.FALSE));
        
        keyPressed.put(KeyCode.SPACE, Boolean.FALSE);

        Ship ship = new Ship(500, 500);
        gamePane.getChildren().add(ship.getObject());

        new AnimationTimer() // klasa do robienia animacji, dziala w 60fps
        {
            @Override
            public void handle(long l)
            {
                // statek i jego funkcjonalnosci
                if(keyPressed.getOrDefault(KeyCode.LEFT, false))
                    ship.shipAccelerate(KeyCode.LEFT);

                if(keyPressed.getOrDefault(KeyCode.RIGHT, false))
                    ship.shipAccelerate(KeyCode.RIGHT);

                if(keyPressed.getOrDefault(KeyCode.UP, false))
                    ship.shipAccelerate(KeyCode.UP);

                if(keyPressed.getOrDefault(KeyCode.DOWN, false))
                    ship.shipAccelerate(KeyCode.DOWN);

                if(keyPressed.getOrDefault(KeyCode.SPACE, false))
                {
                    if(spacePressed)
                    {
                        Laser laser = new Laser(ship.getObject().getTranslateX(), ship.getObject().getTranslateY());
                        laserList.add(laser);
                        gamePane.getChildren().add(laser.getObject());
                    }
                    spacePressed = false;
                }
                
                if(!keyPressed.get(KeyCode.SPACE))
                    spacePressed = true;
                
                ship.move(); // rusza statkiem

                // sprawdza kolizje miedzy statkiem a asteroidami
                meteorList.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        stop();
                    }
                });

                // sprawdza kolizje miedzy statkiem a bandami
                if(ship.getObject().getTranslateY() >= 760) stop();
                else if(ship.getObject().getTranslateX() <= 10) stop();
                else if(ship.getObject().getTranslateX() >= 980) stop();
                else if(ship.getObject().getTranslateY() <= 20) stop();
            }
        }.start();
    }

    public void asteroidsWaterfall() // fukcja generujaca asteroidy u gory ekranu
    {
        System.out.println("Level " + level);
        new AnimationTimer(){

            @Override
            public void handle(long l) {

                Platform.runLater(() ->{ // zapobiega zawieszaniu GUI, przydatna w przyszlosci
                    counter++;
                    // warunek sterujacy poziomami
                    if(counter%600 == 0) {
                        level += 0.5;
                        if(level == 5.5)
                        {
                            System.out.println("Game end");
                            stop();
                        }
                        else
                        {
                            double x = 1 +(level-1)*2;
                            System.out.println("Level " + x);
                        }
                    }

                    // waruenk generujacy asteroidy, tak aby sie nie zderzaly
                    if(counter%(60/level) < 1)
                    {
                        boolean collision = false;

                        while(!collision)
                        {
                            Meteor meteor = new Meteor(new RandomMeteor().generateMeteor(),
                                    new Random().nextInt(1001), new Random().nextInt(101),
                                    new Random().nextDouble()/10, (new Random().nextDouble()/2 + 0.5)*level,
                                    new Random().nextDouble());
                            for(Meteor meteor1 : meteorList)
                                if(meteor1.collide(meteor))
                                    collision = true;
                            if(!collision)
                            {
                                meteorList.add(meteor);
                                gamePane.getChildren().add(meteor.getObject());
                                collision = true;
                            }
                        }
                    }

                    for(int i = 0; i < meteorList.size(); i++)
                    {
                        // steruje ruchem asteroid
                        meteorList.get(i).meteorAccelerate(meteorList.get(i).getVx(),
                                meteorList.get(i).getVy(), meteorList.get(i).getRotate());
                        // warunek usuwajacy asteroidy z GamePane po opuszczeniu jej obszaru
                        if(meteorList.get(i).getObject().getTranslateY() >= 700)
                        {
                            gamePane.getChildren().remove(meteorList.get(i).getObject());
                            meteorList.remove(i);
                        }
                        // sprawdza kolizje miedzy asteroidami
                        for(int j = 1; j < meteorList.size() - i; j++)
                            if(meteorList.get(i).collide(meteorList.get(i + j)))
                            {
                                meteorList.get(i).setVx(-meteorList.get(i).getVx());
                                //meteorsList.get(i).setVy(-meteorsList.get(i).getVy());
                                meteorList.get(i + j).setVx(-meteorList.get(i + j).getVx());
                                //meteorsList.get(i + j + 1).setVy(-meteorsList.get(i + j + 1).getVy());
                                meteorList.get(i).setRotate(-meteorList.get(i).getRotate());
                                meteorList.get(i + j).setRotate(-meteorList.get(i + j).getRotate());
                                //System.out.println(meteorsList.get(i).getCenterOfMass());
                            }
                    }
                });
            }
        }.start();
    }

    public void laserBlaster() { // generuje pociski

        new AnimationTimer() {

            @Override
            public void handle(long l) {
                Platform.runLater(() ->{

                    for(int i = 0; i < laserList.size(); i++)
                    {
                        //steruje ruchem pociskow
                        laserList.get(i).laserAccelerate(-3);

                        //sprawdza kolizje pocisku z asteroida
                        for(int j = 0; j < meteorList.size(); j++)
                            if(laserList.get(i).collide(meteorList.get(j)))
                            {
                                shot = true;
                                meteorsShot[i] = 1;
                                bulletsUsed[j] = 1;
                            }

                        // usuwa pociski po opuszczeniu GamePane
                        if(laserList.get(i).getObject().getTranslateY() <= 100)
                        {
                            gamePane.getChildren().remove(laserList.get(i).getObject());
                            laserList.remove(i);
                        }
                    }

                    if(shot)
                    {
                        for (int i=0; i < laserList.size(); i++) {
                            if (Integer.toString(meteorsShot[i]).equals("1")) {
                                for(int j = 0; j < meteorList.size(); j++)
                                {
                                    if(Integer.toString(bulletsUsed[j]).equals("1"))
                                    {
                                        gamePane.getChildren().remove(meteorList.get(j).getObject());
                                        meteorList.remove(j);
                                        gamePane.getChildren().remove(laserList.get(i).getObject());
                                        laserList.remove(i);
                                        meteorsShot[i] = 0;
                                        bulletsUsed[j] = 0;
                                        shot = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }.start();
    }
}
