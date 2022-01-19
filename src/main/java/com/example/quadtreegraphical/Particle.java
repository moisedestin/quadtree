package com.example.quadtreegraphical;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

public class Particle extends Circle{

    double container_width;
    double container_height;
    private double mass;
    private double velocity_x;
    private double velocity_y;
    private double force_x;
    private double force_y;


    public void setHightlight(boolean hightlight) {
        this.hightlight = hightlight;
    }

    boolean hightlight;

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
//        this.setTranslateX(x);
//        this.setTranslateY(y);
        this.setStrokeWidth(100);
    }

    public void setVelocity(double x, double y){
        this.setVelocityX(x);
        this.setVelocityY(y);
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

    public double getDiffXFromParticle(Particle b){
//        return Math.abs(this.getTranslateX()-b.getTranslateX()); // valeur absolue deplacement x
        return this.getCenterX()-b.getCenterX(); // valeur absolue deplacement x
    }

    public double getDiffYFromParticle(Particle b){
//        return Math.abs(this.getTranslateY()-b.getTranslateY()); // valeur absolue deplacement y
        return this.getCenterY()-b.getCenterY(); // valeur absolue deplacement y
    }

    public double getDistanceFromParticle(Particle b){
        return Math.sqrt(Math.pow(this.getDiffXFromParticle(b), 2)+Math.pow(this.getDiffYFromParticle(b), 2));
    }

    public double getForce(Particle b){
        return (0.000002 * this.getMass() * b.getMass()) / (Math.pow(this.getDistanceFromParticle(b), 2));
    }

    public double getForceX(Particle b){
        return this.getForce(b)*this.getDiffXFromParticle(b)/this.getDistanceFromParticle(b);
    }
    public double getForceY(Particle b){
        return this.getForce(b)*this.getDiffYFromParticle(b)/this.getDistanceFromParticle(b);
    }


}
