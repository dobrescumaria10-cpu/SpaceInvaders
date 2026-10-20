/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaceinvaders.model;

import java.awt.Graphics2D;
import java.util.Random;
import spaceinvaders.Constants;

/**
 * The whole grid of aliens. It moves them together sideways, makes them step
 * down and reverse when one reaches a window edge, and speeds up as aliens are
 * killed.
 */
public class AlienFormation {

    private static final int ROWS = 5;
    private static final int COLS = 8;
    private static final int GAP = 15;        // space between aliens
    private static final int SPEED = 2;       // how many pixels they move per frame
    private static final int STEP_DOWN = 20;  // how far they step down at the edge

    // array 2d
    private Alien[][] aliens = new Alien[ROWS][COLS];

    private int direction = 1;
    private int boardWidth;
    private int wave;
    private Random random = new Random();

    /**
     * Creates the first wave of aliens.
     *
     * @param boardWidth the width of the playing filed, used to detect the
     * edges
     */
    public AlienFormation(int boardWidth) {
        this(boardWidth, 1);
    }

    /**
     * Creates a grid of aliens for the given wave. The aliens of the rows get
     * 30, 20, 20, 10 and 10 points (from the top). Later waves move faster.
     *
     * @param boardWidth the width of the playing field, used to detect the
     * edges
     * @param wave the wave number, starting at 1
     */
    public AlienFormation(int boardWidth, int wave) {
        this.boardWidth = boardWidth;
        this.wave = wave;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = 50 + col * (Alien.WIDTH + GAP);
                int y = 50 + row * (Alien.HEIGHT + GAP);
                aliens[row][col] = new Alien(x, y, Constants.ROW_POINTS[row]);
            }
        }
    }

    /**
     * @return the grid of aliens (rows x columns), including the dead ones
     */
    public Alien[][] getAliens() {
        return aliens;
    }

    /**
     * @return how many aliens are still alive
     */
    public int countAlive() {
        int count = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (aliens[row][col].isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return true when all aliens are dead (the wave is cleared)
     */
    public boolean isEmpty() {
        return countAlive() == 0;
    }

    /**
     * Picks the alien that shoots next: the lowest alive alien of a random
     * column. Only the lowest alien of a column can shoot, so aliens never fire
     * through their own friends.
     *
     * @return the shooting alien, or null when no alien is alive
     */
    public Alien pickShooter() {
        int start = random.nextInt(COLS);
        for (int i = 0; i < COLS; i++) {
            int col = (start + i) % COLS;
            for (int row = ROWS - 1; row >= 0; row--) {
                if (aliens[row][col].isAlive()) {
                    return aliens[row][col];
                }
            }
        }
        return null;
    }

    /**
     * Finds the lowest point of the formation. When it reaches the cannon, the
     * game is over.
     *
     * @return the y of the bottom edge of the lowest alive alien, or 0 if none
     * is alive
     */
    public int lowestAliveBottom() {
        int bottom = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Alien a = aliens[row][col];
                if (a.isAlive()) {
                    bottom = Math.max(bottom, a.getY() + a.getHeight());
                }

            }
        }
        return bottom;
    }

    /**
     * Calculates the speed in pixels per frame. It rises with the wave and with
     * the number of killed aliens, so the last aliens are the fastest.
     *
     * @return the number of pixels the aliens move in one frame
     */
    private int currentSpeed() {
        int total = ROWS * COLS;
        int killed = total - countAlive();
        return SPEED + (wave - 1) + killed * 4 / total;
    }

    /**
     * Moves the formation one step. The aliens move sideways together. When one
     * alive alien would leave the window, the whole formation steps down and
     * reverses direction instead. Called once per frame.
     */
    public void update() {
        int speed = currentSpeed();

        // 1. Check whether any living alien has reached the edge
        boolean hitEdge = false;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Alien a = aliens[row][col];
                if (a.isAlive()) {
                    int nextX = a.getX() + direction * speed;
                    if (nextX < 0 || nextX + Alien.WIDTH > boardWidth) {
                        hitEdge = true;
                    }
                }
            }

        }
        // 2. If so: reverse direction and move down. Otherwise: move sideways.
        int dx = direction * speed;
        int dy = 0;
        if (hitEdge) {
            direction = -direction;
            dx = 0;
            dy = STEP_DOWN;
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                aliens[row][col].move(dx, dy);
            }
        }
    }

}
