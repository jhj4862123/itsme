import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Spaceship extends Sprite {
    private int dx;
    private int dy;
    Missile m = null;

    public Spaceship(int x, int y) {
    	super(x-100, y-100);
        loadImage("C:\\Users\\yeong\\IdeaProjects\\Main\\hw-2\\src\\spaceship.png");
        getImageDimensions();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public Missile getMissile() {

        return m;
    }

    public void fire() {
        m = new Missile(x, y);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
        	fire();
        }
        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
