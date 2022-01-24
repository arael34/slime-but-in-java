package Scripts;
import java.awt.Color;

/*
TO-DO:
more spawn enums
blur function
*/

class Main {
    public static void main(String[] args) {
        new Screen();
    }
}
class Settings {
    enum Spawn {
        RANDOM,
        CIRCLE_IN,
        CIRCLE_OUT,
        DONUT,
        WATERFALL
    }
    final static int FPS = 144;
    final static int AGENT_COUNT = 2000;
    final static int AGENT_SPEED = 1;
    final static int AGENT_SIZE = 1;
    final static Color AGENT_COLOR = Color.PINK;
    final static double TURN_SPEED = Math.PI / 6;
    final static int TRAIL_FADE = 50;
    final static int TRAIL_LENGTH = 200;
    final static int WIDTH = 400;
    final static int HEIGHT = 400;
    final static int SENSOR_SIZE = 3; 
    final static int SENSOR_DST = 4;
    final static double SENSOR_OFFSET = 0.3;
    final static Spawn SPAWN_MODE = Spawn.CIRCLE_IN;
    final static Color BACKGROUND = Color.BLACK;
}