package spaceinvaders;

/**
 * Central space for all fixed values of the game (window size, speeds, sizes,
 * alien layout). Having them here means a value is changed in one spot only.
 */
public final class Constants {

    private Constants() {
    }

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    public static final int FRAME_DELAY_MS = 1000 / FPS;

    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 20;
    public static final int PLAYER_SPEED = 5;
    public static final int PLAYER_Y = HEIGHT - 60;

    public static final int BULLET_WIDTH = 4;
    public static final int BULLET_HEIGHT = 12;
    public static final int BULLET_SPEED = 8;
    public static final int SHOOT_COOLDOWN_MS = 400;

    public static final int ALIEN_ROWS = 5;
    public static final int ALIEN_COLS = 11;
    public static final int ALIEN_WIDTH = 36;
    public static final int ALIEN_HEIGHT = 24;
    public static final int ALIEN_H_GAP = 14;
    public static final int ALIEN_V_GAP = 14;
    public static final int FORMATION_START_X = 60;
    public static final int FORMATION_START_Y = 70;
    public static final int ALIEN_STEP_DOWN = 16;
    public static final int[] ROW_POINTS = {30, 20, 20, 10, 10};

}
