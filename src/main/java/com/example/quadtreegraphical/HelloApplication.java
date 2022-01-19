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
        for (int i = 0; i < 5000; i++) {

            double radius = randomBetween(3, 6);

            Particle particle = new Particle(
                    randomBetween(50, 750 ),
                    randomBetween(50, 750),
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


                for(Particle a : particles) {
                    for(Particle b : particles) {
                        if(!a.equals(b)) {
                            a.setVelocity(
                                    a.getVelocityX()+a.getForceX(b),
                                    a.getVelocityY()+a.getForceY(b));


                        }

//                        if(a.getTranslateX()>b.getTranslateX())
//                            delta[0] *= -1;
//
//                        if(a.getTranslateY()>b.getTranslateY())
//                            delta[1] *= -1;

//                        a.setVelocity(a.getVelocityX()+ ta[0], a.getVelocityY() ta[1]);

                    }
                }



                for(Particle b : particles) {
                    b.setCenterX(b.getCenterX()+b.getVelocityX());
                    b.setCenterY(b.getCenterY() + b.getVelocityY());

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