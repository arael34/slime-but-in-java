package Scripts;
import javax.swing.*;

class Screen extends JFrame {
    Panel panel;
    Screen() {
        panel = new Panel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

