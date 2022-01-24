package Scripts;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import Scripts.Settings.Spawn;

import java.util.HashMap;

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
    HashMap<Point, Integer> trailMap = new HashMap<>();
    Random rand = new Random();
    Panel() {
        this.setPreferredSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        this.setBackground(Color.BLACK);
        timer = new Timer(10, this);
        timer.start();
        agents = new Agent[Settings.AGENT_COUNT];
        
        if (Settings.SPAWN_MODE == Spawn.RANDOM) {
            for (int c = 0; c < Settings.AGENT_COUNT; c++) {
                agents[c] = new Agent((int)(Math.random() * Settings.WIDTH - Settings.AGENT_SIZE) + Settings.AGENT_SIZE, (int)(Math.random() * Settings.HEIGHT - Settings.AGENT_SIZE) + Settings.AGENT_SIZE, 0);
            }
        }
        
        // agents[0] = new Agent(300, 400, 0);
        // agents[1] = new Agent(400, 280, Math.PI / 2);
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
        for(Point point: trailMap.keySet()) {
            g2D.drawLine(point.x, point.y, point.x, point.y);
            trailMap.put(point, trailMap.get(point) - 1);
        }
        for (Point point: trailMap.keySet()) {
            if (trailMap.get(point) != 0) {continue;}
        }
        trailMap.keySet().removeIf(k -> trailMap.get(k) == 0);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    public void move(Agent agent) {
        check(agent);
        int temp_x = (int)agent.getx();
        int temp_y = (int)agent.gety();
        double new_x = agent.getx() + Math.cos(agent.getang()) * Settings.AGENT_SPEED;
        double new_y = agent.gety() + Math.sin(agent.getang()) * Settings.AGENT_SPEED;
        if (new_x > Settings.WIDTH - (int)(Settings.AGENT_SIZE / 2) || new_x < Settings.AGENT_SIZE / 2 || new_y > Settings.HEIGHT - (int)(Settings.AGENT_SIZE / 2) || new_y < Settings.AGENT_SIZE / 2) {
            new_x = Math.min(Settings.WIDTH - Settings.AGENT_SIZE / 2, Math.max(Settings.AGENT_SIZE / 2, new_x));
            new_y = Math.min(Settings.HEIGHT - Settings.AGENT_SIZE / 2, Math.max(Settings.AGENT_SIZE / 2, new_y));
            agent.setang(rand.nextDouble() * 2 * Math.PI);
        }
        agent.setx(new_x); agent.sety(new_y); 
        Point point = new Point(temp_x, temp_y);
        if (trailMap.get(point) == null) {
            trailMap.put(point, Integer.valueOf(Settings.TRAIL_LENGTH));
        } else {
            trailMap.put(point, Settings.TRAIL_LENGTH);
        }
    }
    public void check(Agent agent) {
        int weight_forward = sense(agent, 0);
        int weight_right = sense(agent, -Settings.SENSOR_OFFSET);
        int weight_left= sense(agent, Settings.SENSOR_OFFSET);
        //double strength = Math.random();
        if (weight_forward > weight_right && weight_forward > weight_left) {
        } else if (weight_forward == weight_right) {
        } else if (weight_right > weight_left) {
            agent.setang(agent.getang() - (rand.nextDouble() * Math.PI / 4));
        } else if (weight_left > weight_right) {
            agent.setang(agent.getang() + (rand.nextDouble() * Math.PI / 4));
        }
    }
    public int sense(Agent agent, double offset) {
        double angle = agent.getang() + offset;
        int center_x = (int)(agent.getx() + Settings.SENSOR_DST + Math.cos(angle));
        int center_y = (int)(agent.gety() + Settings.SENSOR_DST + Math.sin(angle));
        int sum = 0;
        for (int x = -Settings.SENSOR_SIZE; x <= Settings.SENSOR_SIZE; x++) {
            for (int y = -Settings.SENSOR_SIZE; y <= Settings.SENSOR_SIZE; y++) {
                Point pos = new Point(center_x + x, center_y + y);
                if (!trailMap.containsKey(pos)) {continue;}
                sum += (int)trailMap.get(pos);
            }
        }
        return sum;
    }
}