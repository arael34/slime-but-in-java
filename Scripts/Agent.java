package Scripts;

class Agent {
    private double x;
    private double y;
    private double ang;
    public Agent(double x, double y, double ang) {
        this.x = x;
        this.y = y;
        this.ang = ang;
    }
    public double getx() {
        return this.x;
    }
    public void setx(double n) {
        this.x = n;
    }
    public double gety() {
        return this.y;
    }
    public void sety(double n) {
        this.y = n;
    }
    public double getang() {
        return this.ang;
    }
    public void setang(double n) {
        this.ang = n;
    }
}