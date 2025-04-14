// name surname: Ahmet Sait Denizli
// student ID: 2023400309   

public class Player {
    private double x;
    private double y;
    final private double width;
    final private double height;
    private double velocityY;
    private char facing;
    final private double[] initialCoords = new double[] { 130, 460 };

    public Player() {
        this.x = initialCoords[0];
        this.y = initialCoords[1];
        this.width = 20;
        this.height = 20;
        this.velocityY = 0;
        this.facing = 'R';
    }

    public double getX() { return this.x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return this.y; }

    public void setY(double y) { this.y = y; }

    public double getWidth() { return this.width; }

    public double getHeight() { return this.height; }

    public double getVelocityY() { return this.velocityY; }

    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    public int[] getCoordinates() {
        return new int[] { (int) this.x, (int) this.y, (int) this.width + (int) this.x,
                (int) this.height + (int) this.y, 0 };
    }

    public char getFacing() { return this.facing; }

    public void setFacing(char facing) { this.facing = facing; }

    public void resetPosition() {
        this.x = initialCoords[0];
        this.y = initialCoords[1];
    }
}
