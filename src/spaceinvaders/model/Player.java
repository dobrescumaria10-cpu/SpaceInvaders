package spaceinvaders.model;

import spaceinvaders.Constants;

/**
 * The cannon controlled by the player. It moves horizontally at a fixed height
 * and cannot leave the window.
 */
public class Player extends Entity {

    private long invulnerableUntil = 0;
    
    public Player() {
        super((Constants.WIDTH - Constants.PLAYER_WIDTH) / 2, Constants.PLAYER_Y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }

    public void update(boolean leftHeld, boolean rightHeld) {
        if (leftHeld) {
            x -= Constants.PLAYER_SPEED;
        }
        if (rightHeld) {
            x += Constants.PLAYER_SPEED;
        }
        x = Math.max(0, Math.min(Constants.WIDTH - width, x));
    }
    
    public void respawn(){
        x = (Constants.WIDTH - Constants.PLAYER_WIDTH) / 2;
    }
    
    public void makeInvulnerable(long untilMs) {
        invulnerableUntil = untilMs;
    }
    
    public boolean isInvulnerable(long now) {
        return now < invulnerableUntil;
    }
}
