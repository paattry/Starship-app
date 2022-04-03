package com.example.demo2;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;

public class Ship {

    private Point2D movement;
    private Polygon ship;

    public Ship(int X, int Y)
    {
        this.ship = new Polygon(-10, -10, 20, 0, -10, 10);
        this.ship.setTranslateX(X);
        this.ship.setTranslateY(Y);
        this.ship.setRotate(270);
        this.movement = new Point2D(0, 0);
    }

    public void turnRight()
    {
        this.ship.setRotate(this.ship.getRotate() + 5);
    }

    public void turnLeft()
    {
        this.ship.setRotate(this.ship.getRotate() - 5);
    }

    public void accelerate(KeyCode code)
    {
        if(code == KeyCode.LEFT)
            this.movement = this.movement.add(-0.1, 0);
        if(code == KeyCode.RIGHT)
            this.movement = this.movement.add(0.1, 0);
        if(code == KeyCode.UP)
            this.movement = this.movement.add(0, -0.1);
        if(code == KeyCode.DOWN)
            this.movement = this.movement.add(0, 0.1);
    }

    public void move()
    {
        this.ship.setTranslateX(this.ship.getTranslateX() + this.movement.getX());
        this.ship.setTranslateY(this.ship.getTranslateY() + this.movement.getY());

    }

    public Polygon getShip()
    {
        return ship;
    }


}
