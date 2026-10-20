package spaceinvaders.model;

import spaceinvaders.Constants;

/**
 * A bullet. Player bullets move up and alien bullets move down: the direction
 * comes from the sign of the speed. It is removed when it hits something or
 * leaves the window.
 */
public class Bullet extends Entity {

    /**
     * Pixels moved per frame: negative = up, positive = down.
     */
    private final int speed;

    /**
     * Creates a player bullet that moves up.
     *
     * @param x left edge in pixels
     * @param y top edge in pixels
     */
    public Bullet(int x, int y) {
        this(x, y, -Constants.BULLET_SPEED);
    }

    /**
     * Creates a bullet with its own speed( used for the alien bullets)
     *
     * @param x left edge in pixels
     * @param y top edge in pixels
     * @param speed pixels per frame (negative = up, positive = down)
     */
    public Bullet(int x, int y, int speed) {
        super(x, y, Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        this.speed = speed;
    }

    /**
     * Moves the bullet one step in its direction.
     */
    public void update() {
        y += speed;
    }

    /**
     * Checks whether the bullet has left the window(top or bottom), so it can
     * be removed.
     *
     * @return true if the bullet is outside the visible area
     */
    public boolean isOffScreen() {
        return y + height < 0 || y > Constants.HEIGHT;
    }

}
