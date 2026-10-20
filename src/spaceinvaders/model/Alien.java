/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaceinvaders.model;

/**
 * A single alien. It can move, be killed and has a point value that the gets
 * when it is shot.
 */
public class Alien extends Entity {

    // static const int 
    public static final int WIDTH = 30;
    public static final int HEIGHT = 20;

    private boolean alive = true;
    private int points;
/**
 * Creates a living alien.
 * @param x left edge in pixels
 * @param y top edge in pixels
 * @param points  the points the player gets for shooting it
 */
    public Alien(int x, int y, int points) {
        super(x, y, WIDTH, HEIGHT);   // calls the parent (Entity) constructor
        this.points = points;
    }

/**
 * Moves the alien.
 * @param dx pixels to the right (negative = left)
 * @param dy pixels down
 */
    public void move(int dx, int dy) {
        x = x + dx;
        y = y + dy;
    }

/**
 * @return the points the player gets for shooting this alien 
 */
    public int getPoints() {
        return points;
    }
/**
 * @return true while the alien is alive 
 */
    public boolean isAlive() {
        return alive;
    }
/**
 * Kills the alien. It is no longer drawn and cannot be hit.
 */
    public void kill() {
        alive = false;
    }
}
