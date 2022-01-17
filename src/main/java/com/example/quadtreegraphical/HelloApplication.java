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





        Rectangle2D boundary = new Rectangle2D(100, 100, 600, 600); // with and height are distances from the center( (x,y) which is at (200,200) position, so that's perfectly in the middle) to the edge
        Quadtree quadtree = new Quadtree(boundary, 1);

//        for (int i = 0; i < 1000; i++) {
//            // the point should be minimum the position of the with and maximum the with * 2
//            quadtree.insert(new Point2D(randomBetween(100, 600 ), randomBetween(100, 600))); //just to not leave the boitn to the boundary
////            quadtree.insert(new Point2D(randomBetween(quadtree.boundary.getWidth(), (quadtree.boundary.getWidth() * 2) ), randomBetween(quadtree.boundary.getHeight(), (quadtree.boundary.getHeight() * 2)))); //just to not leave the boitn to the boundary
//        }

        quadtree.show(root);

//        Rectangle rectangle = new Rectangle(150, 150, 200, 200);
//        rectangle.setStroke(Color.GREEN);
//        rectangle.setFill(Color.TRANSPARENT);
//        root.getChildren().add(rectangle);
//
//        List<Point2D> points = quadtree.query(new Rectangle2D(150, 150, 200, 200));
//        for(Point2D point2D: points){
//            Circle circle = new Circle();
//            circle.setCenterX(point2D.getX());
//            circle.setCenterY(point2D.getY());
//            circle.setStrokeWidth(100);
//            circle.setFill(Color.AQUA);
//            circle.setRadius(2);
//            root.getChildren().add(circle);
//        }

        scene.setRoot(root);

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                quadtree.insert(new Point2D(mouseEvent.getX(), mouseEvent.getY())); //just to not leave the boitn to the boundary
                quadtree.show(root);
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();


    }




}