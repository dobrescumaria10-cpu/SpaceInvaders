package spaceinvaders.model;

import spaceinvaders.Constants;
/**
 * A bullet fired by the player. It moves straight up until it hits an alien
 * or leaves the window.
 */
public class Bullet extends Entity{
    
    public Bullet(int x, int y){
        super(x, y, Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
    }
    
    public void update() {
        y -= Constants.BULLET_SPEED;
    }
    
    public boolean isOffScreen() {
        return y + height < 0;
    }
    
}
