// name surname: Ahmet Sait Denizli
// student ID: 2023400309

import java.awt.Color;

public class Map {
    // Used to store the player
    final private Player player;

    // Used to store the coordinates of the objects
    final private int[][] obstacles;
    final private int[] button;
    final private int[] buttonFloor;
    final private int[][] startPipe;
    final private int[][] exitPipe;
    final private int[] door;
    final private int[][] spikes;

    // Used to store current stage
    private Stage stage;
    // Used to store the total number of button presses
    private int buttonPressNum;
    // Used to store the door status
    private boolean isDoorOpen;
    // Used to store the button status
    private boolean isButtonPressed;
    // Used to store the stage completion status
    private boolean isStageCompleted = false;
    // Used to store the reset stage flag
    private boolean resetStage = false;

    // Some game constants
    final private int BUTTON_CLICK_HEIGHT = 6;
    final private int[] BUTTON_HITBOX = { 405, 400, 465, 408 };
    final private Color PIPE_COLOR = new Color(254, 199, 37);
    final private Color BUTTON_COLOR = new Color(215, 33, 11);
    final private Color FLOOR_COLOR = new Color(59, 65, 63);
    // Storing the initial coordinates of the door for animation
    final private int[] DOOR_INITIAL_COORDS;
    final private int DOOR_ANIMATION_SPEED = 10;
    // Storing the spike image
    final private String SPIKE_IMAGE = "misc/spikes.png";
    // Used to store the player images
    // Each representation is in the order of left and right
    final private String[] PLAYER_IMAGES = { "misc/elephant-left.png", "misc/elephant-right.png" };

    /*
     * Constructor for the Map class
     * 
     * @param Stage stage
     * 
     * @param Player player
     * 
     * @return void
     */
    public Map(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;

        // Set the initial status of the door and button
        this.isDoorOpen = false;
        this.isButtonPressed = false;

        // Set the coordinates of the objects
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

        this.door = new int[] { 685, 180, 700, 240 };

        // Set the initial coordinates of the door to handle reset player position
        this.DOOR_INITIAL_COORDS = new int[] { 685, 180, 700, 240 };
    }

    /*
     * Draws the map
     * 
     * @return void
     */
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

    /*
     * Draws a rectangle by coordinates
     * 
     * @param int[] coordinates
     * 
     * @param Color color
     * 
     * @param boolean isFilled
     * 
     * @return void
     */
    public static void drawRectangleByCoordinates(int[] coordinates, Color color, boolean isFilled) {
        // Calculate the width and height of the object
        // This is required to draw the object in StdDraw
        final double objectWidth = Math.abs(coordinates[2] - coordinates[0]);
        final double objectHeight = Math.abs(coordinates[3] - coordinates[1]);

        // Calculate the center of the object
        final double objectX = (coordinates[2] + coordinates[0]) / 2;
        final double objectY = (coordinates[3] + coordinates[1]) / 2;

        // Set the color of the object
        StdDraw.setPenColor(color);

        // Draw the object by handling the fill status
        if (isFilled) {
            StdDraw.filledRectangle(objectX, objectY, objectWidth / 2, objectHeight / 2);
        } else {
            StdDraw.rectangle(objectX, objectY, objectWidth / 2, objectHeight / 2);
        }
    }

    /*
     * Draws a picture by coordinates
     * 
     * @param int[] coordinates
     * 
     * @param String picture
     * 
     * @return void
     */
    public static void drawPictureByCoordinates(int[] coordinates, String picture) {
        // Calculate the center of the object
        final double objectX = (coordinates[0] + coordinates[2]) / 2.0;
        final double objectY = (coordinates[1] + coordinates[3]) / 2.0;

        // Calculate the width and height of the object
        double objectWidth = Math.abs(coordinates[2] - coordinates[0]);
        double objectHeight = Math.abs(coordinates[3] - coordinates[1]);

        // Calculate the rotation angle of the object
        double rotationAngle = coordinates[4] * 90;

        // If the rotation angle is 90 or 270, swap the width and height of the object
        if (rotationAngle == 90 || rotationAngle == 270) {
            double temp = objectWidth;
            objectWidth = objectHeight;
            objectHeight = temp;
        }

        // Draw the object
        StdDraw.picture(objectX, objectY, picture, objectWidth, objectHeight, rotationAngle);
    }

    /*
     * Generic method to draw text by coordinates
     * 
     * @param int[] coordinates
     * 
     * @param String text
     * 
     * @param Color color
     * 
     * @param char direction
     * 
     * @return void
     */
    public static void drawTextByCoordinates(int[] coordinates, String text, Color color, char direction) {
        StdDraw.setPenColor(color);

        // Draw the text by handling the direction
        switch (direction) {
            case 'R' -> StdDraw.textRight(coordinates[0], coordinates[1], text);
            case 'L' -> StdDraw.textLeft(coordinates[0], coordinates[1], text);
            case 'C' -> StdDraw.text(coordinates[0], coordinates[1], text);
        }
    }

    /*
     * Draws a button by coordinates by using generic methods
     * 
     * @param String buttonText
     * 
     * @param int[] buttonCoords
     * 
     * @param Color color
     * 
     * @return void
     */
    public static void drawButton(String buttonText, int[] buttonCoords, Color color) {
        // Calculate the center of the button
        int midX = (buttonCoords[0] + buttonCoords[2]) / 2;
        int midY = (buttonCoords[1] + buttonCoords[3]) / 2;

        // Set the coordinates of the button
        int[] buttonMidCoords = new int[] { midX, midY };

        // Draw the text and rectangle
        drawTextByCoordinates(buttonMidCoords, buttonText, color, 'C');
        drawRectangleByCoordinates(buttonCoords, color, false);
    }

    /*
     * Cycles the map
     * 
     * @return void
     */
    public void mapCycle() {
        this.updateAcceleration(player);
        this.updatePosition();
        this.handlePlayerCollision(player.getX(), player.getY());
        this.handleDoorAnimation();
    }

    /*
     * Moves the player
     * 
     * @param char direction
     * 
     * @return void
     */
    public void movePlayer(char direction) {
        double playerX = player.getX();
        double playerY = player.getY();

        // Move the player by handling the direction
        switch (direction) {
            case 'R' -> {
                // Set the facing direction, even if the player is not moving
                player.setFacing('R');

                // If the player is able to move right, check for collisions
                if (stage.getAbleToMove('R')) {
                    boolean checkRightCollision = checkPlayerCollision(playerX + stage.getVelocityX(), playerY);
                    if (!checkRightCollision) player.setX(playerX + stage.getVelocityX());
                }
            }

            case 'L' -> {
                // Set the facing direction, even if the player is not moving
                player.setFacing('L');

                // If the player is able to move left, check for collisions
                if (stage.getAbleToMove('L')) {
                    boolean checkLeftCollision = checkPlayerCollision(playerX - stage.getVelocityX(), playerY);
                    if (!checkLeftCollision) player.setX(playerX - stage.getVelocityX());
                }
            }

            case 'U' -> {
                // Calculate the next y position of the player
                double nextY = playerY + stage.getVelocityY() / Game.GAME_FPS;

                // If the player is able to move up, check for collisions
                if (stage.getAbleToMove('U')) {
                    boolean isCollidingTop = checkPlayerCollision(playerX, nextY);
                    boolean isCollidingBottom = checkPlayerCollision(playerX, playerY - 10);

                    // If the player is not moving or colliding with the top or bottom, break
                    if (player.getVelocityY() != 0 || isCollidingTop || !isCollidingBottom) {
                        break;
                    }

                    // Set the velocity of the player
                    player.setVelocityY(stage.getVelocityY());
                }
            }
        }
    }

    /*
     * Handles the door animation
     * 
     * @return void
     */
    private void handleDoorAnimation() {
        // If the door is not open and the door is not at the initial position, move the
        // door to initial position
        if (!this.isDoorOpen && this.door[3] != this.DOOR_INITIAL_COORDS[3]) {
            this.door[3] += this.DOOR_ANIMATION_SPEED;
        }

        // If the door is open and the door is not at required position, move the door
        // to required position
        if (this.isDoorOpen && this.door[3] != this.DOOR_INITIAL_COORDS[1]) {
            this.door[3] -= this.DOOR_ANIMATION_SPEED;
        }
    }

    /*
     * Updates the acceleration of the player
     * 
     * @param Player player
     * 
     * @return void
     */
    private void updateAcceleration(Player player) {
        player.setVelocityY(player.getVelocityY() + this.stage.getGravity());
    }

    /*
     * Updates the position of the player
     * 
     * @return void
     */
    private void updatePosition() {
        double playerX = player.getX();
        double playerY = player.getY();

        // Calculate the next y position of the player
        // By dividing the velocity by the game fps
        // the is is not affected by the game fps
        double nextY = playerY + player.getVelocityY() / Game.GAME_FPS;

        // If there is a collision with a spike, the movement is not updated.
        if (checkPlayerCollision(playerX, nextY)) {
            // Set the velocity of the player to 0 since the player hits an object
            player.setVelocityY(0);
            return;
        }
        // If there is no collision, the movement is updated normally.
        player.setY(nextY);
    }

    /*
     * Checks if the player is colliding with an object It handles just obstacle and
     * door collision since just these obstruct the player movement
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @return boolean
     */
    private boolean checkPlayerCollision(double nextX, double nextY) {
        return checkObstacleCollisions(nextX, nextY) || checkDoorCollisions(nextX, nextY);
    }

    /*
     * Handles the player collisions with the button, exit pipe and spikes
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @return void
     */
    private void handlePlayerCollision(double nextX, double nextY) {
        // For button RCV handling, we need to check if the player is currently on the
        // button
        boolean isCurrentlyOnButton = checkButtonCollisions(nextX, nextY);

        if (isCurrentlyOnButton) {
            // If the player is on the button and the button is not pressed
            if (!this.isButtonPressed) {
                // Reduce button height to simulate button being pressed
                button[3] -= BUTTON_CLICK_HEIGHT;
                // Increment the button press number
                buttonPressNum++;
                // Validate the door
                this.isDoorOpen = stage.validateDoor(buttonPressNum);
                // Set the button pressed status to true
                this.isButtonPressed = true;
            }
        }

        // If the player is not on the button and the button is pressed
        else {
            if (this.isButtonPressed) {
                // Increase button height to simulate button being released
                button[3] += BUTTON_CLICK_HEIGHT;
                // Set the button pressed status to false
                this.isButtonPressed = false;
            }
        }

        // If the player is colliding with the exit pipe, the stage is completed
        if (checkExitPipeCollisions(nextX, nextY)) {
            this.isStageCompleted = true;
        }

        // If the player is colliding with a spike, the stage is reset
        if (checkSpikeCollisions(nextX, nextY)) {
            this.resetStage = true;
        }
    }

    /*
     * Checks if the player is colliding with an object
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @param int[] obstacle
     * 
     * @return boolean
     */
    private boolean checkCollision(double nextX, double nextY, int[] obstacle) {
        final double playerWidth = player.getWidth();
        final double playerHeight = player.getHeight();

        boolean isXCollided = nextX + playerWidth > obstacle[0] && nextX < obstacle[2];
        boolean isYCollided = nextY + playerHeight > obstacle[1] && nextY < obstacle[3];

        return isXCollided && isYCollided;
    }

    /*
     * Checks if the player is colliding with the exit pipe
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @return boolean
     */
    private boolean checkExitPipeCollisions(double nextX, double nextY) {
        return checkCollision(nextX, nextY, exitPipe[1]);
    }

    /*
     * Checks if the player is colliding with the button
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @return boolean
     */
    private boolean checkButtonCollisions(double nextX, double nextY) {
        return checkCollision(nextX, nextY, BUTTON_HITBOX);
    }

    /*
     * Checks if the player is colliding with an obstacle
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @return boolean
     */
    private boolean checkObstacleCollisions(double nextX, double nextY) {
        for (final int[] obstacle : obstacles) {
            if (checkCollision(nextX, nextY, obstacle)) return true;
        }
        return false;
    }

    /*
     * Checks if the player is colliding with a spike
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @return boolean
     */
    private boolean checkSpikeCollisions(double nextX, double nextY) {
        for (final int[] spike : spikes) {
            if (checkCollision(nextX, nextY, spike)) return true;
        }
        return false;
    }

    /*
     * Checks if the player is colliding with the door
     * 
     * @param double nextX
     * 
     * @param double nextY
     * 
     * @return boolean
     */
    private boolean checkDoorCollisions(double nextX, double nextY) {
        return checkCollision(nextX, nextY, door);
    }

    /*
     * Changes the stage
     * 
     * @return boolean
     */
    public boolean changeStage() {
        return this.isStageCompleted;
    }

    /*
     * Restarts the stage
     * 
     * @return void
     */
    public void restartStage() {
        // Close the door
        this.isDoorOpen = false;
        // Reset the player position
        player.resetPosition();
        // Set the velocity of the player to 0
        player.setVelocityY(0);
        // Set the facing direction of the player to right
        player.setFacing('R');
        // Reset the button press number
        this.buttonPressNum = 0;
        // Reset the stage completion status
        this.isStageCompleted = false;
        // Reset the reset stage flag
        this.resetStage = false;
    }

    /*
     * Sets the stage
     * 
     * @param Stage stage
     * 
     * @return void
     */
    public void setStage(Stage stage) {
        // Setting the stage
        this.stage = stage;
        // Resetting the stage params to handle the new stage
        this.restartStage();
    }

    /*
     * Returns the stage
     * 
     * @return Stage
     */
    public Stage getStage() { return stage; }

    /*
     * Returns the player
     * 
     * @return Player
     */
    public Player getPlayer() { return player; }

    /*
     * Returns the reset stage flag
     * 
     * @return boolean
     */
    public boolean getResetStage() { return this.resetStage; }

}
