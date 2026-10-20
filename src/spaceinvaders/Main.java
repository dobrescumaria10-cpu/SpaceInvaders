package spaceinvaders;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point of the game. Creates the window and starts the game loop.
 */
public class Main {

    /**
     * Starts the game: creates the window with the game panel and starts the
     * game loop.
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Space Invaders");
            GamePanel panel = new GamePanel();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            panel.requestFocusInWindow();
            panel.start();
        });
    }
}
