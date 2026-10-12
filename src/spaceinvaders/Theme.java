package spaceinvaders;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Theme {

    private static final int STARTS = 0;
    private static final int BUBBLES = 1;
    private static final int GRID = 2;

    public static final Theme[] ALL = {space(), ocean(), neon()};

    private final String name;
    private final int style;
    private final Color top, bottom;
    private final Color playerColor;
    private final Color[] alienColors;
    private final Color bulletColor, alienBulletColor;
    private final String[] playerSprite;
    private final String[] alienSprites;

    private final int[] dotX = new int[70];
    private final int[] dotY = new int[70];
    private final int[] dotSize = new int[70];

    private Theme(String name, int style, Color top, Color bottom, Color playerColor,
            Color[] alienColors, Color bulletColor, Color alienBulletColor,
            String[] playerSprite, String[][] alienSprites) {
        this.name = name;
        this.style = style;
        this.top = top;
        this.bottom = bottom;
        this.playerColor = playerColor;
        this.alienColors = alienColors;
        this.bulletColor = bulletColor;
        this.alienBulletColor = alienBulletColor;
        this.playerSprite = playerSprite;
        this.alienSprites = alienSprites;

        Random random = new Random(7);
        for (int i = 0; i < dotX.length; i++) {
            dotX[i] = random.nextInt(Constants.WIDTH);
            dotY[i] = random.nextInt(Constants.HEIGHT);
            dotSize[i] = 1 + random.nextInt(3);
        }
    }

    public String getName() {
        return name;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public Color getBulletColor() {
        return bulletColor;
    }

    public Color getAlienBulletColor() {
        return alienBulletColor;
    }
}
