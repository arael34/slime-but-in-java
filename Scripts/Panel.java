package Scripts;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.ArrayList;

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
class Panel extends JPanel implements ActionListener {
    Timer timer;
    Agent[] agents;
    HashMap<Integer[], Integer> trailMap = new HashMap<Integer[], Integer>();
    Panel() {
        this.setPreferredSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        this.setBackground(Color.BLACK);
        timer = new Timer(10, this);
        timer.start();
        agents = new Agent[Settings.AGENT_COUNT];
        for (int c = 0; c < Settings.AGENT_COUNT; c++) {
            agents[c] = new Agent((int)(Math.random() * 400) + 10, (int)(Math.random() * 400) + 10, Math.PI / 4);
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        int x = 0;
        int y = 0;
        g2D.setPaint(Color.BLUE);
        g2D.setStroke(new BasicStroke(Settings.AGENT_SIZE));
        for (Agent ag: agents) {
            x = (int)ag.getx();
            y = (int)ag.gety();
            g2D.drawLine(x, y, x, y);
            move(ag);
        }
        for(Integer[] point: trailMap.keySet()) {
            g2D.drawLine(point[0], point[1], point[0], point[1]);
            trailMap.put(point, trailMap.get(point) - 1);
        }
        ArrayList<Integer[]> temp = new ArrayList<Integer[]>();
        for (Integer[] point: trailMap.keySet()) {
            if (trailMap.get(point) != 0) {continue;}
            temp.add(point);
        }
        trailMap.keySet().removeIf(k -> trailMap.get(k) == 0);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    public void move(Agent agent) {
        //int x = agent.getx();
        //int y = agent.gety();
        double new_x = agent.getx() + Math.cos(agent.getang()) * Settings.AGENT_SPEED;
        double new_y = agent.gety() + Math.sin(agent.getang()) * Settings.AGENT_SPEED;
        double rand = Math.random();
        if (new_x > Settings.WIDTH - (int)(Settings.AGENT_SIZE / 2) || new_x < Settings.AGENT_SIZE / 2 || new_y > Settings.HEIGHT - (int)(Settings.AGENT_SIZE / 2) || new_y < Settings.AGENT_SIZE / 2) {
            new_x = Math.min(Settings.WIDTH - Settings.AGENT_SIZE / 2, Math.max(Settings.AGENT_SIZE / 2, new_x));
            new_y = Math.min(Settings.HEIGHT - Settings.AGENT_SIZE / 2, Math.max(Settings.AGENT_SIZE / 2, new_y));
            agent.setang(rand * 2 * Math.PI);
        }
        agent.setx(new_x); agent.sety(new_y); 
        Integer[] point = {(int)new_x, (int)new_y};
        trailMap.put(point, Integer.valueOf(Settings.TRAIL_LENGTH));
    }
    public void check(Agent agent) {
        int weight_forward = sense(agent, 0);
        int weight_right = sense(agent, 0);
        int weight_left= sense(agent, 0);
        double strength = (double) Math.random();
        if (weight_forward >= weight_right && weight_forward >= weight_left) {
        } else if (weight_right > weight_left) {
            agent.setang(-Math.PI / 6 * strength);
        } else if (weight_left > weight_right) {
            agent.setang(Math.PI / 6 * strength);
        }
    }
    public int sense(Agent agent, double offset) {
        return 0;
    }
}