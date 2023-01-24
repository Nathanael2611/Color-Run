package fr.nathanael2611.colorrunner.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.nathanael2611.colorrunner.ColorRunner;
import fr.nathanael2611.colorrunner.level.Level;
import fr.nathanael2611.colorrunner.level.Tile;
import fr.nathanael2611.colorrunner.util.Difficulty;
import fr.nathanael2611.colorrunner.util.Helpers;

public class RenderManager
{

    private OrthographicCamera camera;
    private final SpriteBatch bgBatch = new SpriteBatch();
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final Texture gameGui = new Texture("gamegui.png");
    private final Texture gameOver = new Texture("gameover.png");
    private final Texture bg = new Texture("backround.png");
    private final Texture check = new Texture("check.png");
    private final Texture cross = new Texture("cross.png");
    private final Texture menu = new Texture("menu.png");
    private final Texture selector = new Texture("selector.png");

    public RenderManager()
    {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(0, 0, 0);
        this.camera.update();
    }

    public OrthographicCamera getCamera()
    {
        return camera;
    }

    public void render(float partialTicks)
    {
        this.camera.zoom = ColorRunner.INSTANCE.processor.getZoom();
        this.camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bgBatch.begin();
        bgBatch.draw(bg, 0, 0);
        bgBatch.end();

        batch.begin();
        batch.setProjectionMatrix(this.camera.combined);
        Level actualLevel = ColorRunner.INSTANCE.getActualLevel();
        if (actualLevel != null)
        {
            if (actualLevel.isGameOver())
            {
                batch.draw(gameOver, -(89 * 4), -(89 * 4), 178 * 4, 178 * 4);
                font.draw(batch, "Cliquez sur Entrée pour continuer", -84 * 4, -90 * 4);
            } else
            {
                batch.draw(gameGui, -(89 * 4), -89 * 4, 178 * 4, 200 * 4);
                font.getData().setScale(3.2f);
                font.draw(batch, actualLevel.secondsRemaining + "s", 48 * 4, 98 * 4);
                font.draw(batch, ColorRunner.INSTANCE.getScore() + "", -65 * 4, 98 * 4);
                batch.setColor(Helpers.toColor(actualLevel.getNeededColor()));
                batch.draw(Tile.TEXTURE, -74, 87 * 4, 55, 55);
                batch.setColor(Color.WHITE);
                batch.draw(check, -74, 87 * 4, 55, 55);
                if(actualLevel.getAvoidColor() != null)
                {
                    batch.setColor(Helpers.toColor(actualLevel.getAvoidColor()));
                }
                else
                {
                    batch.setColor(Color.BLACK);
                }
                batch.draw(Tile.TEXTURE, 4, 87 * 4, 55, 55);
                batch.setColor(Color.WHITE);
                batch.draw(cross, 4, 87 * 4, 55, 55);
                font.draw(batch, "Maintenez entrée pour accélérer le temps", -100 * 4, -90 * 4);
            }
        } else
        {
            batch.draw(menu, -(89 * 4), -(89 * 4), 178 * 4, 178 * 4);
            Difficulty difficulty = ColorRunner.INSTANCE.getDifficulty();
            float x = -89 * 4, y;
            switch (difficulty)
            {
                case EASY:
                    y = -11 * 4;
                    break;
                case MEDIUM:
                    y = -50 * 4;
                    break;
                case HARD:
                    y = -89 * 4;
                    break;
                default:
                    y = 0;
            }
            batch.draw(selector, x, y, 178 * 4, 34 * 4);
            font.getData().setScale(3);
            font.draw(batch, "Cliquez sur Entrée pour lancer le jeu", -84 * 4, -90 * 4);
        }

        batch.end();

        if (ColorRunner.INSTANCE.getActualLevel() != null && !ColorRunner.INSTANCE.getActualLevel().isGameOver())
        {
            ColorRunner.INSTANCE.getActualLevel().render(this, partialTicks);
            ColorRunner.INSTANCE.getPlayer().render(this, partialTicks);
        }
    }
}