package com.example.quadtreegraphical;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.quadtreegraphical.Utils.randomBetween;

public class HelloApplication extends Application {

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;


    List<Color> colors = new ArrayList<>() {{
        add(Color.RED);
        add(Color.BLUE);
        add(Color.YELLOW);
        add(Color.ORANGE);
        add(Color.PURPLE);
        add(Color.AQUA);

    }
    };

    @Override
    public void start(Stage stage) throws IOException {

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 800, 800, Color.BLACK);
        stage.setScene(scene);


        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {

            double radius = randomBetween(3, 20);

            Particle particle = new Particle(
                    randomBetween(100, 600 ),
                    randomBetween(100, 600),
                    scene.getWidth(),
                    scene.getHeight(),
                    radius,
                    radius*50,
                    Utils.randomFromList(colors));
            particles.add(particle);

            root.getChildren().add(particle);
         }

        new AnimationTimer() {

            @Override
            public void handle(long now) {

                long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = now ;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    System.out.println(String.format("Current frame rate: %.3f", frameRate));
                }

                Quadtree quadtree = new Quadtree(new Rectangle2D(0,0,800,800), 1);

                for (Particle particle : particles) {
                    quadtree.insert(new PointParticle(particle.getCenterX(), particle.getCenterY(), particle));
                }

                for (Particle particle : particles) {
                    quadtree.updateForce(new PointParticle(particle.getCenterX(), particle.getCenterY(), particle));
                    particle.update();
                }
//                CalcDeltas(particles);
//                for(Particle b : particles) {
//                    b.setTranslateX(b.getTranslateX()+b.getVelocityX());
//                    b.setTranslateY(b.getTranslateY() + b.getVelocityY());
//
//                }

//                for (Particle particle :
//                        particles) {
//                    particle.move();
//                    particle.setHightlight(false);
//
//                }

//                for (Particle particle :
//                        particles) {
//                    for(Particle other : particles){
//                        if(!other.equals(particle) && particle.intersects(other.getBoundsInParent())){
//                            particle.setHightlight(true);
//                        }
//                    }
//                }

            }
            }.start();


        scene.setRoot(root);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void CalcDeltas(List<Particle> bodies) {
        for(Particle a : bodies) {
            for(Particle b : bodies) {
                if(a.equals(b)) {
                    continue;
                }
                double[] delta = getG(a, b);

                if(a.getTranslateX()>b.getTranslateX())
                    delta[0] *= -1;

                if(a.getTranslateY()>b.getTranslateY())
                    delta[1] *= -1;

                a.setVelocity(a.getVelocityX()+delta[0], a.getVelocityY()+delta[1]);
            }
        }
    }

    public static double[] getG(Particle a, Particle b) {
        double diffX = Math.abs(a.getTranslateX()-b.getTranslateX()); // valeur absolue deplacement x
        double diffY = Math.abs(a.getTranslateY()-b.getTranslateY()); // valeur absolue deplacement x y
        double radius = Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY, 2)); //distance
        double f = (0.000002 * a.getMass() * b.getMass()) / (Math.pow(radius, 2)); // force masse/distance
        return new double[] {diffX*f, diffY*f};

    }

}