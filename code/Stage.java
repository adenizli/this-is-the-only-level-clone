
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
    final private String clue;
    final private String help;
    final private Color color;

    private int requiredNumberOfPress;

    public Stage(double gravity, double velocityX, double velocityY, int stageNumber, int rightCode, int leftCode,
            int upCode, String clue, String help) {
        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.upCode = upCode;
        this.clue = clue;
        this.help = help;
        this.color = this.generateRandomColor();
        this.requiredNumberOfPress = 1;
    }

    public void updatePosition(Player player) {
        player.setVelocityY(player.getVelocityY() + this.gravity);

    }

    private Color generateRandomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    public boolean validateDoor(int numberOfPress) {
        return numberOfPress >= this.requiredNumberOfPress;
    }

    public void setRequiredNumberOfPress(int requiredNumberOfPress) {
        this.requiredNumberOfPress = requiredNumberOfPress;
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
