package com.example.quadtreegraphical;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static double randomBetween(double start, double end) {
        return  ThreadLocalRandom.current().nextDouble(start, end);
    }
}
