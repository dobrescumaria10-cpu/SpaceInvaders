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
            frame.setIconImage(createIcon());
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

    private static java.awt.Image createIcon() {
        int[][] pixels = {
            {0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0},
            {0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}
        };
        java.awt.Color[] colors = {
            null, 
            new java.awt.Color(30, 160, 60), 
            new java.awt.Color(255, 220, 60), 
            new java.awt.Color(120, 210, 255) 
        };
        int scale = 2;                              
        int size = 16 * scale;
        int top = (16 - pixels.length) / 2 * scale; 
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
                size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = img.createGraphics();
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {
                int c = pixels[row][col];
                if (c != 0) {
                    g.setColor(colors[c]);
                    g.fillRect(col * scale, top + row * scale, scale, scale);
                }
            }
        }
        g.dispose();
        return img;
    }
}
