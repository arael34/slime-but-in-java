package Scripts;

class Settings {
    enum Spawn {
        RANDOM,
        CIRCLE_IN,
        CIRCLE_OUT,
        DONUT,
        WATERFALL
    }
    final static int AGENT_COUNT = 1000;
    final static int AGENT_SPEED = 1;
    final static int AGENT_SIZE = 1;
    final static int WIDTH = 800;
    final static int HEIGHT = 800;
    final static int TRAIL_LENGTH = 200;
    final static int SENSOR_SIZE = 3; 
    final static int SENSOR_DST = 4;
    final static double SENSOR_OFFSET = 0.3;
    final static Spawn SPAWN_MODE = Spawn.RANDOM;
}
