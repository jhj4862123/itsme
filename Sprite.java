import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Sprite {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible;
	protected Image image;
	ArrayList MissileList = new ArrayList(8);
	ArrayList AlienList = new ArrayList();

	Missile ms;
	Alien al;
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}


	protected void loadImage(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
	}

	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}