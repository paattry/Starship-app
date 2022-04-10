package com.example.demo2;

import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;
/**/
public class Meteor extends Object{

    private double Vx, Vy, Rotate, Mass;
    private List<Double> vertexes; // bedzie potrzebna potem
    private List<Double> centerOfMass;
    private boolean collision = true; // bedzie potrzebna potem

    public Meteor(Polygon meteor, int X, int Y, double Vx, double Vy, double Rotation)
    {
        super(meteor, X, Y);
        this.vertexes = meteor.getPoints();
        this.Vx = Vx;
        this.Vy = Vy;
        this.Rotate = Rotation*10-5;

        double xCenter = 0, yCenter = 0;
        List<Double> tmp = new ArrayList<>();
        for(int i = 0; i < 10; i+=2)
        {
            xCenter += meteor.getPoints().get(i);
            yCenter += meteor.getPoints().get(i + 1);
        }
        tmp.add(xCenter);
        tmp.add(yCenter);
        this.centerOfMass = tmp;
    }

    public double getVx(){return Vx;} // nie możemy zrobic overright z klasy Object bo sie wywala metoda collide
    public void setVx(double Vx){this.Vx = Vx;}
    public double getVy(){return Vy;}
    public void setVy(double Vy) {this.Vy = Vy;}

    public double getRotate() {return Rotate;}
    public void setRotate(double rotate) {Rotate = rotate;}

    public List<Double> getCenterOfMass() {return centerOfMass;}
    public void setMass(List<Double> centerOfMass) {this.centerOfMass = centerOfMass;}

    public boolean getCollision() {return collision;}
    public void setCollision(boolean collision) {this.collision = collision;}

}
