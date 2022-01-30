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
    enum Spawn {
        RANDOM,
        CIRCLE_IN,
        CIRCLE_OUT,
        DONUT,
        WATERFALL,
        POINT
    }
    enum EdgeType {
        BOUNCE,
        ROTATE
    }
    final static int FPS = 144;
    final static int AGENT_COUNT = 500;
    final static int AGENT_SPEED = 1;
    final static int AGENT_SIZE = 1;
    final static Color AGENT_COLOR = Color.PINK;
    final static double TURN_SPEED = Math.PI / 8;
    final static int TRAIL_FADE = 50;
    final static int TRAIL_LENGTH = 200;
    final static int WIDTH = 500;
    final static int HEIGHT = 500;
    final static int SENSOR_SIZE = 1; 
    final static int SENSOR_DST = 3;
    final static double SENSOR_OFFSET = 0.3;
    final static Spawn SPAWN_MODE = Spawn.WATERFALL;
    final static Color BACKGROUND = Color.BLACK;
    final static EdgeType EDGE = EdgeType.BOUNCE;
}