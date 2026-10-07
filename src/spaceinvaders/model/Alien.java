/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaceinvaders.model;

import java.awt.Color;
import java.awt.Graphics2D;

public class Alien extends Entity {

    // static const int 
    public static final int WIDTH = 30;
    public static final int HEIGHT = 20;

    
    private boolean alive = true;

    public Alien(int x, int y) {
        super(x, y, WIDTH, HEIGHT);   // calls the parent (Entity) constructor
    }

    // moves
    public void move(int dx, int dy) {
        x = x + dx;
        y = y + dy;
    }

    // shoots from the middle of the alien (5-velocity) 
    public Bullet shoot() {
        return new Bullet(x + WIDTH / 2, y + HEIGHT, 5);
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    // drawing alien
    public void draw(Graphics2D g) {
        if (alive) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, WIDTH, HEIGHT);
        }
    }
}


