package fr.nathanael2611.colorrunner.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.nathanael2611.colorrunner.render.RenderManager;
import fr.nathanael2611.colorrunner.util.BoundingBox;
import fr.nathanael2611.colorrunner.util.Helpers;

import java.awt.*;

public class Tile
{

    public static final Texture TEXTURE = new Texture("tile.png");

    private final SpriteBatch spriteBatch;

    public final Level parent;
    public final float x, z;
    public final BoundingBox box;

    public Color color = Color.RED;
    public float width = 64, height = 64;

    public Tile(Level parent, float x, float z)
    {
        this.parent = parent;
        this.spriteBatch = new SpriteBatch();
        this.x = x;
        this.z = z;
        this.box = new BoundingBox(x, z, x + this.width, this.z + this.height);
    }

    public void render(RenderManager renderManager, float partialTicks)
    {
        this.spriteBatch.setProjectionMatrix(renderManager.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.setColor(Helpers.toColor(this.color));
        spriteBatch.draw(TEXTURE, x, z, width, height);
        spriteBatch.end();
    }

}
