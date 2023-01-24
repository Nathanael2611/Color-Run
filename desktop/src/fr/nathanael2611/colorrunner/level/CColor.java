package fr.nathanael2611.colorrunner.level;

import java.awt.*;
import java.awt.color.ColorSpace;

public class CColor extends Color
{

    public boolean needable = true, avoidable = true;

    public CColor(String hex)
    {
        this(Color.decode(hex).getRGB());
    }
    public CColor(int r, int g, int b)
    {
        super(r, g, b);
    }

    public CColor(int r, int g, int b, int a)
    {
        super(r, g, b, a);
    }

    public CColor(int rgb)
    {
        super(rgb);
    }

    public CColor(int rgba, boolean hasalpha)
    {
        super(rgba, hasalpha);
    }

    public CColor(float r, float g, float b)
    {
        super(r, g, b);
    }

    public CColor(float r, float g, float b, float a)
    {
        super(r, g, b, a);
    }

    public CColor(ColorSpace cspace, float[] components, float alpha)
    {
        super(cspace, components, alpha);
    }

    public CColor avoidable(boolean bool)
    {
        CColor color = new CColor(this.getRGB());
        color.needable = this.needable;
        color.avoidable = bool;
        return color;
    }
    public CColor needable(boolean bool)
    {
        CColor color = new CColor(this.getRGB());
        color.avoidable = this.avoidable;
        color.needable = bool;
        return color;
    }

}
