package fr.nathanael2611.colorrunner.util;

import com.badlogic.gdx.graphics.Color;

import java.util.List;
import java.util.Random;

public class Helpers
{

    public static <T> T randomFrom(T[] array)
    {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static <T> T randomFrom(List<T> array)
    {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }

    public static Color toColor(java.awt.Color color)
    {
        return new Color(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, color.getAlpha()/255F);
    }

}
