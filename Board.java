import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Board extends JPanel
    implements ActionListener {

    private final int B_WIDTH = 1100;
    private final int B_HEIGHT = 700;
    private final int INITIAL_X = 0;
    private final int INITIAL_Y = 660;
    private final int DELAY = 25;

    private Image rocket;
    private Timer timer;
    private int x;
    private int y;
    Random rd = new Random();

    public Board() {
        initBoard();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("C:\\Users\\yeong\\IdeaProjects\\Main\\hw-1\\rocket1.jpg");
        rocket = ii.getImage();
    }

    private void initBoard() {
        setBackground(Color.blue);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRocket(g);
    }

    private void drawRocket(Graphics g) {
        g.drawImage(rocket, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    public void actionPerformed(ActionEvent e) {
        int xs = (rd.nextInt(30));
        int ys = (rd.nextInt(30));
            x+=xs;
            y-=ys;
            if (y < 10)
                ys = (-ys);
            if (y > 650)
                ys = (+ys);
            if (x < 0)
                xs = (+xs);
            if (x > 1000)
                xs = (-xs);
        repaint();
    }
    }

