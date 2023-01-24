package fr.nathanael2611.colorrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class PlayerController implements InputProcessor
{

    private final Player player;
    private final float zoom = 1.25f;

    public float getZoom()
    {
        return zoom;
    }

    public PlayerController(Player player)
    {
        this.player = player;
    }

    public void update()
    {
        if(ColorRunner.INSTANCE.getActualLevel() != null)
        {
            if(Gdx.input.isButtonPressed(0))
            {
                float x = Gdx.input.getX() - Gdx.graphics.getWidth() / 2;
                float y = Gdx.input.getY() - Gdx.graphics.getHeight() / 2;

                x *= zoom;
                y *= zoom;
                this.player.desiredPos = new Vector2(x, -y);
            }
            else
            {
                this.player.desiredPos = this.player.getPositionVector();
                if(Gdx.input.isKeyPressed(Input.Keys.UP))
                {
                    this.player.desiredPos.add(0, 64);
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
                {
                    this.player.desiredPos.add(0, -64);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                {
                    this.player.desiredPos.add(64, 0);
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
                {
                    this.player.desiredPos.add(-64, 0);
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if(ColorRunner.INSTANCE.getActualLevel() == null)
        {
            if(keycode == (Input.Keys.UP))
            {
                ColorRunner.INSTANCE.decreaseDifficulty();
                ColorRunner.buttonSound.play(1);
            }

            if(keycode == (Input.Keys.DOWN))
            {
                ColorRunner.INSTANCE.increaseDifficulty();
                ColorRunner.buttonSound.play(1);
            }

            if(keycode == Input.Keys.ENTER)
            {
                ColorRunner.INSTANCE.createLevel();
                ColorRunner.buttonSound.play(1);
            }
        }
        else
        {
            if(ColorRunner.INSTANCE.getActualLevel().isGameOver())
            {
                if (keycode == Input.Keys.ENTER)
                {
                    ColorRunner.INSTANCE.killLevel();
                }
            }
            else
            {
                if(keycode == Input.Keys.ENTER)
                {
                    ColorRunner.INSTANCE.getActualLevel().secondsRemaining = 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return false;
    }
}
