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
        System.out.print("All good");
    }
}

