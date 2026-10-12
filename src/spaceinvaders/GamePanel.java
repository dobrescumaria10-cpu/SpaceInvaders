package spaceinvaders;

import spaceinvaders.model.Bullet;
import spaceinvaders.model.Player;
import spaceinvaders.model.Alien;
import spaceinvaders.model.AlienFormation;
import spaceinvaders.model.GameModel;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The game screen. It runs the game loop with a Swing timer, read the keyboard,
 * updates all game objects every frame and draws them.
 */
public class GamePanel extends JPanel {

    private final Timer timer;
    private final Player player = new Player();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Bullet> alienBullets = new ArrayList<>();
    private AlienFormation formation = new AlienFormation(Constants.WIDTH);
    private final GameModel model = new GameModel();

    private boolean leftHeld, rightHeld, spaceHeld;
    private long lastShotTime = 0;
    private long lastAlienShotTime = 0;
    private long waveMessageUntil = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                setKey(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                setKey(e.getKeyCode(), false);
            }
        });

        timer = new Timer(Constants.FRAME_DELAY_MS, e -> {
            update();
            repaint();
        });

    }

    public void start() {
        timer.start();
    }

    private void setKey(int code, boolean down) {
        if (code == KeyEvent.VK_LEFT) {
            leftHeld = down;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightHeld = down;
        }
        if (code == KeyEvent.VK_SPACE) {
            spaceHeld = down;
        }
        if (code == KeyEvent.VK_ENTER && down && model.isGameOver()) {
            restart();
        }

    }

    private void restart() {
        model.reset();
        formation = new AlienFormation(Constants.WIDTH);
        bullets.clear();
        alienBullets.clear();
        player.respawn();
        player.makeInvulnerable(0);
        waveMessageUntil = 0;
    }

    private void update() {

        long now = System.currentTimeMillis();

        if (model.isGameOver() || now < waveMessageUntil) {
            return;
        }

        player.update(leftHeld, rightHeld);

        if (spaceHeld && now - lastShotTime >= Constants.SHOOT_COOLDOWN_MS) {
            int bx = player.getX() + player.getWidth() / 2 - Constants.BULLET_WIDTH / 2;
            bullets.add(new Bullet(bx, player.getY()));
            lastShotTime = now;
        }

        for (Bullet b : bullets) {
            b.update();
        }
        bullets.removeIf(Bullet::isOffScreen);

        formation.update();

        if (formation.lowestAliveBottom() >= player.getY()) {
            model.endGame();
            return;
        }

        Alien[][] grid = formation.getAliens();
        List<Bullet> deadBullets = new ArrayList<>();
        for (Bullet b : bullets) {
            boolean hit = false;
            for (int row = 0; row < grid.length && !hit; row++) {
                for (int col = 0; col < grid[row].length && !hit; col++) {
                    Alien a = grid[row][col];
                    if (a.isAlive() && b.getBounds().intersects(a.getBounds())) {
                        a.kill();
                        model.addScore(a.getPoints());
                        deadBullets.add(b);
                        hit = true;
                    }
                }
            }
        }
        bullets.removeAll(deadBullets);

        if (alienBullets.size() < Constants.MAX_ALIEN_BULLETS && now - lastAlienShotTime >= Constants.ALIEN_SHOOT_INTERVAL_MS) {
            Alien shooter = formation.pickShooter();
            if (shooter != null) {
                int bx = shooter.getX() + shooter.getWidth() / 2 - Constants.BULLET_WIDTH / 2;
                alienBullets.add(new Bullet(bx, shooter.getY() + shooter.getHeight(), Constants.ALIEN_BULLET_SPEED));
                lastAlienShotTime = now;
            }
        }

        for (Bullet b : alienBullets) {
            b.update();
        }
        alienBullets.removeIf(Bullet::isOffScreen);

        if (!player.isInvulnerable(now)) {
            Bullet hitBullet = null;
            for (Bullet b : alienBullets) {
                if (b.getBounds().intersects(player.getBounds())) {
                    hitBullet = b;
                }
            }

            if (hitBullet != null) {
                alienBullets.remove(hitBullet);
                model.loseLife();
                if (!model.isGameOver()) {
                    player.respawn();
                    player.makeInvulnerable(now + Constants.INVULNERABLE_MS);
                }
            }

        }

        if (formation.isEmpty()) {
            model.nextWave();
            formation = new AlienFormation(Constants.WIDTH, model.getWave());
            bullets.clear();
            alienBullets.clear();
            waveMessageUntil = now + Constants.WAVE_MESSAGE_MS;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long now = System.currentTimeMillis();

        boolean showPlayer = !player.isInvulnerable(now) || (now / 100) % 2 == 0;

        if (showPlayer) {
            g.setColor(Color.GREEN);
            g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        }

        g.setColor(Color.WHITE);
        for (Bullet b : bullets) {
            g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }

        g.setColor(Color.RED);
        for (Bullet b : alienBullets) {
            g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }

        Alien[][] grid = formation.getAliens();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Alien a = grid[row][col];
                if (a.isAlive()) {
                    g.setColor(a.getPoints() == 30 ? Color.MAGENTA : a.getPoints() == 20 ? Color.CYAN : Color.YELLOW);
                    g.fillRect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                }
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 18));
        g.drawString("SCORE: " + model.getScore(), 10, 25);
        g.drawString("WAVE: " + model.getWave(), Constants.WIDTH / 2 - 40, 25);
        g.drawString("LIVES: " + model.getLives(), Constants.WIDTH - 120, 25);

        if (now < waveMessageUntil) {
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            drawCentered(g, "WAVE" + model.getWave(), Constants.HEIGHT / 2);
        }

        if (model.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 48));
            drawCentered(g, "GAME OVER", Constants.HEIGHT / 2 - 20);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 20));
            drawCentered(g, "Final score: " + model.getScore(), Constants.HEIGHT / 2 + 20);
            drawCentered(g, "Press ENTER to play again", Constants.HEIGHT / 2 + 50);
        }

    }
    
    private void drawCentered(Graphics g, String text, int y) {
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (Constants.WIDTH - textWidth) / 2, y);
    }
}
