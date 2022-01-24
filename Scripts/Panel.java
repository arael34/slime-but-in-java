package Scripts;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Panel extends JPanel implements ActionListener {
    Timer timer;
    Agent[] agents;
    Panel() {
        this.setPreferredSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        this.setBackground(Color.BLACK);
        timer = new Timer(10, this);
        timer.start();
        agents = new Agent[Settings.AGENT_COUNT];
        for (int c = 0; c < Settings.AGENT_COUNT; c++) {
            agents[c] = new Agent((int)(Math.random() * 500) + 10, (int)(Math.random() * 500) + 10, (float)Math.PI);
        }
    }
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLUE);
        g2D.setStroke(new BasicStroke(Settings.AGENT_SIZE));
        for (Agent agent: agents) {
            //move(agent);
            g2D.drawLine(agent.getx(), agent.gety(), agent.getx(), agent.gety());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    public void move(Agent agent) {
        //int x = agent.getx();
        //int y = agent.gety();
        double new_x = Math.cos(agent.getang()) * Settings.AGENT_SPEED;
        double new_y = Math.sin(agent.getang()) * Settings.AGENT_SPEED;
        if (new_x > Settings.WIDTH - Settings.AGENT_SIZE || new_x < Settings.AGENT_SIZE
        || new_y > Settings.HEIGHT - Settings.AGENT_SIZE || new_y < Settings.AGENT_SIZE
        ) {
            new_x = Math.max(0, Math.min(Settings.WIDTH - Settings.AGENT_SIZE, new_x));
            new_y = Math.max(0, Math.min(Settings.HEIGHT - Settings.AGENT_SIZE, new_y));
            agent.setang((float)(Math.random() * 2 * Math.PI));
        }
        agent.setx((int)new_x); agent.sety((int)new_y);
        // add x and y to trailmap
    }
    public void check(Agent agent) {
        int weight_forward = sense(agent, 0);
        int weight_right = sense(agent, 0);
        int weight_left= sense(agent, 0);
        float strength = (float) Math.random();
        if (weight_forward >= weight_right && weight_forward >= weight_left) {
        } else if (weight_right > weight_left) {
            agent.setang((float) -Math.PI / 6 * strength);
        } else if (weight_left > weight_right) {
            agent.setang((float) Math.PI / 6 * strength);
        }
    }
    public int sense(Agent agent, float offset) {
        return 0;
    }
}