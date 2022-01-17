package com.example.quadtreegraphical;


import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Quadtree {
    Rectangle2D boundary;
    double mass = 0;
    Point2D center_of_mass  ;
    boolean divided = false;
    int capacity; // capacity
    PointParticle pointParticle;
    Quadtree quadtreenw;
    Quadtree quadtreene;
    Quadtree quadtreesw;
    Quadtree quadtreese;

    Quadtree(Rectangle2D boundary, int n) {
        this.boundary = boundary;
        this.capacity = n;
        this.pointParticle =null;
        this.center_of_mass =null;
    }

    double getTotalMass() {

        if (mass != 0)
            return mass;

        if (!divided)
            return pointParticle.getParticle().getMass();

        double m = 0;
        if (quadtreenw.pointParticle != null) m += quadtreenw.getTotalMass();
        if (quadtreene.pointParticle != null) m += quadtreene.getTotalMass();
        if (quadtreesw.pointParticle != null) m += quadtreesw.getTotalMass();
        if (quadtreese.pointParticle != null) m += quadtreese.getTotalMass();

        mass = m;
        return m;
    }

    public Point2D getCenterOfMass() {

        if (center_of_mass != null)
            return center_of_mass;

        if (!divided) {
            return new Point2D(pointParticle.getParticle().getCenterX(), pointParticle.getParticle().getCenterY());
        }

        Point2D nwCenter, neCenter, swCenter, seCenter;
        double nwMass, neMass, swMass, seMass;

        float x = 0, y = 0;
        float totalMass = 0;

        if (quadtreenw.pointParticle != null) {

            nwCenter = quadtreenw.getCenterOfMass();
            nwMass = quadtreenw.getTotalMass();

            x += nwCenter.getX() * nwMass;
            y += nwCenter.getY() * nwMass;
            totalMass += nwMass;
        }
        if (quadtreene.pointParticle != null){

            neCenter = quadtreene.getCenterOfMass();
            neMass = quadtreene.getTotalMass();

            x += neCenter.getX() * neMass;
            y += neCenter.getY() * neMass;
            totalMass += neMass;
        }
        if (quadtreesw.pointParticle != null) {

            swCenter = quadtreesw.getCenterOfMass();
            swMass = quadtreesw.getTotalMass();

            x += swCenter.getX() * swMass;
            y += swCenter.getY() * swMass;
            totalMass += swMass;
        }
        if (quadtreese.pointParticle != null) {

            seCenter = quadtreese.getCenterOfMass();
            seMass = quadtreese.getTotalMass();

            x += seCenter.getX() * seMass;
            y += seCenter.getY() * seMass;
            totalMass += seMass;
        }

        x /= totalMass;
        y /= totalMass;

        center_of_mass = new Point2D(x, y);
        return center_of_mass;
    }

    public void insert(PointParticle point) {

//            System.out.println(this.boundary);
        if (  this.boundary.contains(point)){
            if (this.pointParticle == null ) {
                this.pointParticle = point;
            } else {

                if (!divided) {

                    this.subdivide();

                    if(!point.equals(pointParticle)){
                        this.quadtreese.insert(pointParticle);
                        this.quadtreenw.insert(pointParticle);
                        this.quadtreesw.insert(pointParticle);
                        this.quadtreene.insert(pointParticle);
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



//            this.quadtreenw = new Quadtree(new Rectangle2D(x - (w / 2), y - (y / 2), w / 2, h / 2), this.capacity); // if we took coordinstr as  100 100 it would be inacurate, draw it
//            this.quadtreene = new Quadtree(new Rectangle2D(x + (w / 2), y - (y / 2), w / 2, h / 2), this.capacity);
//            this.quadtreesw = new Quadtree(new Rectangle2D(x - (w / 2), y + (y / 2), w / 2, h / 2), this.capacity);
//            this.quadtreese = new Quadtree(new Rectangle2D(x + (w / 2), y + (y / 2), w / 2, h / 2), this.capacity);
        this.divided = true;

    }
//
//    public List<PointParticle> query(Rectangle2D queryRectangle2D){
//        List<PointParticle> results = new ArrayList<PointParticle>();
//        if(!this.boundary.intersects(queryRectangle2D)){
//        }
//        else{
//            for (PointParticle point2D : points) {
//                if(queryRectangle2D.contains(point2D)){
//                    results.add(point2D);
//                }
//            }
//
//            if(this.divided){
//                results.addAll(this.quadtreenw.query(queryRectangle2D));
//                results.addAll(this.quadtreene.query(queryRectangle2D));
//                results.addAll(this.quadtreesw.query(queryRectangle2D));
//                results.addAll(this.quadtreese.query(queryRectangle2D));
//            }
//        }
//        return results;
//    }

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

//    public List<PointParticle> query(Circle circle){
//        List<PointParticle> results = new ArrayList<PointParticle>();
//        if(!this.intersects(circle)){
//        }
//        else{
//            for (PointParticle point2D : points) {
//                if(circle.contains(point2D)){
//                    results.add(point2D);
//                }
//            }
//
//            if(this.divided){
//                results.addAll(this.quadtreenw.query(circle));
//                results.addAll(this.quadtreene.query(circle));
//                results.addAll(this.quadtreesw.query(circle));
//                results.addAll(this.quadtreese.query(circle));
//            }
//        }
//        return results;
//    }

    public void show(AnchorPane root) {
        Rectangle rectangle = new Rectangle(this.boundary.getMinX(), this.boundary.getMinY(), this.boundary.getWidth(), this.boundary.getHeight());
        rectangle.setStroke(Color.RED);
        rectangle.setFill(Color.TRANSPARENT);
        root.getChildren().add(rectangle);
        if(pointParticle != null){
            Circle circle = new Circle();
            circle.setCenterX(pointParticle.getX());
            circle.setCenterY(pointParticle.getY());
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

    public void updateForce(PointParticle pointParticleParam) {

        if (!divided && !pointParticleParam.equals(pointParticle)) {

            if(pointParticle != null)
            pointParticleParam.getParticle().addForce(pointParticle.getParticle());
        }
        else {

            double distanceFromCenterOfMass = pointParticleParam.distance(getCenterOfMass());
            double t = this.boundary.getHeight() / distanceFromCenterOfMass; // length

            if (t < .5) {
                pointParticleParam.getParticle().addForce(getCenterOfMass(), getTotalMass());
            }
            else {

                if (this.quadtreenw != null) this.quadtreenw.updateForce(pointParticleParam);
                if (this.quadtreesw != null) this.quadtreesw.updateForce(pointParticleParam);
                if (this.quadtreese != null) this.quadtreese.updateForce(pointParticleParam);
                if (this.quadtreene != null) this.quadtreene.updateForce(pointParticleParam);
            }
        }
    }

}