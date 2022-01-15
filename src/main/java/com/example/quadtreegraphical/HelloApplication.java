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

    @Override
    public void start(Stage stage) throws IOException {

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);


        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Particle particle = new Particle(randomBetween(100, 600 ), randomBetween(100, 600), scene.getWidth(), scene.getHeight(), Color.AQUA);
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

                for (Particle particle :
                        particles) {
                    particle.move();
                    particle.setHightlight(false);

                }

                for (Particle particle :
                        particles) {
                    for(Particle other : particles){
                        if(!other.equals(particle) && particle.intersects(other.getBoundsInParent())){
                            particle.setHightlight(true);
                        }
                    }
                }

            }
            }.start();


        scene.setRoot(root);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}