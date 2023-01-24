package fr.nathanael2611.colorrunner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.nathanael2611.colorrunner.level.Level;
import fr.nathanael2611.colorrunner.level.Tile;
import fr.nathanael2611.colorrunner.render.RenderManager;

public class Player
{

    private final SpriteBatch batch;

    public float prevX, prevZ, x, z;

    public Vector2 desiredPos = null, direction = new Vector2();

    Texture[] animationTextures;
    private int animationCount = 0;
    private long lastAnimUpdate = 0;

    public Player()
    {
        this.batch = new SpriteBatch();
        this.animationTextures = new Texture[]{
                        new Texture("player/1.png"), new Texture("player/2.png"),
                        new Texture("player/3.png"), new Texture("player/4.png"),
        };
    }

    public void updateAnimation()
    {
        animationCount++;
        this.animationCount = animationCount >= animationTextures.length ? 0 : this.animationCount;

        Level actualLevel = ColorRunner.INSTANCE.getActualLevel();
        Tile tileUnderPos = actualLevel.getTileUnderPos(this.x, this.z);
        if(tileUnderPos.parent.getAvoidColor() != null)
        {
            if (tileUnderPos.parent.getAvoidColor().getRGB() == tileUnderPos.color.getRGB())
            {
                actualLevel.gameOver();
            }
        }
    }

    public Texture getActualTexture()
    {
        return this.animationTextures[this.animationCount];
    }

    public void update()
    {
        this.prevX = x;
        this.prevZ = z;

        if (desiredPos == null)
            direction = new Vector2();
        else
        {
            Vector2 nor = new Vector2(this.desiredPos).sub(this.getPositionVector()).nor();
            this.direction = nor;
            if (this.getPositionVector().dst(this.desiredPos) > 5)
            {
                this.x += direction.x * 4;
                this.z += direction.y * 4;

                this.x = Math.max(-300, Math.min(x, 300));
                this.z = Math.max(-300, Math.min(z, 300));


                if (System.currentTimeMillis() - lastAnimUpdate > 300)
                {
                    this.lastAnimUpdate = System.currentTimeMillis();
                    updateAnimation();
                }
            }
        }
    }

    public Vector2 getPositionVector()
    {
        return new Vector2(this.x, this.z);
    }

    public float getRenderX(float partialTicks)
    {
        return prevX + (x - prevX) * partialTicks;
    }

    public float getRenderZ(float partialTicks)
    {
        return prevZ + (z - prevZ) * partialTicks;
    }

    public void render(RenderManager renderManager, float partialTicks)
    {
        this.batch.setProjectionMatrix(renderManager.getCamera().combined);
        this.batch.begin();
        this.batch.draw(getActualTexture(), this.getRenderX(partialTicks) - 32, this.getRenderZ(partialTicks) - 32, 64, 64);
        this.batch.end();
    }

}