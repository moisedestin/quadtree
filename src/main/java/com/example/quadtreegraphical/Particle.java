package com.example.quadtreegraphical;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

public class Particle extends Circle{

    double container_width;
    double container_height;
    private double mass;
    private double velocity_x;
    private double velocity_y;
    double fx, fy;


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

    public Particle(double x, double y, double container_width, double container_height, double radius, double mass, Color color) {
        super(x,y,radius, color); // radius = 3
        this.hightlight = false;
        this.container_width = container_width;
        this.container_height = container_height;
        this.mass = mass;
        this.setVelocity(0,0);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setStrokeWidth(100);
    }

    public void setVelocity(double x, double y){
        this.setVelocityX(x);
        this.setVelocityY(y);
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

//        if(this.hightlight){
//            this.setFill(Color.RED);
//        }
//        else{
//            this.setFill(Color.AQUA);
//        }



    }

    public double getVelocityX() {
        return velocity_x;
    }

    public void setVelocityX(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    public double getVelocityY() {
        return velocity_y;
    }

    public void setVelocityY(double velocity_y) {
        this.velocity_y = velocity_y;
    }
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }


    public void addForce(Particle b) {

//        double dx = b.getTranslateX() - a.getTranslateX();
//        double dy = b.getTranslateY() - a.getCenterY();
//        double dist = Math.sqrt(dx*dx + dy*dy);

//        double diffX = Math.abs(a.getTranslateX()-b.getTranslateX()); // valeur absolue deplacement x
//        double diffY = Math.abs(a.getTranslateY()-b.getTranslateY()); // valeur absolue deplacement x y

        double diffX =  this.getTranslateX()-b.getTranslateX() ; // valeur absolue deplacement x
        double diffY =  this.getTranslateY()-b.getTranslateY() ; // valeur absolue deplacement x y
        double distance = Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY, 2)); //distance
        double F = (0.000002 * this.getMass() * b.getMass()) / (Math.pow(distance, 2)); // force masse/distance


        this.fx += F * diffX / distance;
        this.fy += F * diffY / distance;
//        a.fx += F * dx / dist;
//        a.fy += F * dy / dist;
    }

    public void addForce(Point2D p, double mass) {

 //        double diffX = p.getX() - a.getTranslateX();
//        double diffY = p.getY() - a.getCenterY();
//        double distance = Math.sqrt(dx*dx + dy*dy);

//        double diffX = Math.abs(a.getTranslateX()-p.getX()); // valeur absolue deplacement x
//        double diffY = Math.abs(a.getTranslateY()-p.getY()); // valeur absolue deplacement x y
        double diffX =  this.getTranslateX()-p.getX() ; // valeur absolue deplacement x
        double diffY =  this.getTranslateY()-p.getY() ; // valeur absolue deplacement x y
        double distance = Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY, 2)); //distance
//        double F = (0.000002 * a.getMass() * b.getMass()) / (Math.pow(distance, 2)); // force masse/distance


        double F = (0.000002 * this.mass * mass) / (distance*distance  );
        this.fx += F * diffX / distance;
        this.fy += F * diffY / distance;
//        a.fx += F * dx / dist;
//        a.fy += F * dy / dist;
    }

    public void update() {

        setVelocity(
                getVelocityX() + (  fx / mass),
                getVelocityY() + (  fy / mass)
                );

        setTranslateX(getTranslateX() +  getVelocityX());
        setTranslateY(getTranslateY() +  getVelocityY());

    }
}
