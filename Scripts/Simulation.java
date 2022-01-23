package Scripts;
import java.lang.Math;

/*
TODO
make imagery
create trails
create sensing function
*/

class Simulation {
    public static void main(String[] args) {
        Agent[] agents = new Agent[Settings.AGENT_COUNT];
        for (int c = 0; c < Settings.AGENT_COUNT; c++) {
            agents[c] = new Agent(0, 0, (float)Math.PI);
        }
        System.out.print("All good\n");
    }
    public void move(Agent agent) {
        int x = agent.getx();
        int y = agent.gety();
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

