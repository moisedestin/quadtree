package com.example.quadtreegraphical;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static double randomBetween(double start, double end) {
        return  ThreadLocalRandom.current().nextDouble(start, end);
    }
    public static Color randomFromList(List<Color> list)
    {
        return list.get(new Random().nextInt(list.size()));
    }
}
