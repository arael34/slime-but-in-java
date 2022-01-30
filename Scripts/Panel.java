package Scripts;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Scripts.Main.Spawn;
import java.util.HashMap;

class Panel extends JPanel implements ActionListener {
    Timer timer;
    Agent[] agents;
    HashMap<Point, Float> trailMap;
    Panel() {
        this.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        this.setBackground(Main.BACKGROUND);
        timer = new Timer((int)(1000 / Main.FPS) + 1, this);
        timer.start();
        agents = new Agent[Main.AGENT_COUNT];
        trailMap = new HashMap<>();
        if (Main.SPAWN_MODE == Spawn.RANDOM) {
            for (int c = 0; c < Main.AGENT_COUNT; c++) {
                agents[c] = new Agent((Math.random() * Main.WIDTH - Main.AGENT_SIZE) + Main.AGENT_SIZE, (Math.random() * Main.HEIGHT - Main.AGENT_SIZE) + Main.AGENT_SIZE, Math.random() * 2 * Math.PI);
            }
        } else if (Main.SPAWN_MODE == Spawn.CIRCLE_IN) {
            double angle;
            double radius;
            for (int c = 0; c < Main.AGENT_COUNT; c++) {
                angle = Math.random() * 2 * Math.PI;
                radius = Math.random() * (Math.min(Main.WIDTH, Main.HEIGHT) / 2 - Main.AGENT_SIZE);
                agents[c] = new Agent(Math.cos(angle) * radius + Main.WIDTH / 2, Math.sin(angle) * radius + Main.HEIGHT / 2, angle + Math.PI);
            }
        } else if (Main.SPAWN_MODE == Spawn.CIRCLE_OUT) {
            double angle;
            double radius;
            for (int c = 0; c < Main.AGENT_COUNT; c++) {
                angle = Math.random() * 2 * Math.PI;
                radius = Math.random() * (Math.min(Main.WIDTH, Main.HEIGHT) / 2 - Main.AGENT_SIZE);
                agents[c] = new Agent(Math.cos(angle) * radius + Main.WIDTH / 2, Math.sin(angle) * radius + Main.HEIGHT / 2, angle);
            }
        } else if (Main.SPAWN_MODE == Spawn.DONUT) {
            double angle;
            double radius;
            int min;
            for (int c = 0; c < Main.AGENT_COUNT; c++) {
                angle = Math.random() * 2 * Math.PI;
                min = Math.min(Main.WIDTH, Main.HEIGHT);
                radius = Math.random() * (min / 2 - Main.AGENT_SIZE - min / 4) + min / 4;
                agents[c] = new Agent(Math.cos(angle) * radius + Main.WIDTH / 2, Math.sin(angle) * radius + Main.HEIGHT / 2, angle + Math.PI);
            }
        } else if (Main.SPAWN_MODE == Spawn.WATERFALL) {
            for (int c = 0; c < Main.AGENT_COUNT; c++) {
                agents[c] = new Agent((Math.random() * Main.WIDTH - Main.AGENT_SIZE) + Main.AGENT_SIZE, (Math.random() * Main.HEIGHT - Main.AGENT_SIZE) + Main.AGENT_SIZE, Math.PI / 2);
            }
        } else if (Main.SPAWN_MODE == Spawn.POINT) {
            for (int c = 0; c < Main.AGENT_COUNT; c++) {
                agents[c] = new Agent(Main.WIDTH / 2, Main.HEIGHT / 2, Math.random() * 2 * Math.PI);
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        //blur();
        int x = 0;
        int y = 0;
        g2D.setPaint(Main.AGENT_COLOR);
        g2D.setStroke(new BasicStroke(Main.AGENT_SIZE));
        for (Agent ag: agents) {
            x = (int)ag.getx();
            y = (int)ag.gety();
            g2D.drawLine(x, y, x, y);
            move(ag);
        }
        trailMap.keySet().removeIf(k -> trailMap.get(k) <= 0);
        AlphaComposite alpha;
        for(Point point: trailMap.keySet()) {
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)trailMap.get(point) / (Main.TRAIL_LENGTH + Main.TRAIL_FADE));
            g2D.setComposite(alpha);
            g2D.drawLine(point.x, point.y, point.x, point.y);
            trailMap.put(point, trailMap.get(point) - 1);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    public void move(Agent agent) {
        check(agent);
        trailMap.put(new Point((int)agent.getx(), (int)agent.gety()), (float)Main.TRAIL_LENGTH);
        double new_x = agent.getx() + Math.cos(agent.getang()) * Main.AGENT_SPEED;
        double new_y = agent.gety() + Math.sin(agent.getang()) * Main.AGENT_SPEED;
        /*
        if (new_x > Main.WIDTH - (int)(Main.AGENT_SIZE / 2) || new_x < Main.AGENT_SIZE / 2 || new_y > Main.HEIGHT - (int)(Main.AGENT_SIZE / 2) || new_y < Main.AGENT_SIZE / 2) {
            new_x = Math.min(Main.WIDTH - Main.AGENT_SIZE / 2, Math.max(Main.AGENT_SIZE / 2, new_x));
            new_y = Math.min(Main.HEIGHT - Main.AGENT_SIZE / 2, Math.max(Main.AGENT_SIZE / 2, new_y));
            agent.setang(Math.random() * 2 * Math.PI);
        }
        */
        if (new_x > Main.WIDTH - (int)(Main.AGENT_SIZE / 2)) {
            new_x = Main.AGENT_SIZE / 2;
        } else if (new_x < Main.AGENT_SIZE / 2) {
            new_x = Main.WIDTH - (int)(Main.AGENT_SIZE / 2);
        }
        if (new_y > Main.HEIGHT - (int)(Main.AGENT_SIZE / 2)) {
            new_y = Main.AGENT_SIZE / 2;
        } else if (new_y < Main.AGENT_SIZE / 2) {
            new_y = Main.HEIGHT - (int)(Main.AGENT_SIZE / 2);
        }
        agent.setx(new_x); agent.sety(new_y); 
    }
    public void check(Agent agent) {
        float weight_forward = sense(agent, 0);
        float weight_right = sense(agent, -Main.SENSOR_OFFSET);
        float weight_left= sense(agent, Main.SENSOR_OFFSET);
        if (weight_forward > weight_right && weight_forward > weight_left) {
        } else if (weight_forward == weight_right) {
        } else if (weight_right > weight_left) {
            agent.setang(agent.getang() - (Math.random() * Main.TURN_SPEED));
        } else if (weight_left > weight_right) {
            agent.setang(agent.getang() + (Math.random() * Main.TURN_SPEED));
        }
    }
    public float sense(Agent agent, double offset) {
        double angle = agent.getang() + offset;
        int center_x = (int)(agent.getx() + Main.SENSOR_DST * Math.cos(angle));
        int center_y = (int)(agent.gety() + Main.SENSOR_DST * Math.sin(angle));
        float sum = 0;
        for (int x = -Main.SENSOR_SIZE; x <= Main.SENSOR_SIZE; x++) {
            for (int y = -Main.SENSOR_SIZE; y <= Main.SENSOR_SIZE; y++) {
                Point pos = new Point(center_x + x, center_y + y);
                if (!trailMap.containsKey(pos)) {continue;}
                sum += trailMap.get(pos);
            }
        }
        return sum;
    }
    /*
    public void blur() {
        HashMap<Point, Float> temp = new HashMap<>();
        for (Point point: trailMap.keySet()) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x < Main.WIDTH && x >= 0 && y < Main.HEIGHT && y >= 0 && !(x == 0 && y == 0)) {
                        temp.put(new Point(x, y), trailMap.get(point) / 20);
                    }
                } // for y
            } // for x
        } // for point
        for (Point point: temp.keySet()) {
            if (!trailMap.containsKey(point)) {
                trailMap.put(point, temp.get(point));
            } else {
                if (temp.get(point) + trailMap.get(point) > 1) {
                    trailMap.put(point, 0.99f);
                    System.out.print("if");
                } else {
                    trailMap.put(point, temp.get(point) + trailMap.get(point));
                    System.out.print("else");
                }
            }
        }
    } // blur
    */
}