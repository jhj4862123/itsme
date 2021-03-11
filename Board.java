import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener, KeyListener {

	private Timer timer;
	private Spaceship ship;
	private Alien alien;
	private final int DELAY = 10;

	int e_w, e_h;
	int m_w, m_h;
	public Board() {
		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.BLACK);
		alien = new Alien(500,100);
		ship = new Spaceship(500, 500);
		timer = new Timer(DELAY, this);
		timer.start();
		e_w = ImageWidthValue("alien.png");
	}

	private int ImageWidthValue(String file) {
		int x = 0;
		try {
			File f = new File(file);
			BufferedImage bi = ImageIO.read(f);;
			x = bi.getWidth();
		}catch (Exception e){}
		return x;
		}
	public int ImageHeightValue(String file){ // 이미지 높이 크기 값 계산
		int y = 0;
		try{
			File f = new File(file);
			BufferedImage bi = ImageIO.read(f);
			y = bi.getHeight();
		}catch(Exception e){}
		return y;
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
		g2d.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);

		if( ship.getMissile() != null )
			g2d.drawImage(ship.getMissile().getImage(), ship.getMissile().getX(), ship.getMissile().getY(), this);
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ship.move();
		if( ship.getMissile() != null )
			ship.getMissile().move();
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ship.keyReleased(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ship.keyPressed(e);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}