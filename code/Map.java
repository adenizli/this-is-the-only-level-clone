
import java.awt.Color;
// name surname: Ahmet Sait Denizli
// student ID: 2023400309

public class Map {

    private Stage stage;
    final private Player player;
    final private int[][] obstacles;
    final private int[] button;
    final private int[] buttonFloor;
    final private int BUTTON_CLICK_HEIGHT = 6;
    final private int[][] startPipe;
    final private int[][] exitPipe;
    final private Color PIPE_COLOR = new Color(254, 199, 37);
    final private Color BUTTON_COLOR = new Color(215, 33, 11);
    final private Color FLOOR_COLOR = new Color(59, 65, 63);
    final private int[][] spikes;
    final private int[] DOOR_INITIAL_COORDS;
    final private int DOOR_ANIMATION_SPEED = 10;
    final private int[] door;

    private int buttonPressNum;
    private boolean isDoorOpen;

    private boolean isButtonPressed;
    private boolean isStageCompleted = false;
    private boolean resetStage = false;

    final private String SPIKE_IMAGE = "misc/spikes.png";
    final private String[] PLAYER_IMAGES = { "misc/elephant-left.png", "misc/elephant-right.png" };

    public Map(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;

        this.isDoorOpen = false;
        this.isButtonPressed = false;

        this.obstacles = new int[][] { new int[] { 0, 120, 120, 270 }, new int[] { 0, 270, 168, 330 },
                new int[] { 0, 330, 30, 480 }, new int[] { 0, 480, 180, 600 }, new int[] { 180, 570, 680, 600 },
                new int[] { 270, 540, 300, 570 }, new int[] { 590, 540, 620, 570 }, new int[] { 680, 510, 800, 600 },
                new int[] { 710, 450, 800, 510 }, new int[] { 740, 420, 800, 450 }, new int[] { 770, 300, 800, 420 },
                new int[] { 680, 240, 800, 300 }, new int[] { 680, 300, 710, 330 }, new int[] { 770, 180, 800, 240 },
                new int[] { 0, 120, 800, 150 }, new int[] { 560, 150, 800, 180 }, new int[] { 530, 180, 590, 210 },
                new int[] { 530, 210, 560, 240 }, new int[] { 320, 150, 440, 210 }, new int[] { 350, 210, 440, 270 },
                new int[] { 220, 270, 310, 300 }, new int[] { 360, 360, 480, 390 }, new int[] { 530, 310, 590, 340 },
                new int[] { 560, 400, 620, 430 } };

        this.spikes = new int[][] { new int[] { 30, 333, 50, 423, 1 }, new int[] { 121, 150, 207, 170, 2 },
                new int[] { 441, 150, 557, 170, 2 }, new int[] { 591, 180, 621, 200, 2 },
                new int[] { 750, 301, 769, 419, 3 }, new int[] { 680, 490, 710, 510, 0 },
                new int[] { 401, 550, 521, 570, 0 } };

        this.button = new int[] { 405, 400, 465, 408 };

        this.buttonFloor = new int[] { 400, 390, 470, 400 };

        this.startPipe = new int[][] { new int[] { 115, 450, 145, 480 }, new int[] { 110, 430, 150, 450 } };

        this.exitPipe = new int[][] { new int[] { 720, 175, 740, 215 }, new int[] { 740, 180, 770, 210 } };

        // Door Coordinates
        this.door = new int[] { 685, 180, 700, 240 };
        this.DOOR_INITIAL_COORDS = new int[] { 685, 180, 700, 240 };
    }

    public void draw() {
        // Draw Player
        String playerImage = player.getFacing() == 'L' ? PLAYER_IMAGES[0] : PLAYER_IMAGES[1];
        Map.drawPictureByCoordinates(player.getCoordinates(), playerImage);

        // Draw obstacles seperately
        for (final int[] obstacle : obstacles) {
            Map.drawRectangleByCoordinates(obstacle, stage.getColor(), true);
        }

        // Draw Spikes
        for (final int[] spike : spikes) {
            Map.drawPictureByCoordinates(spike, SPIKE_IMAGE);
        }

        // Draw Start Pipe
        for (final int[] startPipePart : this.startPipe) {
            Map.drawRectangleByCoordinates(startPipePart, this.PIPE_COLOR, true);
        }

        // Draw Exit Pipe
        for (final int[] exitPipePart : this.exitPipe) {
            Map.drawRectangleByCoordinates(exitPipePart, this.PIPE_COLOR, true);
        }

        // Draw Button
        Map.drawRectangleByCoordinates(button, this.BUTTON_COLOR, true);

        // Draw Button Floor
        Map.drawRectangleByCoordinates(buttonFloor, this.FLOOR_COLOR, true);

        // Draw Door
        Map.drawRectangleByCoordinates(door, this.FLOOR_COLOR, true);
    }

    public static void drawRectangleByCoordinates(int[] coordinates, Color color, boolean isFilled) {
        final double objectWidth = Math.abs(coordinates[2] - coordinates[0]);
        final double objectHeight = Math.abs(coordinates[3] - coordinates[1]);
        final double objectX = (coordinates[2] + coordinates[0]) / 2;
        final double objectY = (coordinates[3] + coordinates[1]) / 2;

        StdDraw.setPenColor(color);

        if (isFilled) {
            StdDraw.filledRectangle(objectX, objectY, objectWidth / 2, objectHeight / 2);
        } else {
            StdDraw.rectangle(objectX, objectY, objectWidth / 2, objectHeight / 2);
        }
    }

    public static void drawPictureByCoordinates(int[] coordinates, String picture) {
        final double objectX = (coordinates[0] + coordinates[2]) / 2.0;
        final double objectY = (coordinates[1] + coordinates[3]) / 2.0;

        double objectWidth = Math.abs(coordinates[2] - coordinates[0]);
        double objectHeight = Math.abs(coordinates[3] - coordinates[1]);

        double rotationAngle = coordinates[4] * 90;
        if (rotationAngle == 90 || rotationAngle == 270) {
            double temp = objectWidth;
            objectWidth = objectHeight;
            objectHeight = temp;
        }

        StdDraw.picture(objectX, objectY, picture, objectWidth, objectHeight, rotationAngle);
    }

    public static void drawTextByCoordinates(int[] coordinates, String text, Color color, char direction) {
        StdDraw.setPenColor(color);
        switch (direction) {
            case 'R' -> StdDraw.textRight(coordinates[0], coordinates[1], text);
            case 'L' -> StdDraw.textLeft(coordinates[0], coordinates[1], text);
            case 'C' -> StdDraw.text(coordinates[0], coordinates[1], text);
        }
    }

    public static void drawButton(String buttonText, int[] buttonCoords, Color color) {
        int midX = (buttonCoords[0] + buttonCoords[2]) / 2;
        int midY = (buttonCoords[1] + buttonCoords[3]) / 2;

        int[] buttonMidCoords = new int[] { midX, midY };

        drawTextByCoordinates(buttonMidCoords, buttonText, color, 'C');
        drawRectangleByCoordinates(buttonCoords, color, false);
    }

    public void mapCycle() {
        this.stage.updatePosition(player);
        this.updateAcceleration();
        this.checkDoorStatus();
    }

    public void movePlayer(char direction) {
        double playerX = player.getX();
        double playerY = player.getY();

        switch (direction) {
            case 'R' -> {
                player.setFacing('R');
                boolean checkRightCollision = checkPlayerCollision(playerX + stage.getVelocityX(), playerY);
                if (!checkRightCollision) {
                    player.setX(playerX + stage.getVelocityX());
                }
            }

            case 'L' -> {
                player.setFacing('L');
                boolean checkLeftCollision = checkPlayerCollision(playerX - stage.getVelocityX(), playerY);
                if (!checkLeftCollision) {
                    player.setX(playerX - stage.getVelocityX());
                }
            }

            case 'U' -> {
                double nextY = playerY + stage.getVelocityY() / Game.GAME_FPS;
                boolean isCollidingTop = checkPlayerCollision(playerX, nextY);
                boolean isCollidingBottom = checkPlayerCollision(playerX, playerY - 10);

                if (player.getVelocityY() != 0 || isCollidingTop || !isCollidingBottom) {
                    break;
                }
                player.setVelocityY(stage.getVelocityY());
            }
        }
        this.handlePlayerCollision(player.getX(), player.getY());
    }

    public void checkDoorStatus() {
        if (!this.isDoorOpen && this.door[1] != this.DOOR_INITIAL_COORDS[1]) {
            this.door[1] -= this.DOOR_ANIMATION_SPEED;
            this.door[3] -= this.DOOR_ANIMATION_SPEED;
        }
        if (this.isDoorOpen && this.door[1] != this.DOOR_INITIAL_COORDS[3]) {
            this.door[1] += this.DOOR_ANIMATION_SPEED;
            this.door[3] += this.DOOR_ANIMATION_SPEED;
        }
    }

    public void updateAcceleration() {
        double playerX = player.getX();
        double playerY = player.getY();

        double nextY = playerY + player.getVelocityY() / Game.GAME_FPS;

        // If there is a collision with a spike, the movement is not updated.
        if (checkPlayerCollision(playerX, nextY)) {
            player.setVelocityY(0);
            return;
        }

        // If there is no collision, the movement is updated normally.
        player.setY(nextY);
        this.handlePlayerCollision(player.getX(), player.getY());
    }

    public boolean checkPlayerCollision(double nextX, double nextY) {
        return checkObstacleCollisions(nextX, nextY) || checkDoorCollisions(nextX, nextY);
    }

    public void handlePlayerCollision(double nextX, double nextY) {
        boolean isCurrentlyOnButton = checkButtonCollisions(nextX, nextY);

        if (isCurrentlyOnButton && !isButtonPressed) {
            // Oyuncu butona yeni bastı.
            button[3] -= BUTTON_CLICK_HEIGHT;
            buttonPressNum++;
            isDoorOpen = stage.validateDoor(buttonPressNum);
            isButtonPressed = true;
        } else if (!isCurrentlyOnButton && isButtonPressed) {
            // Oyuncu butondan ayrıldı.
            button[3] += BUTTON_CLICK_HEIGHT;
            isButtonPressed = false;
        }

        if (checkExitPipeCollisions(nextX, nextY)) {
            this.isStageCompleted = true;
        }

        if (checkSpikeCollisions(nextX, nextY)) {
            this.resetStage = true;
        }
    }

    public boolean checkExitPipeCollisions(double nextX, double nextY) {
        for (final int[] exitPipePart : exitPipe) {
            if (checkCollision(nextX, nextY, exitPipePart)) return true;
        }
        return false;
    }

    public boolean checkButtonCollisions(double nextX, double nextY) {
        return checkCollision(nextX, nextY, button);
    }

    public boolean checkObstacleCollisions(double nextX, double nextY) {
        for (final int[] obstacle : obstacles) {
            if (checkCollision(nextX, nextY, obstacle)) return true;
        }
        return false;
    }

    public boolean checkSpikeCollisions(double nextX, double nextY) {
        for (final int[] spike : spikes) {
            if (checkCollision(nextX, nextY, spike)) return true;
        }
        return false;
    }

    public boolean checkDoorCollisions(double nextX, double nextY) {
        return checkCollision(nextX, nextY, door);
    }

    private boolean checkCollision(double nextX, double nextY, int[] obstacle) {
        final double playerWidth = player.getWidth();
        final double playerHeight = player.getHeight();

        boolean isXCollided = nextX + playerWidth > obstacle[0] && nextX < obstacle[2];
        boolean isYCollided = nextY + playerHeight > obstacle[1] && nextY < obstacle[3];

        return isXCollided && isYCollided;
    }

    public boolean changeStage() {
        return this.isStageCompleted;
    }

    public void restartStage() {
        this.isDoorOpen = false;
        player.resetPosition();
        player.setVelocityY(0);
        player.setFacing('R');
        this.buttonPressNum = 0;
        this.isStageCompleted = false;
        this.resetStage = false;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.restartStage();
    }

    public Stage getStage() { return stage; }

    public Player getPlayer() { return player; }

    public boolean getResetStage() { return this.resetStage; }

}
