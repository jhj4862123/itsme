import javax.swing.JFrame;
public class test extends JFrame{
    public test() {
        add(new Board());
        setSize(1200,800);
        setTitle("Application");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        test ex = new test();
    }
}
