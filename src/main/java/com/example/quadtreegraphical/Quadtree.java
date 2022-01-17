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
        ArrayList<Point2D> points;
        Quadtree quadtreenw;
        Quadtree quadtreene;
        Quadtree quadtreesw;
        Quadtree quadtreese;

        Quadtree(Rectangle2D boundary, int n ) {
            this.boundary = boundary;
            this.capacity = n;
            this.points = new ArrayList<>();
//            this.divided = divided;
        }

        public void insert(Point2D point) {


            if (  this.boundary.contains(point)){
                if (this.points.size() < this.capacity) {
                    this.points.add(point);
                } else {

                    if (!divided) {
                        this.subdivide();

                        for (Point2D existingpoint :
                                points) {
                            if(!point.equals(existingpoint)){
                                this.quadtreese.insert(existingpoint);
                                this.quadtreenw.insert(existingpoint);
                                this.quadtreesw.insert(existingpoint);
                                this.quadtreene.insert(existingpoint);
                            }

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

        public List<Point2D> query(Rectangle2D queryRectangle2D){
            List<Point2D> results = new ArrayList<Point2D>();
            if(!this.boundary.intersects(queryRectangle2D)){
            }
            else{
                for (Point2D point2D : points) {
                    if(queryRectangle2D.contains(point2D)){
                        results.add(point2D);
                    }
                }

                if(this.divided){
                    results.addAll(this.quadtreenw.query(queryRectangle2D));
                    results.addAll(this.quadtreene.query(queryRectangle2D));
                    results.addAll(this.quadtreesw.query(queryRectangle2D));
                    results.addAll(this.quadtreese.query(queryRectangle2D));
                }
            }

            return results;


        }

        public void show(AnchorPane root) {
            Rectangle rectangle = new Rectangle(this.boundary.getMinX(), this.boundary.getMinY(), this.boundary.getWidth(), this.boundary.getHeight());
            rectangle.setStroke(Color.RED);
            rectangle.setFill(Color.TRANSPARENT);
            root.getChildren().add(rectangle);
            for(Point2D point2D: points){
                Circle circle = new Circle();
                circle.setCenterX(point2D.getX());
                circle.setCenterY(point2D.getY());
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
