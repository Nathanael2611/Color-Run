package fr.nathanael2611.colorrunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import fr.nathanael2611.colorrunner.level.Level;
import fr.nathanael2611.colorrunner.level.Templates;
import fr.nathanael2611.colorrunner.render.RenderManager;
import fr.nathanael2611.colorrunner.util.Difficulty;
import fr.nathanael2611.colorrunner.util.Timer;

public class ColorRunner extends ApplicationAdapter
{

    public static ColorRunner INSTANCE;

    private Timer timer;
    private GameLoop gameLoop;
    private RenderManager renderManager;
    private Player player;
    private Level actualLevel;
    public PlayerController processor;

    public Music music;
    public static Sound coinSound;
    public static Sound buttonSound;
    public static Sound waterSound;

    private int score = 0;
    private Difficulty chosenDifficulty = Difficulty.EASY;

    public ColorRunner()
    {
        INSTANCE = this;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Level getActualLevel()
    {
        return actualLevel;
    }

    public Timer getTimer()
    {
        return timer;
    }

    @Override
    public void create()
    {
        super.create();
        this.timer = new Timer(20);
        this.gameLoop = new GameLoop(this.timer);
        this.renderManager = new RenderManager();

        this.player = new Player();
        this.processor = new PlayerController(this.player);
        Gdx.input.setInputProcessor(this.processor);

        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
        this.music.setLooping(true);
        this.music.play();

        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin.ogg"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button.ogg"));
        waterSound = Gdx.audio.newSound(Gdx.files.internal("water.ogg"));

        Templates.loadLevelTemplates();
    }

    @Override
    public void render()
    {
        this.update();
        super.render();
        this.renderManager.render(this.timer.renderPartialTicks);
    }

    private void update()
    {
        this.timer.updateTimer();
        this.processor.update();
        this.gameLoop.updateLoop();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    public Difficulty getDifficulty()
    {
        return this.chosenDifficulty;
    }

    public void increaseDifficulty()
    {
        this.chosenDifficulty = this.chosenDifficulty.increase();
    }

    public void decreaseDifficulty()
    {
        this.chosenDifficulty = this.chosenDifficulty.decrease();
    }

    public void score()
    {
        this.score++;
        coinSound.play(1);
    }

    public int getScore()
    {
        return score;
    }

    public void createLevel()
    {
        this.actualLevel = Templates.getLevelFromDifficulty(this.chosenDifficulty).createLevel();
        this.player.x = this.actualLevel.getSpawnTile().x + 32;
        this.player.z = this.actualLevel.getSpawnTile().z + 32;
    }

    public void killLevel()
    {
        this.actualLevel = null;
        this.score = 0;
    }

}