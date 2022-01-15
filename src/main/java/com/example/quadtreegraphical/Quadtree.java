package com.example.quadtreegraphical;


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
        ArrayList<PointParticle> points;
        Quadtree quadtreenw;
        Quadtree quadtreene;
        Quadtree quadtreesw;
        Quadtree quadtreese;

        Quadtree(Rectangle2D boundary, int n) {
            this.boundary = boundary;
            this.capacity = n;
            this.points = new ArrayList<>();
        }

        public void insert(PointParticle point) {

//            System.out.println(this.boundary);
            if (  this.boundary.contains(point)){
                if (this.points.size() < this.capacity) {
                    this.points.add(point);

                } else {

                    if (!divided) {

                        this.subdivide();
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



//            this.quadtreenw = new Quadtree(new Rectangle2D(x - (w / 2), y - (y / 2), w / 2, h / 2), this.capacity); // if we took coordinstr as  100 100 it would be inacurate, draw it
//            this.quadtreene = new Quadtree(new Rectangle2D(x + (w / 2), y - (y / 2), w / 2, h / 2), this.capacity);
//            this.quadtreesw = new Quadtree(new Rectangle2D(x - (w / 2), y + (y / 2), w / 2, h / 2), this.capacity);
//            this.quadtreese = new Quadtree(new Rectangle2D(x + (w / 2), y + (y / 2), w / 2, h / 2), this.capacity);
            this.divided = true;

        }

        public List<PointParticle> query(Rectangle2D queryRectangle2D){
            List<PointParticle> results = new ArrayList<PointParticle>();
            if(!this.boundary.intersects(queryRectangle2D)){
            }
            else{
                for (PointParticle point2D : points) {
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

        public boolean intersects(Circle circle){
//            def intersect(Circle(P, R), Rectangle(A, B, C, D)):
//            S = Circle(P, R)
//            return (this.boundary.contains(circle.getCenterX(), circle.getCenterY()) or
//            intersectCircle(S, (A, B)) or
//            intersectCircle(S, (B, C)) or
//            intersectCircle(S, (C, D)) or
//            intersectCircle(S, (D, A)))
            return this.boundary.contains(circle.getCenterX(), circle.getCenterY());
        }

    public List<PointParticle> query(Circle circle){
        List<PointParticle> results = new ArrayList<PointParticle>();
        if(!this.intersects(circle)){
        }
        else{
            for (PointParticle point2D : points) {
                if(circle.contains(point2D)){
                    results.add(point2D);
                }
            }

            if(this.divided){
                results.addAll(this.quadtreenw.query(circle));
                results.addAll(this.quadtreene.query(circle));
                results.addAll(this.quadtreesw.query(circle));
                results.addAll(this.quadtreese.query(circle));
            }
        }
        return results;
    }

        public void show(AnchorPane root) {
            Rectangle rectangle = new Rectangle(this.boundary.getMinX(), this.boundary.getMinY(), this.boundary.getWidth(), this.boundary.getHeight());
            rectangle.setStroke(Color.RED);
            rectangle.setFill(Color.TRANSPARENT);
            root.getChildren().add(rectangle);
            for(PointParticle point2D: points){
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

}
