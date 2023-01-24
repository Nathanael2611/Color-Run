package fr.nathanael2611.colorrunner.level;

import fr.nathanael2611.colorrunner.ColorRunner;
import fr.nathanael2611.colorrunner.Player;
import fr.nathanael2611.colorrunner.render.RenderManager;

import java.awt.*;

public class Level
{

    public int totalSeconds = -1;
    public int secondsRemaining = 10;
    private long lastTick = System.currentTimeMillis();
    private Tile[][] tiles = new Tile[10][10];
    private Color neededColor = null;
    private Color avoidColor = null;
    private boolean gameOver;
    private Tile spawnTile;

    public Level()
    {
    }

    public void setSpawnTile(Tile spawnTile)
    {
        this.spawnTile = spawnTile;
    }

    public Tile getSpawnTile()
    {
        return spawnTile;
    }

    public Tile[][] getTiles()
    {
        return tiles;
    }

    public void tick()
    {
        if (isGameOver()) return;
        if (this.totalSeconds == -1) this.totalSeconds = this.secondsRemaining;
        if (System.currentTimeMillis() - this.lastTick > (1000))
        {
            this.lastTick = System.currentTimeMillis();
            this.secondsRemaining--;
        }
        if (this.secondsRemaining < 0)
        {
            Player player = ColorRunner.INSTANCE.getPlayer();
            Tile underTile = getTileUnderPos(player.x, player.z);
            if (underTile.color.getRGB() == this.neededColor.getRGB())
            {
                ColorRunner.INSTANCE.score();
                ColorRunner.INSTANCE.createLevel();
            } else
            {
                this.gameOver();
            }
        }
    }

    public Color getNeededColor()
    {
        return neededColor;
    }

    public void setNeededColor(Color neededColor)
    {
        this.neededColor = neededColor;
    }

    public void setAvoidColor(Color avoidColor)
    {
        this.avoidColor = avoidColor;
    }

    public Color getAvoidColor()
    {
        return avoidColor;
    }


    public void gameOver()
    {
        this.gameOver = true;
        ColorRunner.waterSound.play(1);
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void render(RenderManager renderManager, float partialTicks)
    {
        for (int i = 0; i < this.getTiles().length; i++)
        {
            for (int i1 = 0; i1 < this.getTiles().length; i1++)
            {
                this.getTiles()[i][i1].render(renderManager, partialTicks);
            }
        }
    }

    public Tile getTileUnderPos(float x, float z)
    {
        for (int i = 0; i < this.tiles.length; i++)
        {
            for (int i1 = 0; i1 < this.tiles[i].length; i1++)
            {
                Tile tile = this.tiles[i][i1];
                if (tile != null)
                {
                    if (tile.box.containsPoint(x, z))
                    {
                        return tile;
                    }
                }
            }
        }
        return null;
    }

}
