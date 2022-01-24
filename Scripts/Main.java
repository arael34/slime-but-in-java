package Scripts;
import java.awt.Color;

/*
TO-DO:
more spawn enums
blur function
*/

class Main {
    public static void main(String[] args) {
        Screen s = new Screen();
    }
    enum Spawn {
        RANDOM,
        CIRCLE_IN,
        CIRCLE_OUT,
        DONUT,
        WATERFALL,
        POINT
    }
    final static int FPS = 60;
    final static int AGENT_COUNT = 1000;
    final static int AGENT_SPEED = 1;
    final static int AGENT_SIZE = 1;
    final static Color AGENT_COLOR = new Color(255, 67, 164);
    final static double TURN_SPEED = Math.PI / 8;
    final static int TRAIL_FADE = 50;
    final static int TRAIL_LENGTH = 200;
    final static int WIDTH = 500;
    final static int HEIGHT = 500;
    final static int SENSOR_SIZE = 2; 
    final static int SENSOR_DST = 3;
    final static double SENSOR_OFFSET = 0.3;
    final static Spawn SPAWN_MODE = Spawn.CIRCLE_IN;
    final static Color BACKGROUND = new Color(150, 0, 0);
}