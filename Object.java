package com.example.demo2;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Object {

    Point2D movement;
    Polygon object;
    double Vx, Vy, Rotate;

    public Object(Polygon polygon, double X, double Y)
    {
        this.object = polygon;
        this.object.setTranslateX(X);
        this.object.setTranslateY(Y);
        this.movement = new Point2D(0,0);
    }

    public Object(Polygon polygon, int X, int Y, double Vx, double Vy, double Rotate)
    {
        this.object = polygon;
        this.object.setLayoutX(X);
        this.object.setTranslateY(Y);
        // policz srodek masy
        this.Vx = Vx; this.Vy = Vy;
        this.Rotate = Rotate;
    }

    public Polygon getObject()
    {
        return object;
    }

    public boolean collide(Object other) {
        Shape collisionArea = Shape.intersect(this.object, other.getObject());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}
