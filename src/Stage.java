
// name surname: Ahmet Sait Denizli
// student ID: 2023400309
import java.awt.Color;

public class Stage {
    // Storing the stage number
    final private int stageNumber;
    // Storing the gravity
    final private double gravity;
    // Storing the velocity of the player in the x direction
    final private double velocityX;
    // Storing the velocity of the player in the y direction
    final private double velocityY;
    // Storing the key code for the right direction
    final private int rightCode;
    // Storing the key code for the left direction
    final private int leftCode;
    // Storing the key code for the up direction
    final private int upCode;
    // Storing the ability to move in the right, left and up direction
    // Order: right, up, left
    final private boolean[] ableToMove;
    // Storing the clue
    final private String clue;
    // Storing the help
    final private String help;
    // Storing the required number of press
    final private int requiredNumberOfPress;
    // Storing the color of the stage
    final private Color color;

    public Stage(double gravity, double velocityX, double velocityY, int stageNumber, int rightCode, int leftCode,
            boolean[] ableToMove, int upCode, String clue, String help, int requiredNumberOfPress) {
        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.upCode = upCode;
        this.ableToMove = ableToMove;
        this.clue = clue;
        this.help = help;
        this.color = Game.generateRandomColor();
        this.requiredNumberOfPress = requiredNumberOfPress;
    }

    public boolean validateDoor(int numberOfPress) {
        // Handle the door validation
        // Logic may be changed in the future
        return numberOfPress >= this.requiredNumberOfPress;
    }

    /*
     * Returns the required number of press
     * 
     * @return int
     */
    public int getRequiredNumberOfPress() { return this.requiredNumberOfPress; }

    /*
     * Returns the ability to move in the given direction
     * 
     * @param char direction
     * 
     * @return boolean
     */
    public boolean getAbleToMove(char direction) {
        return switch (direction) {
            case 'R' -> this.ableToMove[0];
            case 'L' -> this.ableToMove[1];
            case 'U' -> this.ableToMove[2];
            default -> false;
        };
    }

    /*
     * Returns the stage number
     * 
     * @return int
     */
    public int getStageNumber() { return this.stageNumber; }

    /*
     * Returns the gravity
     * 
     * @return double
     */
    public double getGravity() { return this.gravity; }

    /*
     * Returns the velocity of the player in the x direction
     * 
     * @return double
     */
    public double getVelocityX() { return this.velocityX; }

    /*
     * Returns the velocity of the player in the y direction
     * 
     * @return double
     */
    public double getVelocityY() { return this.velocityY; }

    /*
     * Returns the key codes
     * 
     * @return int[]
     */
    public int[] getKeyCodes() { return new int[] { this.rightCode, this.leftCode, this.upCode }; }

    /*
     * Returns the clue
     * 
     * @return String
     */
    public String getClue() { return this.clue; }

    /*
     * Returns the help
     * 
     * @return String
     */
    public String getHelp() { return this.help; }

    /*
     * Returns the color of the stage
     * 
     * @return Color
     */
    public Color getColor() { return this.color; }
}
