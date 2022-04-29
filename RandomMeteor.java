package com.example.demo2;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class RandomMeteor {

    private final double[] sizes = new double[5];

    public Polygon generateMeteor()
    {
        Random rand = new Random();
        for(int i = 0; i < 5; i++)
        {
            sizes[i] = 10 + rand.nextInt(16);
        }

        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);

        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(
                sizes[0], 0.0,
                sizes[1] * c1, -1 * sizes[1] * s1,
                -1 * sizes[2] * c2, -1 * sizes[2] * s2,
                -1 * sizes[3] * c2, sizes[3] * s2,
                sizes[4] * c1, sizes[4] * s1);

        /*polygon.getPoints().addAll(
                sizes[0], 0.0,
                sizes[0] * c1, -1 * sizes[0] * s1,
                -1 * sizes[0] * c2, -1 * sizes[0] * s2,
                -1 * sizes[0] * c2, sizes[0] * s2,
                sizes[0] * c1, sizes[0] * s1);

         */

        return polygon;
    }
}
