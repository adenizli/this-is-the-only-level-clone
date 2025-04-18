// name surname: Ahmet Sait Denizli
// student ID: 2023400309

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    private enum GAME_STATE {
        INIT, PLAYING, WINNER
    }

    // Used to store the game state
    private GAME_STATE gameState;
    // Used to store the stage index
    private int stageIndex;
    // Used to store the stages
    private ArrayList<Stage> stages;
    // Used to store the death number
    private int deathNumber;
    // Used to store the game time
    private double gameTime;
    // Used to store the reset time
    private double resetTime;
    // Used to store the reset game flag
    private boolean resetGame;
    // Used to store the help pressed flag
    private boolean isHelpPressed;
    // Used to store the restart recently pressed flag
    // (Check documentation Section 2.5.1)
    private boolean isRestartRecentlyPressed;

    // Used to store the help button coordinates
    private final int[] HELP_BUTTON_COORDS = new int[] { 210, 75, 290, 105 };
    // Used to store the restart button coordinates
    private final int[] RESTART_BUTTON_COORDS = new int[] { 510, 75, 590, 105 };
    // Used to store the reset game button coordinates
    private final int[] RESET_GAME_BUTTON_COORDS = new int[] { 320, 5, 480, 35 };
    // Used to store the info section coordinates
    private final int[] INFO_SECTION_COORDS = new int[] { 0, 0, 800, 120 };
    // Used to store the info section color
    private final Color INFO_SECTION_COLOR = new Color(56, 93, 172);
    // Used to store the game fonts
    private final Font[] GAME_FONTS = new Font[] { new Font("Arial", Font.PLAIN, 16), new Font("Arial", Font.BOLD, 24),
            new Font("Arial", Font.PLAIN, 18) };

    // Used to set the game FPS
    public static final int GAME_FPS = 60;

    /*
     * Constructor for the Game class
     * 
     * @param ArrayList<Stage> stages
     * 
     * @return void
     */
    public Game(ArrayList<Stage> stages) {
        this.stages = stages;
        this.stageIndex = 0;
        this.deathNumber = 0;
        this.gameTime = 0;
        this.resetGame = false;
        this.gameState = GAME_STATE.INIT;
        this.isHelpPressed = false;
        this.resetTime = System.currentTimeMillis();

        // Creating the window and setting the scale
        this.initializeGUI();
    }

    /*
     * Initialize the GUI
     * 
     * @return void
     */
    private void initializeGUI() {
        final int CANVAS_WIDTH = 800;
        final int CANVAS_HEIGHT = 600;

        // Draw the game area
        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        StdDraw.setXscale(0, CANVAS_WIDTH);
        StdDraw.setYscale(0, CANVAS_HEIGHT);
    }

    /*
     * Play the game
     * 
     * @return void
     */
    public void play() {
        final Player player = new Player();
        final Map map = new Map(this.getCurrentStage(), player);

        // Set the game state to playing
        this.gameState = GAME_STATE.PLAYING;

        while (true) {
            StdDraw.clear();

            handleInput(map);

            switch (this.gameState) {
                case GAME_STATE.PLAYING -> {
                    // Draw the map and objects
                    map.draw();

                    // Render the info bar
                    this.renderInfoBar();

                    // Update the game time
                    this.gameTime = System.currentTimeMillis() - this.resetTime;

                    // Cycle the map
                    map.mapCycle();

                    // Check if the stage is over
                    if (map.changeStage()) this.setNextStage(map);

                    // Restart the stage
                    if (map.getResetStage()) this.restartStage(map);
                }
                case GAME_STATE.WINNER -> this.drawWinnerBanner();
            }
            if (this.resetGame) this.resetGame(map);

            // Show the screen regarding the FPS
            StdDraw.show(1000 / GAME_FPS);
        }
    }

    /*
     * Render the info bar
     * 
     * @return void
     */
    private void renderInfoBar() {
        StdDraw.setPenColor(this.INFO_SECTION_COLOR);

        // Draw the info bar
        Map.drawRectangleByCoordinates(INFO_SECTION_COORDS, this.INFO_SECTION_COLOR, true);

        // Set the pen color to white
        StdDraw.setPenColor(StdDraw.WHITE);

        // Draw the buttons
        Map.drawButton("Help", this.HELP_BUTTON_COORDS, StdDraw.WHITE);
        Map.drawButton("Restart", this.RESTART_BUTTON_COORDS, StdDraw.WHITE);
        Map.drawButton("Reset Game", this.RESET_GAME_BUTTON_COORDS, StdDraw.WHITE);

        // Draw the deaths, stage, and time
        StdDraw.text(700, 75, "Deaths: " + this.deathNumber);
        StdDraw.text(700, 50, "Stage: " + (this.stageIndex + 1));

        // Draw the time
        StdDraw.text(100, 50, this.formattedTime());
        StdDraw.text(100, 75, "Level: 1");

        // Draw the help or clue
        if (this.isHelpPressed) {
            StdDraw.text(400, 85, "Help");
            StdDraw.text(400, 55, this.getCurrentStage().getHelp());
        } else {
            StdDraw.text(400, 85, "Clue:");
            StdDraw.text(400, 55, this.getCurrentStage().getClue());
        }
    }

    /*
     * Check if the mouse is pressed on the button
     * 
     * @param int[] buttonCoords
     * 
     * @return boolean
     */
    private boolean isButtonPressed(int[] buttonCoords) {
        // Get the mouse coordinates
        double[] mouseCoords = new double[] { StdDraw.mouseX(), StdDraw.mouseY() };

        // Check if the mouse is in the button range by checking the x and y coordinates
        boolean isXinRange = mouseCoords[0] >= buttonCoords[0] && mouseCoords[0] <= buttonCoords[2];
        boolean isYinRange = mouseCoords[1] >= buttonCoords[1] && mouseCoords[1] <= buttonCoords[3];

        // Return true if the mouse is in the button range
        return isXinRange && isYinRange;
    }

    /*
     * Set the next stage
     * 
     * @param Map map
     * 
     * @return void
     */
    private void setNextStage(Map map) {
        // Check if the stage index is in the range
        boolean stageIndexInRange = this.stageIndex < this.stages.size() - 1;

        // If the stage index is in the range, set the next stage
        if (stageIndexInRange) {
            this.stageIndex++;

            // Set the stage
            map.setStage(this.getCurrentStage());

            // Reset the help pressed flag
            this.isHelpPressed = false;

            // Draw the next stage banner
            this.drawNextStageBanner();

            // Pause for 2 seconds
            StdDraw.pause(2000);
            this.resetTime += 2000;
        } else {
            this.gameState = GAME_STATE.WINNER;
        }
    }

    /*
     * Handle the input
     * 
     * @param Map map
     * 
     * @return void
     */
    private void handleInput(Map map) {
        // Get the key codes
        final int[] keyCodes = this.getCurrentStage().getKeyCodes();

        // Handle the input depending on the game state

        // If the game state is playing
        if (this.gameState == GAME_STATE.PLAYING) {
            // Check if the right key is pressed
            if (StdDraw.isKeyPressed(keyCodes[0]) || keyCodes[0] == -1) map.movePlayer('R');

            // Check if the left key is pressed
            if (StdDraw.isKeyPressed(keyCodes[1]) || keyCodes[1] == -1) map.movePlayer('L');

            // Check if the up key is pressed
            if (StdDraw.isKeyPressed(keyCodes[2]) || keyCodes[2] == -1) map.movePlayer('U');

            // Check if the mouse is pressed, if mouse is not pressed, reset the state flags
            // This approach increases the performance by
            // reducing the number of checks and the number of if statements
            if (StdDraw.isMousePressed()) {
                // Check if the help button is pressed and the help pressed flag is not set
                if (isButtonPressed(this.HELP_BUTTON_COORDS) && !this.isHelpPressed) this.isHelpPressed = true;

                // Check if the restart button is pressed
                if (isButtonPressed(this.RESTART_BUTTON_COORDS)) this.restartStage(map);
                else this.isRestartRecentlyPressed = false;

                // Check if the reset game button is pressed
                if (isButtonPressed(this.RESET_GAME_BUTTON_COORDS)) this.resetGame = true;
            } else this.resetStateFlags();
        }

        // If the game state is winner
        if (this.gameState == GAME_STATE.WINNER) {
            // Check if the A key is pressed
            if (StdDraw.isKeyPressed(KeyEvent.VK_A)) this.resetGame = true;

            // Check if the Q key is pressed
            if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) System.exit(0);
        }
    }

    /*
     * Reset the state flags
     * 
     * @return void
     */
    private void resetStateFlags() {
        this.isRestartRecentlyPressed = false;
    }

    /*
     * Draw the banner
     * 
     * @param String[] lines
     * 
     * @param Font[] fonts
     * 
     * @param boolean showNow
     * 
     * @return void
     */
    private void drawBanner(String[] lines, Font[] fonts, boolean showNow) {
        final int bannerHeight = 200;
        final int bannerWidth = 800;
        // Draw banner rectangle
        StdDraw.setPenColor(new Color(56, 172, 10));
        StdDraw.filledRectangle(400, 300, bannerWidth / 2, bannerHeight / 2);

        Font tempFont = StdDraw.getFont();
        // Draw main text
        StdDraw.setPenColor(StdDraw.WHITE);

        // Execute the loop for each line
        for (int i = 0; i < lines.length; i++) {
            // Check if the font is not null
            if (fonts != null && i < fonts.length && fonts[i] != null) {
                // Set the font
                StdDraw.setFont(fonts[i]);
            }

            // Calculate spacing based on font size * 1.8
            int fontSize = StdDraw.getFont().getSize();
            int spacing = (int) (fontSize * 1.8);

            // Calculate the y position
            int yPosition = 300 + (int) (((lines.length - 1) / 2.0 - i) * spacing);

            // Draw the text
            StdDraw.text(400, yPosition, lines[i]);
        }

        // Set the font to the original font
        StdDraw.setFont(tempFont);

        // If the banner should be shown now, show it
        if (showNow) {
            StdDraw.show();
        }
    }

    /*
     * Draw the winner banner
     * 
     * @return void
     */
    private void drawWinnerBanner() {
        String[] lines = { "CONGRATULATIONS! YOU FINISHED THE GAME!", "PRESS A TO RESTART",
                "You finished the game with " + this.deathNumber + " deaths in " + this.formattedTime() };
        Font[] fonts = { this.GAME_FONTS[1], this.GAME_FONTS[1], this.GAME_FONTS[2] };

        // Call generic banner method
        drawBanner(lines, fonts, false);
    }

    /*
     * Draw the next stage banner
     * 
     * @return void
     */
    private void drawNextStageBanner() {
        String[] lines = { "You passed the stage!", "But is the level over?!" };
        Font[] fonts = { this.GAME_FONTS[1], this.GAME_FONTS[1] };

        drawBanner(lines, fonts, true);
    }

    /*
     * Draw the resetting banner
     * 
     * @return void
     */
    private void drawResettingBanner() {
        String[] lines = { "Resetting the game..." };
        Font[] fonts = { this.GAME_FONTS[1] };

        drawBanner(lines, fonts, true);
    }

    /*
     * Reset the game
     * 
     * @param Map map
     * 
     * @return void
     */
    private void resetGame(Map map) {
        // Reset the stage index, death number, game time, reset time, help pressed
        // flag, and game state
        this.stageIndex = 0;
        this.deathNumber = 0;
        this.gameTime = 0;
        this.resetTime = System.currentTimeMillis();
        this.isHelpPressed = false;
        this.gameState = GAME_STATE.PLAYING;

        // Set the stage
        map.setStage(this.getCurrentStage());

        // Restart the stage
        map.restartStage();

        // Call back the reset game flag
        this.resetGame = false;

        // Draw the resetting banner
        this.drawResettingBanner();

        // Pause for 2 seconds
        StdDraw.pause(2000);
        this.resetTime += 2000;
    }

    /*
     * Restart the stage
     * 
     * @param Map map
     * 
     * @return void
     */
    private void restartStage(Map map) {
        if (this.isRestartRecentlyPressed) return;

        // Restart the stage
        map.restartStage();

        // Increment the death number
        this.deathNumber++;

        // Set the restart recently pressed flag
        this.isRestartRecentlyPressed = true;
    }

    /*
     * Format the time from milliseconds to minutes, seconds, and milliseconds
     * 
     * @return String
     */
    private String formattedTime() {
        final long totalMilliseconds = Math.max(0, (long) this.gameTime);
        final long minutes = (totalMilliseconds / 60000);
        final long seconds = (totalMilliseconds % 60000) / 1000;
        final long milliseconds = (totalMilliseconds % 1000) / 10; // Divide by 10 to get two digits
        return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
    }

    /*
     * Get the stage index
     * 
     * @return int
     */
    public int getStageIndex() { return this.stageIndex; }

    /*
     * Get the current stage
     * 
     * @return Stage
     */
    public Stage getCurrentStage() { return this.stages.get(this.stageIndex); }

    /*
     * Generate a random color
     * 
     * @return Color
     */
    public static Color generateRandomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }
}
