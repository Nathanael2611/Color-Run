package fr.nathanael2611.colorrunner;

import fr.nathanael2611.colorrunner.util.Timer;

public class GameLoop
{

    private final Timer timer;

    public GameLoop(Timer timer)
    {
        this.timer = timer;
    }

    public void updateLoop()
    {
        for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j)
        {
            this.update();
        }
    }

    private void update()
    {
        ColorRunner.INSTANCE.getPlayer().update();
        if (ColorRunner.INSTANCE.getActualLevel() != null)
        {
            ColorRunner.INSTANCE.getActualLevel().tick();
        }
    }

}