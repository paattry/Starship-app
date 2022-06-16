package com.example.demo2;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;

public class Ship extends Object
{

    public Ship(int X, int Y)
    {
        super(new Polygon(0, -15, 10, 15, -10, 15), X, Y);
    }

    public void move()
    {
        this.object.setTranslateX(this.object.getTranslateX() + this.movement.getX());
        this.object.setTranslateY(this.object.getTranslateY() + this.movement.getY());
    }

    public void shipAccelerate(KeyCode code)
    {
        switch (code)
        {
            case LEFT -> this.movement = this.movement.add(-0.1, 0);
            case RIGHT -> this.movement = this.movement.add(0.1, 0);
            case UP -> this.movement = this.movement.add(0, -0.1);
            case DOWN -> this.movement = this.movement.add(0, 0.1);
        }
    }
}
