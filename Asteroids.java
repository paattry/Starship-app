package com.example.demo2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Asteroids extends Application {

    StackPane startingPane;
    StackPane gamePane;
    Pane emptyPane, firstLayer, secondLayer, thirdLayer;
    AnchorPane ap;
    Scene GUIScene, gameScene;
    Button start, stats, exit, stopButton, language, language2, heartButton, heartButton2, heartButton3;
    //Text pointsLabel;
    Label someAction, player1, player2, player3, score1, score2, score3, rankingTitle, pointsLabel, life, lifeCount;
    VBox ranking;
    HBox row1, row2, row3;
    List<Meteor> meteorList;
    List<Laser> laserList;
    Thread asteroidsThread, laserThread;
    Image image, image2;
    ImageView heart3, heart4;
    int counter = 0;
    int points = 0;
    int heartsNumber = 0;
    double level = 1;
    boolean spacePressed = true;
    boolean shot = false;
    boolean asteroidsThreadRunning = true;
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
        StackPane root = new StackPane();
        startingPane = new StackPane();
        firstLayer = new Pane();
        secondLayer = new Pane();
        Image image = new Image("https://d27nqrvkk22y65.cloudfront.net/product_image/image/143280/big_5a42725f2c.jpg", 1000, 825, false, false);
        ImageView background = new ImageView(image);
        //root.setStyle("-fx-background-image: url('https://cdn.gamedevmarket.net/wp-content/uploads/20191203202550/6ce750b040c02c62d2b0fecf796da64d95548f4d.jpg')");
        //startingPane.setBackground(new Background(new BackgroundFill(root)));
       // startingPane.setBackground(new Background(new BackgroundFill(newStack,  CornerRadii.EMPTY, Insets.EMPTY)));
        startingPane.setPrefSize(1400, 1050);
        firstLayer.getChildren().add(background);
        //ap = new AnchorPane();
        //ap.setPrefSize(240, 800);
       // ap.setBackground(new Background(new BackgroundFill(Color.GRAY,  CornerRadii.EMPTY, Insets.EMPTY)));

        //Buttons
        start = new Button("START");
        start.setLayoutX(410);
        start.setLayoutY(600);
        start.setPrefSize(180, 50);

        language = new Button ("Polish ");
        language.setLayoutX(800);
        language.setLayoutY(50);
        language.setPrefSize(180,50);

        language2 = new Button ("English ");
        language2.setLayoutX(600);
        language2.setLayoutY(50);
        language2.setPrefSize(180,50);
        //stats = new Button("STOP");
        //stats.setLayoutX(30);
        //stats.setLayoutY(150);
        //stats.setPrefSize(180, 50);
        //exit = new Button("RESTART");
        //exit.setLayoutX(30);
       // exit.setLayoutY(240);
       // exit.setPrefSize(180, 50);
/*
        someAction = new Label("Tu bedzie gra");
        someAction.setPrefSize(200, 40);
        someAction.setLayoutX(420);
        someAction.setLayoutY(380);

 */
/*
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
*/
        //gamepane pane numero 2
        emptyPane = new Pane();
        //.getChildren().add(someAction);
        emptyPane.setPrefSize(760, 800);

        stopButton = new Button("Stop");
        stopButton.setFocusTraversable(false);
        stopButton.setPrefSize(100, 50);
        stopButton.setLayoutX(890);
        stopButton.setLayoutY(10);

        /*
        heartButton = new Button();
        Image image2 = new Image("https://th.bing.com/th/id/R.42a9481e03b7893823e7a3847c0bbbf9?rik=XiAzfn0ZnGczXg&pid=ImgRaw&r=0", 50, 50, false, false);
        ImageView heart = new ImageView(image2);
        heartButton.setGraphic(heart);
        heartButton.setLayoutX(790);
        heartButton.setLayoutY(10);

       // heartButton2 = new Button();
        Image image3 = new Image("https://th.bing.com/th/id/R.42a9481e03b7893823e7a3847c0bbbf9?rik=XiAzfn0ZnGczXg&pid=ImgRaw&r=0", 50, 50, false, false);
        ImageView heart3 = new ImageView(image3);
        heart3.setImage(image3);
      //  heartButton2.setGraphic(heart3);
       // heartButton2.setLayoutX(720);
       // heartButton2.setLayoutY(10);

       // heartButton3 = new Button();
        Image image4 = new Image("https://th.bing.com/th/id/R.42a9481e03b7893823e7a3847c0bbbf9?rik=XiAzfn0ZnGczXg&pid=ImgRaw&r=0", 50, 50, false, false);
        ImageView heart4 = new ImageView(image4);
        heart4.setImage(image4);
       // heartButton3.setGraphic(heart4);
        //heartButton3.setLayoutX(650);
        //heartButton3.setLayoutY(10);
        /*
        */

        pointsLabel = new Label(String.valueOf(points));
        pointsLabel.setPrefSize(60, 40);
        pointsLabel.setLayoutX(60);
        pointsLabel.setLayoutY(0);
        pointsLabel.setBackground(new Background(new BackgroundFill(Color.WHITE,  CornerRadii.EMPTY, Insets.EMPTY)));

        life = new Label("Life");
        life.setPrefSize(60, 40);
        life.setLayoutX(120);
        life.setLayoutY(0);
        life.setBackground(new Background(new BackgroundFill(Color.WHITE,  CornerRadii.EMPTY, Insets.EMPTY)));

        lifeCount = new Label("3");
        lifeCount.setPrefSize(60, 40);
        lifeCount.setLayoutX(180);
        lifeCount.setLayoutY(0);
        lifeCount.setBackground(new Background(new BackgroundFill(Color.WHITE,  CornerRadii.EMPTY, Insets.EMPTY)));
// pane numero 2

        //ap.getChildren().addAll(start, stats, exit, ranking);
        //ap.getChildren().addAll(start);
        secondLayer.getChildren().add(start);
        secondLayer.getChildren().add(language);
        secondLayer.getChildren().add(language2);
        startingPane.getChildren().addAll(firstLayer, secondLayer);
       // startingPane.setCenter(start);
        //startingPane.setLeft(ap);
        //startingPane.setCenter(emptyPane);

        GUIScene = new Scene(startingPane, 1000, 800);
    }

    public void setGameGUI()
    {
        gamePane = new StackPane();
        firstLayer = new Pane();
        secondLayer = new Pane();
        thirdLayer = new Pane();

        firstLayer.setBackground(new Background(new BackgroundFill(Color.WHITE,  CornerRadii.EMPTY, Insets.EMPTY)));
        //thirdLayer.getChildren().addAll(heartButton, heartButton2, heartButton3);
        //secondLayer.getChildren().addAll(stopButton, heartButton, heart3, heart4);
        //secondLayer.getChildren().addAll(stopButton);
        secondLayer.getChildren().addAll(stopButton, pointsLabel, life, lifeCount);
        gamePane.getChildren().addAll(firstLayer, secondLayer);
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
            asteroidsThread = new Thread(asteroidsTask); // watek sterujacy asteroidami
            asteroidsThread.setDaemon(true);
            asteroidsThread.start();

            Runnable laserTask = this::laserBlaster;
            laserThread = new Thread(laserTask); // watek sterujacy pociskami
            laserThread.setDaemon(true);
            laserThread.start();
        });

        language.setOnAction(event ->
        {
            Platform.runLater(() -> {
                language.setText("Polski");
                language2.setText("Angielski");
                life.setText("Życia");
            });
        });

        language2.setOnAction(event ->
        {
            Platform.runLater(() -> {
                language.setText("Polish");
                language2.setText("English");
                life.setText("Life");
            });
        });

        stopButton.setOnAction(e ->
        {
            asteroidsThreadRunning = !asteroidsThreadRunning;
        });
    }

    public void shipMovement()
    {
        HashMap<KeyCode, Boolean> keyPressed = new HashMap<>();

        gameScene.setOnKeyPressed(event -> keyPressed.put(event.getCode(), Boolean.TRUE));
        gameScene.setOnKeyReleased(event -> keyPressed.put(event.getCode(), Boolean.FALSE));

        keyPressed.put(KeyCode.SPACE, Boolean.FALSE);

        Ship ship = new Ship(500, 500);
        firstLayer.getChildren().add(ship.getObject());

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
                        firstLayer.getChildren().add(laser.getObject());
                    }
                    spacePressed = false;
                }

                if(!keyPressed.get(KeyCode.SPACE))
                    spacePressed = true;

                ship.move(); // rusza statkiem

                // sprawdza kolizje miedzy statkiem a asteroidami
                meteorList.forEach(asteroid -> {
                    Platform.runLater(() ->
                    {
                        if (ship.collide(asteroid)) {
                            if(heartsNumber == 0)
                            {
                                firstLayer.getChildren().remove(asteroid.getObject());
                                meteorList.remove(asteroid);
                                //secondLayer.getChildren().remove(heartButton);
                                lifeCount.setText("2");
                            }
                            else if(heartsNumber == 1)
                            {
                                firstLayer.getChildren().remove(asteroid.getObject());
                                meteorList.remove(asteroid);
                                //secondLayer.getChildren().remove(heartButton2);
                                lifeCount.setText("1");
                            }
                            else if(heartsNumber == 2)
                            {
                                //firstLayer.getChildren().remove(asteroid.getObject());
                                //meteorList.remove(asteroid);
                                //secondLayer.getChildren().remove(heartButton3);
                                lifeCount.setText("0");
                                //heartsNumber++;
                                stop();
                            }
                            heartsNumber++;
                            System.out.println(heartsNumber);
                        }
                    });
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

                if(asteroidsThreadRunning){
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
                                    firstLayer.getChildren().add(meteor.getObject());
                                    //gamePane.getChildren().remove(pointsLabel);
                                    //gamePane.getChildren().add(pointsLabel);
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
                                firstLayer.getChildren().remove(meteorList.get(i).getObject());
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
                if(heartsNumber == 3)
                    stop();
            }
        }.start();
    }

    public void laserBlaster()
    { // generuje pociski

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
                                points++;
                                pointsLabel.setText(String.valueOf(points));
                                meteorsShot[i] = 1;
                                bulletsUsed[j] = 1;
                            }

                        // usuwa pociski po opuszczeniu GamePane
                        if(laserList.get(i).getObject().getTranslateY() <= 100)
                        {
                            firstLayer.getChildren().remove(laserList.get(i).getObject());
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
                                        firstLayer.getChildren().remove(meteorList.get(j).getObject());
                                        meteorList.remove(j);
                                        firstLayer.getChildren().remove(laserList.get(i).getObject());
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