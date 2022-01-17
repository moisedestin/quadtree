package com.example.quadtreegraphical;

import javafx.geometry.Point2D;

public class PointParticle extends Point2D {
    public Particle getParticle() {
        return particle;
    }

    private Particle particle;

    public PointParticle(double v, double v1) {
        super(v, v1);
    }
    public PointParticle(double v, double v1, Particle particle) {
        super(v, v1);
        this.particle = particle;
    }
}