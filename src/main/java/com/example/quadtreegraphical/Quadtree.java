package com.example.quadtreegraphical;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Quadtree {
        Rectangle2D boundary;
        boolean divided = false;
        int capacity; // capacity
        Point2D points;
        Quadtree quadtreenw;
        Quadtree quadtreene;
        Quadtree quadtreesw;
        Quadtree quadtreese;

        Quadtree(Rectangle2D boundary, int n ) {
            this.boundary = boundary;
            this.capacity = n;
            this.points = null;
        }

        public void insert(Point2D point) {


            if (  this.boundary.contains(point)){
                if (this.points == null) {
                    this.points = point;
                } else {

                    if (!divided) {
                        this.subdivide();


                            if(!point.equals(points)){
                                this.quadtreese.insert(points);
                                this.quadtreenw.insert(points);
                                this.quadtreesw.insert(points);
                                this.quadtreene.insert(points);
                            }

                    }


                    this.quadtreese.insert(point);
                    this.quadtreenw.insert(point);
                    this.quadtreesw.insert(point);
                    this.quadtreene.insert(point);
                }
            }



        }

        private void subdivide() {

            double x = this.boundary.getMinX();
            double y = boundary.getMinY();
            double w = boundary.getWidth();
            double h = boundary.getHeight();


            this.quadtreenw = new Quadtree(new Rectangle2D(x, y, w / 2, h / 2), this.capacity); // if we took coordinstr as  100 100 it would be inacurate, draw it
            this.quadtreene = new Quadtree(new Rectangle2D(x + (w / 2), y, w / 2, h / 2), this.capacity);
            this.quadtreesw = new Quadtree(new Rectangle2D(x, y + (h / 2), w / 2, h / 2), this.capacity);
            this.quadtreese = new Quadtree(new Rectangle2D(x + (w / 2), y + (h / 2), w / 2, h / 2), this.capacity);
 
            this.divided = true;

        }


        public void show(AnchorPane root) {
            Rectangle rectangle = new Rectangle(this.boundary.getMinX(), this.boundary.getMinY(), this.boundary.getWidth(), this.boundary.getHeight());
            rectangle.setStroke(Color.RED);
            rectangle.setFill(Color.TRANSPARENT);
            root.getChildren().add(rectangle);
            if(points != null){
                Circle circle = new Circle();
                circle.setCenterX(points.getX());
                circle.setCenterY(points.getY());
                circle.setStrokeWidth(100);
                circle.setFill(Color.BLACK);
                circle.setRadius(2);
                root.getChildren().add(circle);

            }

            if(this.divided){

                this.quadtreenw.show(root);
                this.quadtreene.show(root);
                this.quadtreesw.show(root);
                this.quadtreese.show(root);
            }
        }

    @Override
    public String toString() {
        return "Quadtree{" +
                "boundary=" + boundary +
                ", divided=" + divided +
                ", capacity=" + capacity +
                ", points=" + points +
                ", quadtreenw=" + quadtreenw +
                ", quadtreene=" + quadtreene +
                ", quadtreesw=" + quadtreesw +
                ", quadtreese=" + quadtreese +
                '}';
    }
}
