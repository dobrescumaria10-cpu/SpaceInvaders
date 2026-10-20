package spaceinvaders.model;

/**
 * Holds the game data that does not belong to a single object: score, lives and
 * wave number.
 */
public class GameModel {

    private int score;
    private int lives;
    private int wave;
    private boolean gameOver;

    /**
     * Creates a model for a new game.
     */
    public GameModel() {
        reset();
    }

    /**
     * Resets the score, the lives, the wave and the game over flag.
     */
    public void reset() {
        score = 0;
        lives = 3;
        wave = 1;
        gameOver = false;
    }

    /**
     * @param points the points to add to the score
     */
    public void addScore(int points) {
        score += points;
    }

    /**
     * Takes one life. The game is over when no lives are left.
     */
    public void loseLife() {
        lives--;
        if (lives <= 0) {
            lives = 0;
            gameOver = true;
        }
    }

    /**
     * Ends the game immediately (used when the aliens reach the cannon).
     */
    public void endGame() {
        gameOver = true;
    }

    /**
     * Moves on to the next, harder wave.
     */
    public void nextWave() {
        wave++;
    }

    /**
     * @return true when the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the number of lives left
     */
    public int getLives() {
        return lives;
    }

    /**
     * @return the current wave number, starting at 1
     */
    public int getWave() {
        return wave;
    }
}
