package com.example.demo2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/**/
public class Asteroids extends Application {

    BorderPane pane;
    Pane gamePane, emptyPane;
    AnchorPane ap;
    Scene GUIScene, gameScene;
    Button start, stats, exit;
    Label someAction, player1, player2, player3, score1, score2, score3, rankingTitle;
    VBox ranking;
    HBox row1, row2, row3;
    List<Meteor> meteorsList;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Starship game");

        setStartingGUI();
        setGameGUI();
        setGUIFunctionality(stage);

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
        gamePane.getChildren().add(someAction);
        gameScene = new Scene(gamePane, 1000, 800);
    }

    public void setGUIFunctionality(Stage stage)
    {
        start.setOnAction(e ->
        {
            meteorsList = new ArrayList<>();
            stage.setScene(gameScene);

            for(int i = 0; i < 12; i++)
                meteorsList.add(new Meteor(new RandomMeteor().generateMeteor(),
                        new Random().nextInt(1001), new Random().nextInt(801),
                        new Random().nextDouble() - 0.5, new Random().nextDouble() - 0.5,
                        new Random().nextDouble()));

            shipMovement();
        });
    }

    public void shipMovement()
    {
        HashMap<KeyCode, Boolean> keyPressed = new HashMap<>();

        gameScene.setOnKeyPressed(event -> keyPressed.put(event.getCode(), Boolean.TRUE));
        gameScene.setOnKeyReleased(event -> keyPressed.put(event.getCode(), Boolean.FALSE));

        Ship ship = new Ship(300, 200);
        gamePane.getChildren().add(ship.getObject());

        for(Meteor meteors : meteorsList)
            gamePane.getChildren().add(meteors.getObject());

        new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {

                if(keyPressed.getOrDefault(KeyCode.LEFT, false))
                    ship.shipAccelerate(KeyCode.LEFT);

                if(keyPressed.getOrDefault(KeyCode.RIGHT, false))
                    ship.shipAccelerate(KeyCode.RIGHT);

                if(keyPressed.getOrDefault(KeyCode.UP, false))
                    ship.shipAccelerate(KeyCode.UP);

                if(keyPressed.getOrDefault(KeyCode.DOWN, false))
                    ship.shipAccelerate(KeyCode.DOWN);

                for(Meteor meteors : meteorsList)
                    meteors.asteroidAccelerate(meteors.getVx(), meteors.getVy(), meteors.getRotate());
                
                ship.move();

                for(int i = 0; i < meteorsList.size() -1; i++)
                    for(int j = 0; j < meteorsList.size() - i - 1; j++)
                        if(meteorsList.get(i).collide(meteorsList.get(i + j + 1)))
                        {
                            meteorsList.get(i).setVx(-meteorsList.get(i).getVx());
                            meteorsList.get(i).setVy(-meteorsList.get(i).getVy());
                            meteorsList.get(i + j + 1).setVx(-meteorsList.get(i + j + 1).getVx());
                            meteorsList.get(i + j + 1).setVy(-meteorsList.get(i + j + 1).getVy());
                            meteorsList.get(i).setRotate(-meteorsList.get(i).getRotate());
                            meteorsList.get(i + j + 1).setRotate(-meteorsList.get(i + j + 1).getRotate());
                        }

            }
        }.start();
    }

    public double averageSpeed(double speed1, double speed2)
    {
        return (speed1 + speed2)/2;
    }

}
