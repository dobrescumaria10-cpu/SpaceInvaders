package spaceinvaders.model;

import spaceinvaders.Constants;

/**
 * The cannon controlled by the player. It moves horizontally at a fixed height
 * and cannot leave the window.
 */
public class Player extends Entity {

    private long invulnerableUntil = 0;

    /**
     * Creates the cannon in the middle of the window, at the fixed height.
     */
    public Player() {
        super((Constants.WIDTH - Constants.PLAYER_WIDTH) / 2, Constants.PLAYER_Y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }

    /**
     * Moves the cannon one step to the left and/or right and keeps it inside
     * the window.
     *
     * @param leftHeld true while left key is held down
     * @param rightHeld true while right key is held down
     */
    public void update(boolean leftHeld, boolean rightHeld) {
        if (leftHeld) {
            x -= Constants.PLAYER_SPEED;
        }
        if (rightHeld) {
            x += Constants.PLAYER_SPEED;
        }
        x = Math.max(0, Math.min(Constants.WIDTH - width, x));
    }

    /**
     * Puts the cannon back in the middle of the window (after losing a life).
     */
    public void respawn() {
        x = (Constants.WIDTH - Constants.PLAYER_WIDTH) / 2;
    }

    /**
     * Makes the cannon immune to hits for a while
     *
     * @param untilMs the time (System.currentTimeMillis) when the protection
     * ends
     */
    public void makeInvulnerable(long untilMs) {
        invulnerableUntil = untilMs;
    }

    /**
     * @param now the current time in milliseconds
     * @return true while the cannon cannot be hit
     */
    public boolean isInvulnerable(long now) {
        return now < invulnerableUntil;
    }
}
