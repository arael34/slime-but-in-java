package Scripts;
import javax.swing.*;

/*
todo

make imagery
create trails
create sensing function
*/

class Main {
    public static void main(String[] args) {
        new Screen();
        System.out.print("All good\n");
    }
}
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