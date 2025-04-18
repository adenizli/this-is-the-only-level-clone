// name surname: Ahmet Sait Denizli
// student ID: 2023400309   

public class Player {
    // Storing the player's coordinates
    private double x;
    private double y;
    // Storing the player's velocity
    private double velocityY;
    // Storing the player's facing direction
    private char facing;
    // Storing the player's width
    final private double width = 20;
    // Storing the player's height
    final private double height = 20;
    // Storing the player's initial coordinates
    final private double[] initialCoords = new double[] { 130, 460 };

    /*
     * Constructor for the Player class
     * 
     * @return void
     */
    public Player() {
        this.x = initialCoords[0];
        this.y = initialCoords[1];
        this.velocityY = 0;
        this.facing = 'R';
    }

    /*
     * Returns the player's coordinates
     * 
     * @return int[]
     */
    public int[] getCoordinates() {
        return new int[] { (int) this.x, (int) this.y, (int) this.width + (int) this.x,
                (int) this.height + (int) this.y, 0 };
    }

    /*
     * Resets the player's position
     * 
     * @return void
     */
    public void resetPosition() {
        this.x = initialCoords[0];
        this.y = initialCoords[1];
    }

    /*
     * Returns the player's width
     * 
     * @return double
     */
    public double getWidth() { return this.width; }

    /*
     * Returns the player's height
     * 
     * @return double
     */
    public double getHeight() { return this.height; }

    /*
     * Returns the player's x coordinate
     * 
     * @return double
     */
    public double getX() { return this.x; }

    /*
     * Sets the player's x coordinate
     * 
     * @param double x
     * 
     * @return void
     */
    public void setX(double x) { this.x = x; }

    /*
     * Returns the player's y coordinate
     * 
     * @return double
     */
    public double getY() { return this.y; }

    /*
     * Sets the player's y coordinate
     * 
     * @param double y
     * 
     * @return void
     */
    public void setY(double y) { this.y = y; }

    /*
     * Returns the player's velocity in the y direction
     * 
     * @return double
     */
    public double getVelocityY() { return this.velocityY; }

    /*
     * Sets the player's velocity in the y direction
     * 
     * @param double velocityY
     * 
     * @return void
     */
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    /*
     * Returns the player's facing direction
     * 
     * @return char
     */
    public char getFacing() { return this.facing; }

    /*
     * Sets the player's facing direction
     * 
     * @param char facing
     * 
     * @return void
     */
    public void setFacing(char facing) { this.facing = facing; }
}
