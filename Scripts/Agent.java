package Scripts;

class Agent {
    private int x;
    private int y;
    private float ang;
    public Agent(int x, int y, float ang) {
        this.x = x;
        this.y = y;
        this.ang = ang;
    }
    public int getx() {
        return this.x;
    }
    public int gety() {
        return this.y;
    }
    public float getang() {
        return this.ang;
    }
}
