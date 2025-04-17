// name surname: Ahmet Sait Denizli
// student ID: 2023400309   

public class Player {
    private double x;
    private double y;
    private double velocityY;
    private char facing;

    final private double width = 20;
    final private double height = 20;
    final private double[] initialCoords = new double[] { 130, 460 };

    public Player() {
        this.x = initialCoords[0];
        this.y = initialCoords[1];
        this.velocityY = 0;
        this.facing = 'R';
    }

    public int[] getCoordinates() {
        return new int[] { (int) this.x, (int) this.y, (int) this.width + (int) this.x,
                (int) this.height + (int) this.y, 0 };
    }

    public void resetPosition() {
        this.x = initialCoords[0];
        this.y = initialCoords[1];
    }

    public double getWidth() { return this.width; }

    public double getHeight() { return this.height; }

    public double getX() { return this.x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return this.y; }

    public void setY(double y) { this.y = y; }

    public double getVelocityY() { return this.velocityY; }

    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    public char getFacing() { return this.facing; }

    public void setFacing(char facing) { this.facing = facing; }
}
