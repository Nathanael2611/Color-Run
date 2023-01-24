package fr.nathanael2611.colorrunner.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import fr.nathanael2611.colorrunner.util.Difficulty;
import fr.nathanael2611.colorrunner.util.Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Templates
{

    public static HashMap<Difficulty, List<Template>> TEMPLATES = new HashMap<>();

    public static void loadLevelTemplates()
    {
        for (Difficulty value : Difficulty.values())
        {
            String fileName = value.name() + ".png";
            FileHandle handle = Gdx.files.internal("templates/" + fileName);
            Pixmap pixmap = new Pixmap(handle);
            loadTemplate(pixmap, value);
        }
    }

    public static void loadTemplate(Pixmap pixmap, Difficulty difficulty)
    {
        TEMPLATES.put(difficulty, new ArrayList<>());
        if (pixmap.getWidth() == 100 && pixmap.getHeight() == 150)
        {
            for (int i = 0; i < 50; i++)
            {
                int y = i * 3;
                List<CColor> colors = retrieveCColorsFromLine(pixmap, y);
                if (!colors.isEmpty())
                {
                    Template template = Template.builder().addColors(colors).setSeconds(difficulty.getSeconds()).build();
                    TEMPLATES.get(difficulty).add(template);
                }
            }
        }
    }

    public static List<CColor> retrieveCColorsFromLine(Pixmap pixmap, int y)
    {
        List<CColor> colors = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            int pixelColor = pixmap.getPixel(i, y);
            if (pixelColor != 0)
            {
                Color c = new Color(pixelColor);

                CColor color = new CColor(c.r, c.g, c.b);
                if (pixmap.getPixel(i, y + 1) != 0)
                {
                    color = color.needable(true);
                } else color = color.needable(false);

                if (pixmap.getPixel(i, y + 2) != 0)
                {
                    color = color.avoidable(true);
                } else color = color.avoidable(false);
                colors.add(color);
            }
        }
        return colors;
    }

    public static Template getLevelFromDifficulty(Difficulty difficulty)
    {
        return Helpers.randomFrom(TEMPLATES.get(difficulty));
    }

}
