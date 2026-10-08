package spaceinvaders.model;

import java.awt.Rectangle;
/**
 * Base class of everything that is drawn in the game and has a position and
 * a size (player, aliens, bullets). Provides the bounding box used for 
 * collision detection.
 */
public abstract class Entity {

    protected int x, y;
    protected final int width, height;

    protected Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
