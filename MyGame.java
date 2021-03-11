import java.awt.EventQueue;
import javax.swing.JFrame;

public class MyGame extends JFrame {

    public MyGame() {
        initUI();
    }
    
    private void initUI() {
        add(new Board());
        setSize(1000, 600);
        setTitle("Shooting");
        setVisible(true);                
    }

    public static void main(String[] args) {
            	MyGame ex = new MyGame();
    }
}