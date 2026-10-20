package spaceinvaders.model;

import java.awt.Rectangle;

/**
 * Base class of everything that is drawn in the game and has a position and a
 * size (player, aliens, bullets). Provides the bounding box used for collision
 * detection.
 */
public abstract class Entity {

    protected int x, y;
    protected final int width, height;

    /**
     * Creates an entity.
     *
     * @param x left edge in pixels
     * @param y top edge in pixels
     * @param width width in pixels
     * @param height height in pixels
     */
    protected Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the left edge in pixels
     */
    public int getX() {
        return x;
    }

    /**
     * @return the top edge in pixels
     */
    public int getY() {
        return y;
    }

    /**
     * @return the width in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height in pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gives the rectangle around the entity, used to detect collisions.
     *
     * @return a new rectangle with the position and size of this entity
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
