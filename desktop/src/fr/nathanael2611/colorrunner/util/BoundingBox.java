package fr.nathanael2611.colorrunner.util;

public class BoundingBox
{

    public float minX, minZ, maxX, maxZ;

    public BoundingBox(float minX, float minZ, float maxX, float maxZ)
    {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public boolean containsPoint(float x, float z)
    {
        return x >= this.minX && x <= this.maxX
                && z >= this.minZ && z <= this.maxZ;
    }
}
