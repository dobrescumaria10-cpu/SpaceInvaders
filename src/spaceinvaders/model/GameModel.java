package spaceinvaders.model;

/**
 * Holds the game data that does not belong to a single object: score, lives and
 * wave number.
 */
public class GameModel {

    private int score;
    private int lives;
    private int wave;

    public GameModel() {
        reset();
    }

    public void reset() {
        score = 0;
        lives = 3;
        wave = 1;
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getWave() {
        return wave;
    }
}
