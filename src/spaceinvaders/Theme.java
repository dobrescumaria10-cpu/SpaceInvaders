package spaceinvaders;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * A look for the game: the background, the colors and the pixel-art pictures of
 * the cannon and the aliens. A picture is a list of text rows: '#' is a pixel
 * in the main color, 'w' is a white pixel and '.' is empty. The player chooses
 * one of the themes in {@link #ALL} in the start menu. A theme only changes how
 * the game looks, not how it plays.
 */
public class Theme {

    private static final int STARS = 0;
    private static final int BUBBLES = 1;
    private static final int GRID = 2;
    private static final int HAUNTED = 3;   // HALLOWEEN: moon, bats and graveyard
    private static final int ARCADE = 4;    // ARCADE: old-school arcade screen

    public static final Theme[] ALL = {space(), ocean(), neon(), halloween(), arcade()};

    private final String name;
    private final int style;
    private final Color top, bottom;
    private final Color playerColor;
    private final Color[] alienColors;
    private final Color bulletColor, alienBulletColor;
    private final String[] playerSprite;
    private final String[][] alienSprites;

    private final int[] dotX = new int[70];
    private final int[] dotY = new int[70];
    private final int[] dotSize = new int[70];

    /**
     * Creates a theme. The constructor is private: the themes are created by
     * the methods at the end of this class.
     *
     * @param name the name shown in the menu
     * @param style the background style (STARTS, BUBBLES, ...)
     * @param top the color at the top of the background gradient
     * @param bottom the color at the bottom of the background gradient
     * @param playerColor the main color of the cannon
     * @param alienColors the main colors of the aliens worth 30, 20 and 10
     * points
     * @param bulletColor the color of the player's bullets
     * @param alienBulletColor the color of the alien bullets
     * @param playerSprite the picture of the cannon (text rows)
     * @param alienSprites the picture of the three alien types (text rows)
     */
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

    /**
     * @return the name of the theme, shown in the menu
     */
    public String getName() {
        return name;
    }

    /**
     * @return the main color of the cannon
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * @return the color of the player's bullets
     */
    public Color getBulletColor() {
        return bulletColor;
    }

    /**
     * @return the color of the alien bullets
     */
    public Color getAlienBulletColor() {
        return alienBulletColor;
    }

    /**
     * Translates the points of an alien into the number of its picture.
     *
     * @param points the points of the alien (30, 20 or 10)
     * @return 0 for 30 points, 1 for 20 points, 2 for 10 points
     */
    private int alienIndex(int points) {
        if (points >= 30) {
            return 0;
        } else if (points >= 20) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Draws the cannon picture, scaled into the given rectangle.
     *
     * @param g the graphics object to draw on
     * @param x left edge of the rectangle
     * @param y top edge of the rectangle
     * @param w width of the rectangle
     * @param h height of the rectangle
     */
    public void drawPlayer(Graphics g, int x, int y, int w, int h) {
        drawSprite(g, playerSprite, x, y, w, h, playerColor);
    }

    /**
     * Draws the picture of an alien, scaled into the given rectangle. The
     * points of the alien decide which picture and color is used.
     *
     * @param g the graphics object to draw on
     * @param points the points of the alien (30, 20 or 10)
     * @param x left edge of the rectangle
     * @param y top edge of the rectangle
     * @param w width of the rectangle
     * @param h height of the rectangle
     */
    public void drawAlien(Graphics g, int points, int x, int y, int w, int h) {
        int i = alienIndex(points);
        drawSprite(g, alienSprites[i], x, y, w, h, alienColors[i]);
    }

    /**
     * Draws a pixel-art picture scaled into a rectangle. Every pixel of the
     * picture becomes one filled rectangle, so the same picture works at any
     * size.
     *
     * @param g the graphics object to draw on
     * @param sprite the text rows of the picture: '#' main color, 'w' white,
     * '.' empty
     * @param x left edge of the rectangle
     * @param y top edge of the rectangle
     * @param w width of the rectangle
     * @param h height of the rectangle
     * @param color the color of the '#' pixels
     */
    private static void drawSprite(Graphics g, String[] sprite, int x, int y, int w, int h, Color color) {
        int rows = sprite.length;
        int cols = sprite[0].length();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = sprite[r].charAt(c);
                if (ch != '.') {
                    g.setColor(ch == 'w' ? Color.WHITE : color);
                    int x1 = x + c * w / cols;
                    int x2 = x + (c + 1) * w / cols;
                    int y1 = y + r * h / rows;
                    int y2 = y + (r + 1) * h / rows;
                    g.fillRect(x1, y1, x2 - x1, y2 - y1);
                }
            }
        }
    }

    /**
     * Draws the background of this theme: a gradient and, depending on the
     * style, moving decorations. The movement is calculated from time, so the
     * theme needs no extra state.
     *
     * @param g the graphics object to draw on
     * @param now the current time in milliseconds
     */
    public void drawBackground(Graphics2D g, long now) {
        g.setPaint(new GradientPaint(0, 0, top, 0, Constants.HEIGHT, bottom));
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        if (style == STARS) {
            g.setColor(new Color(255, 255, 255, 190));
            for (int i = 0; i < dotX.length; i++) {
                int y = (int) ((dotY[i] + now / (25 + dotSize[i] * 15)) % Constants.HEIGHT);
                g.fillRect(dotX[i], y, dotSize[i], dotSize[i]);
            }
        } else if (style == BUBBLES) {
            g.setColor(new Color(180, 235, 255, 120));
            for (int i = 0; i < dotX.length; i++) {
                int size = dotSize[i] * 5;
                int shift = (int) ((now / (12 + dotSize[i] * 6)) % Constants.HEIGHT);
                int y = ((dotY[i] - shift) % Constants.HEIGHT + Constants.HEIGHT) % Constants.HEIGHT;
                g.drawOval(dotX[i], y, size, size);
            }
        } else if (style == HAUNTED) {
            drawHauntedBackground(g, now);
        } else if (style == ARCADE) {
            drawArcadeBackground(g, now);
        } else {
            drawGridBackground(g, now);
        }
    }

    /**
     * Draws the NEON background: starts, a sun on the horizon and a moving
     * grid.
     */
    private void drawGridBackground(Graphics2D g, long now) {
        int horizon = 380;

        g.setColor(new Color(255, 255, 255, 170));
        for (int i = 0; i < dotX.length; i++) {
            g.fillRect(dotX[i], dotY[i] % horizon, 1 + dotSize[i] / 2, 1 + dotSize[i] / 2);
        }

        g.setColor(new Color(255, 110, 150, 150));
        g.fillOval(Constants.WIDTH / 2 - 90, horizon - 110, 180, 180);

        g.setColor(new Color(20, 0, 40));
        g.fillRect(0, horizon, Constants.WIDTH, Constants.HEIGHT - horizon);

        g.setColor(new Color(255, 60, 200, 110));
        double shift = (now / 50 % 40) / 40.0;
        for (int i = 0; i < 12; i++) {
            double p = (i + shift) / 12.0;
            int y = horizon + (int) (p * p * (Constants.HEIGHT - horizon));
            g.drawLine(0, y, Constants.WIDTH, y);
        }
        for (int i = -10; i <= 10; i++) {
            g.drawLine(Constants.WIDTH / 2 + i * 8, horizon, Constants.WIDTH / 2 + i * 90, Constants.HEIGHT);
        }
        g.drawLine(0, horizon, Constants.WIDTH, horizon);
    }

    /**
     * Draws the HALLOWEEN background: a moon, bats and a graveyard.
     */
    private void drawHauntedBackground(Graphics2D g, long now) {
        // stars that blink: each star is on or off depending on the time
        for (int i = 0; i < dotX.length; i++) {
            if ((now / 400 + i) % 5 != 0) {
                g.setColor(new Color(255, 240, 200, 160));
                g.fillRect(dotX[i], dotY[i] % 400, dotSize[i], dotSize[i]);
            }
        }

        // the moon: a soft glow, the full moon, then a few darker craters
        int moonX = Constants.WIDTH - 200;
        int moonY = 50;
        g.setColor(new Color(255, 200, 120, 40));
        g.fillOval(moonX - 25, moonY - 25, 170, 170);
        g.setColor(new Color(255, 235, 180));
        g.fillOval(moonX, moonY, 120, 120);
        g.setColor(new Color(230, 205, 150));
        g.fillOval(moonX + 25, moonY + 30, 22, 22);
        g.setColor(new Color(230, 205, 150));
        g.fillOval(moonX + 70, moonY + 60, 30, 30);
        g.fillOval(moonX + 45, moonY + 85, 14, 14);

        // bats: they fly from left to right and flap their wings
        String[] wingsUp = {
            "#........#",
            "##..##..##",
            ".########.",
            "...#..#..."
        };
        String[] wingsDown = {
            "..........",
            "....##....",
            ".########.",
            "##.#..#.##"
        };
        Color batColor = new Color(110, 50, 140);
        for (int i = 0; i < 7; i++) {
            int speed = 12 + i * 4;                          // smaller number = faster bat
            int x = (int) ((dotX[i] + now / speed) % (Constants.WIDTH + 60)) - 30;
            int y = 60 + dotY[i] % 220 + (int) (10 * Math.sin(now / 300.0 + i));
            boolean flap = (now / 150 + i) % 2 == 0;
            drawSprite(g, flap ? wingsUp : wingsDown, x, y, 30, 12, batColor);
        }

        // graveyard: dark ground with tombstones
        int ground = Constants.HEIGHT - 45;
        g.setColor(new Color(15, 5, 20));
        g.fillRect(0, ground, Constants.WIDTH, Constants.HEIGHT - ground);
        g.setColor(new Color(40, 30, 50));
        for (int i = 0; i < 8; i++) {
            int tx = 30 + i * (Constants.WIDTH / 8);
            g.fillRoundRect(tx, ground - 28, 26, 40, 14, 14);
        }
    }

    /**
     * Draws the ARCADE background: an old-school arcade screen
     */
    private void drawArcadeBackground(Graphics2D g, long now) {
        // a few dim stars
        g.setColor(new Color(255, 255, 255, 70));
        for (int i = 0; i < 25; i++) {
            g.fillRect(dotX[i], dotY[i], 1, 1);
        }

        // colored film strips, like on the real arcade cabinet
        g.setColor(new Color(255, 0, 60, 45));
        g.fillRect(0, 40, Constants.WIDTH, 70);
        g.setColor(new Color(0, 255, 80, 35));
        g.fillRect(0, Constants.HEIGHT - 150, Constants.WIDTH, 150);

        // CRT scanlines: a thin light line every 4 pixels
        g.setColor(new Color(255, 255, 255, 12));
        for (int y = 0; y < Constants.HEIGHT; y += 4) {
            g.drawLine(0, y, Constants.WIDTH, y);
        }

        // green ground line at the bottom
        g.setColor(new Color(60, 255, 90));
        g.fillRect(0, Constants.HEIGHT - 22, Constants.WIDTH, 2);

        // "INSERT COIN" blinks once per second
        if ((now / 500) % 2 == 0) {
            g.setColor(new Color(255, 230, 60));
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
            String text = "INSERT COIN";
            int textWidth = g.getFontMetrics().stringWidth(text);
            g.drawString(text, (Constants.WIDTH - textWidth) / 2, Constants.HEIGHT - 5);
        }
    }

    private static Theme space() {
        String[] player = {
            ".........##.........",
            "........####........",
            "........#ww#........",
            ".......######.......",
            "..#...########...#..",
            ".###..########..###.",
            "####.##########.####",
            "#..#............#..#"
        };
        String[][] aliens = {
            {// alien head with big eyes (30 points)
                "...######...",
                "..########..",
                ".##########.",
                ".##ww##ww##.",
                ".##ww##ww##.",
                ".##########.",
                "..#.####.#..",
                ".#..#..#..#."
            },
            {// flying saucer (20 points)
                "....####....",
                "...######...",
                "..########..",
                "############",
                "#w##w##w##w#",
                "############",
                "..##....##..",
                ".#........#."

            },
            {// bug (10 points)
                "#..........#",
                ".#........#.",
                "..########..",
                ".##########.",
                ".#w##..##w#.",
                ".##########.",
                "..#.#..#.#..",
                ".#........#."

            }
        };
        return new Theme("SPACE", STARS, new Color(5, 5, 25), new Color(25, 10, 60), new Color(80, 255, 120),
                new Color[]{new Color(255, 80, 220), new Color(80, 230, 255), new Color(255, 230, 80)},
                Color.WHITE, new Color(255, 70, 70), player, aliens);
    }

    private static Theme ocean() {
        String[] player = {
            "..........#.........",
            "..........####......",
            ".....##########.....",
            "..################..",
            ".#w##w##w##w#######.",
            "####################",
            ".##################.",
            "...##..........##..."
        };
        String[][] aliens = {
            { // jellyfish (30 points)
                "...######...",
                "..########..",
                ".##########.",
                ".#w##..##w#.",
                ".##########.",
                ".#.#.##.#.#.",
                ".#.#..#..#.#",
                "..#.#..#.#.."
            },
            { // fish (20 points)
                "....####....",
                "..########.#",
                ".####w####.#",
                "############",
                ".##########.",
                "..########.#",
                "....####..#.",
                "............"
            },
            { // crab (10 points)
                "##........##",
                "#..######..#",
                "#.########.#",
                "..##w##w##..",
                "..########..",
                "...######...",
                "..#.#..#.#..",
                ".#.#....#.#."
            }
        };
        return new Theme("OCEAN", BUBBLES, new Color(0, 95, 145), new Color(0, 15, 55),
                new Color(255, 170, 40),
                new Color[]{new Color(255, 120, 190), new Color(120, 235, 235), new Color(255, 90, 70)},
                new Color(255, 255, 190), new Color(255, 80, 80), player, aliens);
    }

    private static Theme neon() {
        String[] player = {
            "........####........",
            ".......######.......",
            "......###ww###......",
            ".....##########.....",
            "....############....",
            "..################..",
            ".######.####.######.",
            "#....##########....#"
        };
        String[][] aliens = {
            { // diamond (30 points)
                ".....##.....",
                "....####....",
                "...######...",
                "..##w##w##..",
                "..########..",
                "...######...",
                "....####....",
                ".....##....."
            },
            { // robot (20 points)
                "....####....",
                "....#ww#....",
                "############",
                "############",
                ".##########.",
                "....####....",
                "...##..##...",
                "..##....##.."
            },
            { // ring (10 points)
                "...######...",
                "..########..",
                ".###....###.",
                "###..ww..###",
                "###..ww..###",
                ".###....###.",
                "..########..",
                "...######..."
            }
        };
        return new Theme("NEON", GRID, new Color(20, 0, 45), new Color(70, 0, 80),
                new Color(0, 255, 255),
                new Color[]{new Color(255, 60, 200), new Color(255, 200, 50), new Color(120, 255, 120)},
                Color.WHITE, new Color(255, 80, 80), player, aliens);
    }

    // HALLOWEEN theme: a witch's cauldron fights pumpkins, ghosts and skulls.
    // Eyes and mouths are '.' (empty), so the dark background shows through them.
    private static Theme halloween() {
        String[] player = { // bubbling cauldron
            "....#....#....#.....",
            ".....#..#.#..#......",
            "..################..",
            "...##############...",
            "..##w####ww####w##..",
            "..################..",
            "...##############...",
            "....##........##...."
        };
        String[][] aliens = {
            { // jack-o'-lantern (30 points)
                ".....##.....",
                "..########..",
                ".##########.",
                "##..####..##",
                "#####..#####",
                "##.#.##.#.##",
                ".###.##.###.",
                "..########.."
            },
            { // ghost (20 points)
                "...######...",
                "..########..",
                ".##########.",
                ".##..##..##.",
                ".##..##..##.",
                ".####..####.",
                ".##########.",
                ".#.##..##.#."
            },
            { // skull (10 points)
                "..########..",
                ".##########.",
                "##..####..##",
                "##..####..##",
                "#####..#####",
                ".##########.",
                "..#.#..#.#..",
                "..########.."
            }
        };
        return new Theme("HALLOWEEN", HAUNTED, new Color(30, 0, 45), new Color(5, 0, 10),
                new Color(120, 255, 80),
                new Color[]{new Color(255, 140, 20), new Color(225, 225, 255), new Color(235, 230, 205)},
                new Color(255, 160, 40), new Color(170, 255, 60), player, aliens);
    }

    // ARCADE theme: the classic laser cannon against the three original invaders
    private static Theme arcade() {
        String[] player = { // laser cannon
            ".........##.........",
            "........####........",
            "........####........",
            "..################..",
            ".##################.",
            "####################",
            "####################",
            "####################"
        };
        String[][] aliens = {
            { // squid (30 points)
                ".....##.....",
                "....####....",
                "...######...",
                "..##.##.##..",
                "..########..",
                "....#..#....",
                "...#.##.#...",
                "..#.#..#.#.."
            },
            { // crab (20 points)
                "..#......#..",
                "...#....#...",
                "..########..",
                ".##.####.##.",
                "############",
                "#.########.#",
                "#.#......#.#",
                "...##..##..."
            },
            { // octopus (10 points)
                "....####....",
                ".##########.",
                "############",
                "###..##..###",
                "############",
                "...##..##...",
                "..##.##.##..",
                "##........##"
            }
        };
        return new Theme("ARCADE", ARCADE, Color.BLACK, Color.BLACK,
                new Color(60, 255, 90),
                new Color[]{new Color(255, 90, 120), Color.WHITE, new Color(150, 255, 150)},
                Color.WHITE, new Color(255, 255, 120), player, aliens);
    }

}
