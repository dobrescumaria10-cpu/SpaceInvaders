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
    
    public AlienFormation(int boardWidth) {
        this(boardWidth, 1);
    }

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

    public Alien[][] getAliens() {
        return aliens;
    }

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

    public boolean isEmpty() {
        return countAlive() == 0;
    }
    
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

    private int currentSpeed() {
        int total = ROWS * COLS;
        int killed = total - countAlive();
        return SPEED + (wave - 1) + killed * 4 / total;
    }

    // UPDATE - called every frame from GameModel; moves the formation.
    // 1. Goes through all living aliens and checks whether any of them would
    //    leave the screen on the next step (nextX < 0 or nextX + WIDTH > boardWidth).
    // 2. If yes: reverses direction (direction = -direction) and the whole formation moves down 20 pixels.
    // 3. If no: they all move sideways by SPEED pixels (right or left).
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
