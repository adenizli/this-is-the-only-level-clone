
// name surname: Ahmet Sait Denizli
// student ID: 2023400309
import java.awt.Color;

public class Stage {
    final private int stageNumber;
    final private double gravity;
    final private double velocityX;
    final private double velocityY;
    final private int rightCode;
    final private int leftCode;
    final private int upCode;
    final private boolean[] ableToMove;
    final private String clue;
    final private String help;
    final private int requiredNumberOfPress;
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
        return numberOfPress >= this.requiredNumberOfPress;
    }

    public int getRequiredNumberOfPress() { return this.requiredNumberOfPress; }

    public boolean getAbleToMove(char direction) {
        return switch (direction) {
            case 'R' -> this.ableToMove[0];
            case 'L' -> this.ableToMove[1];
            case 'U' -> this.ableToMove[2];
            default -> false;
        };
    }

    public int getStageNumber() { return this.stageNumber; }

    public double getGravity() { return this.gravity; }

    public double getVelocityX() { return this.velocityX; }

    public double getVelocityY() { return this.velocityY; }

    public int[] getKeyCodes() { return new int[] { this.rightCode, this.leftCode, this.upCode }; }

    public String getClue() { return this.clue; }

    public String getHelp() { return this.help; }

    public Color getColor() { return this.color; }
}
