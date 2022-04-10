package com.example.demo2;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
/**/
public abstract class Object {

    Point2D movement;
    Polygon object;
    double Vx, Vy, Rotate;

    public Object(Polygon polygon, int X, int Y)
    {
        this.object = polygon;
        this.object.setTranslateX(X);
        this.object.setTranslateY(Y);
        this.movement = new Point2D(0,0);
    }

    public void shipAccelerate(KeyCode code)
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

    public void asteroidAccelerate(double shiftX, double shiftY, double Rotate)
    {
        this.object.setTranslateX(this.object.getTranslateX() + shiftX);
        this.object.setTranslateY(this.object.getTranslateY() + shiftY);
        this.object.setRotate(this.object.getRotate() + Rotate);
        //this.vertexes[6] = this.vertexes[6] + shiftX; // jak mamy
        //this.vertexes[0] = this.vertexes[0] + shiftX;
        //this.vertexes[2] = this.vertexes[2] + shiftX;
        //this.vertexes[4] = this.vertexes[4] + shiftX;
    }

    public void move()
    {
        this.object.setTranslateX(this.object.getTranslateX() + this.movement.getX());
        this.object.setTranslateY(this.object.getTranslateY() + this.movement.getY());
    }

    public Polygon getObject() {return object;}

    public boolean collide(Object other) {
        Shape collisionArea = Shape.intersect(this.object, other.getObject());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

}
