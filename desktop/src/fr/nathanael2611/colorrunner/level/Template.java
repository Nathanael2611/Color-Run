package fr.nathanael2611.colorrunner.level;

import fr.nathanael2611.colorrunner.util.Helpers;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Template
{

    private int seconds;
    private List<Color> possibleColors;
    private List<Color> possibleNeededColors;
    private List<Color> possibleAvoidColors;

    private Template()
    {
        this.seconds = 10;
        this.possibleColors = new ArrayList<>();
        this.possibleNeededColors = new ArrayList<>();
        this.possibleAvoidColors = new ArrayList<>();
    }

    public Level createLevel()
    {
        Level level = new Level();
        List<Color> composition = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            while (composition.size() < 100)
            {
                for (Color possibleColor : possibleColors)
                {
                    if (composition.size() < 100)
                    {
                        composition.add(possibleColor);
                    }
                }
            }
        }
        Collections.shuffle(composition);
        Queue<Color> colors = new ArrayDeque<>();
        colors.addAll(composition);
        List<Tile> possibleSpawnTiles = new ArrayList<>();
        level.setNeededColor(Helpers.randomFrom(possibleNeededColors));
        List<Color> avoidableColors = new ArrayList<>(possibleAvoidColors);
        removeColorFrom(avoidableColors, level.getNeededColor());
        if(avoidableColors.size() > 0)
        {
            level.setAvoidColor(Helpers.randomFrom(avoidableColors));
        }
        level.secondsRemaining = this.seconds;
        for (int i = 0; i < level.getTiles().length; i++)
        {
            for (int i1 = 0; i1 < level.getTiles()[i].length; i1++)
            {
                Tile tile = new Tile(level, -(level.getTiles().length * 64) / 2 + i * 64, -(level.getTiles()[i].length * 64) / 2 + i1 * 64);
                level.getTiles()[i][i1] = tile;
                tile.color = colors.remove();
                if((level.getNeededColor() == null || tile.color.getRGB() != level.getNeededColor().getRGB()) && (level.getAvoidColor() == null || tile.color.getRGB() != level.getAvoidColor().getRGB() ))
                {
                    possibleSpawnTiles.add(tile);
                }
            }
        }
        level.setSpawnTile(Helpers.randomFrom(possibleSpawnTiles));
        return level;
    }

    private void removeColorFrom(List<Color> avoidableColors, Color neededColor)
    {
        for (int i = 0; i < avoidableColors.size(); i++)
        {
            Color coloor = avoidableColors.get(i);
            if(neededColor.getRGB() == coloor.getRGB())
            {
                avoidableColors.remove(i);
            }
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private Template template = new Template();

        public Builder addColors(CColor... colors)
        {
            for (CColor color : colors)
            {
                this.addColor(color);
            }
            return this;
        }

        public Builder addColors(List<CColor> colors)
        {
            for (CColor color : colors)
            {
                this.addColor(color);
            }
            return this;
        }

        public Builder setSeconds(int seconds)
        {
            this.template.seconds = seconds;
            return this;
        }

        public Builder addColor(CColor color)
        {
            template.possibleColors.add(color);
            if (color.needable)
            {
                template.possibleNeededColors.add(color);
            }
            if(color.avoidable)
            {
                template.possibleAvoidColors.add(color);
            }
            return this;
        }

        public Template build()
        {
            return template;
        }
    }

}
