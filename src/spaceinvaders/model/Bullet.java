package spaceinvaders.model;

import spaceinvaders.Constants;

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
