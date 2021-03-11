import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Missile extends Sprite {

	private final int MISSILE_SPEED = 2;
	int dx = 1, dy = 1;

	public Missile(int x, int y) {
		super(x-5 , y-5);
			loadImage("C:\\Users\\yeong\\IdeaProjects\\Main\\hw-2\\src\\missile.png");

		}


	public void move() {
		y -= MISSILE_SPEED;
		if (y < 0) {
			visible = false;
		}
	}
}