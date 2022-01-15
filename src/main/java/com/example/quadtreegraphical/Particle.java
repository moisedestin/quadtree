package com.example.quadtreegraphical;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.security.auth.login.LoginContext;
import java.util.concurrent.ThreadLocalRandom;

public class Particle extends Circle{

    double container_width;
    double container_height;

    // distance (in pixels) traveled per unit of time
    private static final int dist = 10;

    public void setHightlight(boolean hightlight) {
        this.hightlight = hightlight;
    }

    boolean hightlight;
    private int dx; // change in x-coordinate of particle's center
    private int dy; // change in y-coordinate of particle's center

    public Particle(double x, double y, double container_width, double container_height, Color color) {
        super(x,y,3, color); // radius = 3
        this.hightlight = false;
        this.container_width = container_width;
        this.container_height = container_height;
        this.setStrokeWidth(100);

    }

    public void move(){

        double x = this.getCenterX();
        double y = this.getCenterY();
        double r =this.getRadius();

        // On impact with vertical boundary, reflect horizontally.
        if (x <= r || x >= container_width - r) {
            dx = -dx;
        }

        // On impact with horizontal boundary, reflect vertically.
        if (y <= r || y >= container_height - r) {
            dy = -dy;
        }

        // new location of particle
//        double newX = x + dx;
//        double newY = y + dy;

        // Make sure that new location is within the container.
//        newX = Math.max(newX, r);
//        newX = Math.min(newX, container_width - r);
//        newY = Math.max(newY, r);
//        newY = Math.min(newY, container_height - r);


        double newX = this.getCenterX()  ;
        double newY = this.getCenterY()  ;

        ThreadLocalRandom rand = ThreadLocalRandom.current();

        if(rand.nextBoolean()){
            if(rand.nextBoolean()){
                // right down
                newX += 1;
                newY += 1;

            }
            else{
                // left up
                newX -= 1;
                newY -= 1;
            }
        }
        else{
            if(rand.nextBoolean()){
                // left down
                newX -= 1;
                newY += 1;
            }
            else{
                // right up
                newX += 1;
                newY -= 1;
            }
        }


        // eviter qu'il depasse les bordures
        if(newX == this.container_width - 5){
            newX += 1;
        }
        if(newX == this.container_height - 5){
            newY += 1;
        }
        if(newX == this.container_width - 5){
            newX -= 1;
        }
        if(newY == this.container_height - 5){
            newY -= 1;
        }

        this.setCenterX(newX); ;
        this.setCenterY(newY);

        if(this.hightlight){
            this.setFill(Color.RED);
        }
        else{
            this.setFill(Color.AQUA);
        }

    }



}
