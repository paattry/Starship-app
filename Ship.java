package com.example.demo2;

import javafx.scene.shape.Polygon;

public class Ship extends Object {

    public Ship(int X, int Y)
    {
        super(new Polygon(-10, -10, 20, 0, -10, 10), X, Y);
        super.object.setRotate(270);
    }
}
