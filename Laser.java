package com.example.demo2;

import javafx.scene.shape.Polygon;

public class Laser extends Object{
    public Laser(double X, double Y) {
        super(new Polygon(5, -5, 5, 5, -5, 5, -5, -5), X, Y);
    }

    public void laserAccelerate(double shiftY)
    {
        this.object.setTranslateY(this.object.getTranslateY() + shiftY);
    }
}
