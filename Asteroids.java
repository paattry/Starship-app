package com.example.demo2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.io.IOException;

public class Asteroids extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Starship game");

        BorderPane pane = new BorderPane();
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK,  CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPrefSize(1000, 800);
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(240, 800);
        ap.setBackground(new Background(new BackgroundFill(Color.GRAY,  CornerRadii.EMPTY, Insets.EMPTY)));

        //Buttons

        Button start = new Button("START");
        start.setLayoutX(30);
        start.setLayoutY(60);
        start.setPrefSize(180, 50);
        Button stats = new Button("STOP");
        stats.setLayoutX(30);
        stats.setLayoutY(150);
        stats.setPrefSize(180, 50);
        Button exit = new Button("RESTART");
        exit.setLayoutX(30);
        exit.setLayoutY(240);
        exit.setPrefSize(180, 50);

        Label someAction = new Label("Tu bedzie gra");
        someAction.setPrefSize(200, 40);
        someAction.setLayoutX(420);
        someAction.setLayoutY(380);

        /*start.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                someAction.setText("Hey, don't touch me you pervert!");
            }
        });

         */

        //Ranking box

        VBox ranking = new VBox();
        ranking.setPrefSize(200, 300);
        ranking.setLayoutX(20);
        ranking.setLayoutY(400);
        ranking.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2))));

        HBox row1 = new HBox();
        row1.setPrefSize(200, 40);
        row1.setLayoutX(0);
        row1.setLayoutY(0);
        HBox row2 = new HBox();
        row2.setPrefSize(200, 40);
        row2.setLayoutX(0);
        row2.setLayoutY(40);
        HBox row3 = new HBox();
        row3.setPrefSize(200, 40);
        row3.setLayoutX(0);
        row3.setLayoutY(80);

        Label player1 = new Label("Jacek");
        Label score1 = new Label("6730");
        player1.setAlignment(Pos.CENTER);
        player1.setPrefSize(100, 40);
        player1.setLayoutY(0); player1.setLayoutY(0);
        score1.setAlignment(Pos.CENTER);
        score1.setPrefSize(100, 40);
        score1.setLayoutX(100); score1.setLayoutY(0);
        row1.getChildren().addAll(player1, score1);

        Label player2 = new Label("Wacek");
        Label score2 = new Label("4560");
        player2.setAlignment(Pos.CENTER);
        player2.setPrefSize(100, 40);
        player2.setLayoutY(0); player2.setLayoutY(0);
        score2.setAlignment(Pos.CENTER);
        score2.setPrefSize(100, 40);
        score2.setLayoutX(100); score2.setLayoutY(0);
        row2.getChildren().addAll(player2, score2);

        Label player3 = new Label("Placek");
        Label score3 = new Label("2890");
        player3.setAlignment(Pos.CENTER);
        player3.setPrefSize(100, 40);
        player3.setLayoutY(0); player3.setLayoutY(0);
        score3.setAlignment(Pos.CENTER);
        score3.setPrefSize(100, 40);
        score3.setLayoutX(100); score3.setLayoutY(0);
        row3.getChildren().addAll(player3, score3);

        Label rankingTitle = new Label("Ranking");
        rankingTitle.setPrefSize(200, 40);
        rankingTitle.setAlignment(Pos.CENTER);

        ranking.getChildren().addAll(rankingTitle, row1, row2, row3);

        Pane emptyPane = new Pane();
        emptyPane.getChildren().add(someAction);
        emptyPane.setPrefSize(760, 800);

        ap.getChildren().addAll(start, stats, exit, ranking);
        pane.setRight(ap);
        pane.setCenter(emptyPane);

        Scene scene = new Scene(pane, 1000, 800);

        Pane pane1 = new Pane();
        pane1.getChildren().add(someAction);
        Scene scene1 = new Scene(pane1, 1000, 800);

        Polygon ship = new Polygon(-15, -15, 10, 0, -15, 15);
        ship.setTranslateX(300);
        ship.setTranslateY(200);
        ship.setRotate(270);

        pane1.getChildren().add(ship);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stage.setScene(scene1);
            }
        });

        scene1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                //ship.setRotate(-30);
                System.out.println("LEFT");
            }

            if (event.getCode() == KeyCode.RIGHT) {
                //ship.setRotate(30);
                System.out.println("RIGHT");
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}