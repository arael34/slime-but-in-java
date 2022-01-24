package Scripts;

class Settings {
    enum Spawn {
        RANDOM,
        CIRCLE_IN,
        CIRCLE_OUT,
        DONUT_IN
    }
    final static int AGENT_COUNT = 10;
    final static int AGENT_SPEED = 2;
    final static int AGENT_SIZE = 2;
    final static int WIDTH = 500;
    final static int HEIGHT = 500;
    final static int TRAIL_LENGTH = 50;
    final static Spawn SPAWN_MODE = Spawn.RANDOM;
}
