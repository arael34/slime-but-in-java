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
    public void setx(int n) {
        this.x = n;
    }
    public int gety() {
        return this.y;
    }
    public void sety(int n) {
        this.y = n;
    }
    public float getang() {
        return this.ang;
    }
    public void setang(float n) {
        this.ang = n;
    }
}
