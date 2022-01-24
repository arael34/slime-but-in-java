package Scripts;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
    HashMap<Point, Integer> trailMap;
    Panel() {
        this.setPreferredSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        this.setBackground(Settings.BACKGROUND);
        timer = new Timer((int)(1000 / Settings.FPS) + 1, this);
        timer.start();
        agents = new Agent[Settings.AGENT_COUNT];
        trailMap = new HashMap<>();
        if (Settings.SPAWN_MODE == Spawn.RANDOM) {
            for (int c = 0; c < Settings.AGENT_COUNT; c++) {
                agents[c] = new Agent((Math.random() * Settings.WIDTH - Settings.AGENT_SIZE) + Settings.AGENT_SIZE, (Math.random() * Settings.HEIGHT - Settings.AGENT_SIZE) + Settings.AGENT_SIZE, Math.random() * 2 * Math.PI);
            }
        } else if (Settings.SPAWN_MODE == Spawn.CIRCLE_IN) {
            double angle;
            double radius;
            for (int c = 0; c < Settings.AGENT_COUNT; c++) {
                angle = Math.random() * 2 * Math.PI;
                radius = Math.random() * (Math.min(Settings.WIDTH, Settings.HEIGHT) / 2 - Settings.AGENT_SIZE);
                agents[c] = new Agent(Math.cos(angle) * radius + Settings.WIDTH / 2, Math.sin(angle) * radius + Settings.HEIGHT / 2, angle + Math.PI);
            }
        } else if (Settings.SPAWN_MODE == Spawn.CIRCLE_OUT) {
            double angle;
            double radius;
            for (int c = 0; c < Settings.AGENT_COUNT; c++) {
                angle = Math.random() * 2 * Math.PI;
                radius = Math.random() * (Math.min(Settings.WIDTH, Settings.HEIGHT) / 2 - Settings.AGENT_SIZE);
                agents[c] = new Agent(Math.cos(angle) * radius + Settings.WIDTH / 2, Math.sin(angle) * radius + Settings.HEIGHT / 2, angle);
            }
        } else if (Settings.SPAWN_MODE == Spawn.DONUT) {
            double angle;
            double radius;
            int min;
            for (int c = 0; c < Settings.AGENT_COUNT; c++) {
                angle = Math.random() * 2 * Math.PI;
                min = Math.min(Settings.WIDTH, Settings.HEIGHT);
                radius = Math.random() * (min / 2 - Settings.AGENT_SIZE - min / 4) + min / 4;
                agents[c] = new Agent(Math.cos(angle) * radius + Settings.WIDTH / 2, Math.sin(angle) * radius + Settings.HEIGHT / 2, angle + Math.PI);
            }
        } else if (Settings.SPAWN_MODE == Spawn.WATERFALL) {
            for (int c = 0; c < Settings.AGENT_COUNT; c++) {
                agents[c] = new Agent((Math.random() * Settings.WIDTH - Settings.AGENT_SIZE) + Settings.AGENT_SIZE, (Math.random() * Settings.HEIGHT - Settings.AGENT_SIZE) + Settings.AGENT_SIZE, Math.PI / 2);
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        int x = 0;
        int y = 0;
        g2D.setPaint(Settings.AGENT_COLOR);
        g2D.setStroke(new BasicStroke(Settings.AGENT_SIZE));
        for (Agent ag: agents) {
            x = (int)ag.getx();
            y = (int)ag.gety();
            g2D.drawLine(x, y, x, y);
            move(ag);
        }
        AlphaComposite alpha;
        for(Point point: trailMap.keySet()) {
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)trailMap.get(point) / (Settings.TRAIL_LENGTH + Settings.TRAIL_FADE));
            g2D.setComposite(alpha);
            g2D.drawLine(point.x, point.y, point.x, point.y);
            trailMap.put(point, trailMap.get(point) - 1);
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
            agent.setang(Math.random() * 2 * Math.PI);
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
        if (weight_forward > weight_right && weight_forward > weight_left) {
        } else if (weight_forward == weight_right) {
        } else if (weight_right > weight_left) {
            agent.setang(agent.getang() - (Math.random() * Settings.TURN_SPEED));
        } else if (weight_left > weight_right) {
            agent.setang(agent.getang() + (Math.random() * Settings.TURN_SPEED));
        }
    }
    public int sense(Agent agent, double offset) {
        double angle = agent.getang() + offset;
        int center_x = (int)(agent.getx() + Settings.SENSOR_DST * Math.cos(angle));
        int center_y = (int)(agent.gety() + Settings.SENSOR_DST * Math.sin(angle));
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