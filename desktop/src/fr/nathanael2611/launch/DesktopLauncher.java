package fr.nathanael2611.launch;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import fr.nathanael2611.colorrunner.ColorRunner;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher
{

    public static int VIEWPORT_WIDTH;
    public static int VIEWPORT_HEIGHT;

    public static void main(String[] args)
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        VIEWPORT_WIDTH = (int) (850);
        VIEWPORT_HEIGHT = (int) (850);
        config.setTitle("Color Run - \"La couleur change tout\"");
        config.setWindowedMode(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        config.setResizable(false);
        config.setWindowIcon("player/2.png");
        new Lwjgl3Application(new ColorRunner(), config);
    }
}
