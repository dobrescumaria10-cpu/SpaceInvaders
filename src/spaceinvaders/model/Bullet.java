package spaceinvaders.model;

import spaceinvaders.Constants;

/**
 * A bullet fired by the player. It moves straight up until it hits an alien or
 * leaves the window.
 */
public class Bullet extends Entity {

    private final int speed;

    public Bullet(int x, int y) {
        this(x, y, -Constants.BULLET_SPEED);
    }

    public Bullet(int x, int y, int speed) {
        super(x, y, Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public boolean isOffScreen() {
        return y + height < 0 || y > Constants.HEIGHT;
    }

}
