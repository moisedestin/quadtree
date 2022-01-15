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
        for (int i = 0; i < 5000; i++) {
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


                Quadtree quadtree = new Quadtree(new Rectangle2D(0,0,800,800), 4);
                for (Particle particle :
                        particles) {
                    particle.move();
                    particle.setHightlight(false);
                    quadtree.insert(new PointParticle(particle.getCenterX(), particle.getCenterY(), particle));

                }

                for (Particle particle :
                        particles) {
                    List<PointParticle> otherParticlesWithinTheRange = quadtree.query(new Circle(particle.getCenterX(), particle.getCenterY(), particle.getRadius()*2));
//                    for(Particle other : particles){
//                        if(!other.equals(particle) && particle.intersects(other.getBoundsInParent())){
//                            particle.setHightlight(true);
//                        }
//                    }
                    for(PointParticle point : otherParticlesWithinTheRange){
                        Particle other = point.getParticle();
                        if(!other.equals(particle) && particle.intersects(other.getBoundsInParent())){
                            particle.setHightlight(true);
                        }
                    }
                }

            }
            }.start();






        scene.setRoot(root);



//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

//        AnchorPane root = new AnchorPane();
//        Scene scene = new Scene(root, 800, 800);
//        stage.setScene(scene);
//
//
//
//
//
//        Rectangle2D boundary = new Rectangle2D(100, 100, 600, 600); // with and height are distances from the center( (x,y) which is at (200,200) position, so that's perfectly in the middle) to the edge
//        Quadtree quadtree = new Quadtree(boundary, 4);
//
//        for (int i = 0; i < 1000; i++) {
//            // the point should be minimum the position of the with and maximum the with * 2
//            quadtree.insert(new Point2D(randomBetween(100, 600 ), randomBetween(100, 600))); //just to not leave the boitn to the boundary
////            quadtree.insert(new Point2D(randomBetween(quadtree.boundary.getWidth(), (quadtree.boundary.getWidth() * 2) ), randomBetween(quadtree.boundary.getHeight(), (quadtree.boundary.getHeight() * 2)))); //just to not leave the boitn to the boundary
//        }
//
//        quadtree.show(root);
//
//        Rectangle rectangle = new Rectangle(150, 150, 200, 200);
//        rectangle.setStroke(Color.GREEN);
//        rectangle.setFill(Color.TRANSPARENT);
//        root.getChildren().add(rectangle);
//
//        List<Point2D> points = quadtree.query(new Rectangle2D(150, 150, 200, 200));
//        System.out.println(points.size());
//        for(Point2D point2D: points){
//            Circle circle = new Circle();
//            circle.setCenterX(point2D.getX());
//            circle.setCenterY(point2D.getY());
//            circle.setStrokeWidth(100);
//            circle.setFill(Color.AQUA);
//            circle.setRadius(2);
//            root.getChildren().add(circle);
//        }
//
//        scene.setRoot(root);
//
//        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                quadtree.insert(new Point2D(mouseEvent.getX(), mouseEvent.getY())); //just to not leave the boitn to the boundary
//                quadtree.show(root);
//            }
//        });
//
//        stage.show();
    }




}