import java.awt.Image;
import java.util.*;

import javax.swing.ImageIcon;
public class Alien extends Sprite {

    Alien al;
    Random rd = new Random();
    int xspeed = rd.nextInt(2) - 2;
    int yspeed = rd.nextInt(1) - 1;
    int dx = 1, dy = 1;

    public Alien(int x, int y) {
        super(x, y);
        for (int i = 0; i < 10; i++) {
            loadImage("C:\\Users\\yeong\\IdeaProjects\\Main\\hw-2\\src\\alien.png");
            AlienList.add(i);
            setVisible(true);

            x += xspeed;
            y += yspeed;
        }
    }
}

