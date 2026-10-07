package spaceinvaders.model;

import spaceinvaders.Constants;

public class Player extends Entity {

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
}
