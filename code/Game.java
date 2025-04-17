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

    private GAME_STATE gameState;
    private int stageIndex;
    private ArrayList<Stage> stages;
    private int deathNumber;
    private double gameTime;
    private double resetTime;
    private boolean resetGame;
    private boolean isHelpPressed;
    private boolean isRestartRecentlyPressed;

    private final int[] HELP_BUTTON_COORDS = new int[] { 210, 75, 290, 105 };
    private final int[] RESTART_BUTTON_COORDS = new int[] { 510, 75, 590, 105 };
    private final int[] RESET_GAME_BUTTON_COORDS = new int[] { 320, 5, 480, 35 };
    private final int[] INFO_SECTION_COORDS = new int[] { 0, 0, 800, 120 };

    private final Color INFO_SECTION_COLOR = new Color(56, 93, 172);
    private final Font[] GAME_FONTS = new Font[] { new Font("Arial", Font.PLAIN, 16), new Font("Arial", Font.BOLD, 24),
            new Font("Arial", Font.PLAIN, 18) };

    public static final int GAME_FPS = 60;

    public Game(ArrayList<Stage> stages) {
        this.stages = stages;
        this.stageIndex = 0;
        this.deathNumber = 0;
        this.gameTime = 0;
        this.resetGame = false;
        this.gameState = GAME_STATE.INIT;
        this.isHelpPressed = false;
        this.resetTime = System.currentTimeMillis();

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
        this.gameState = GAME_STATE.PLAYING;

        while (true) {
            StdDraw.clear();
            if (this.resetGame) this.resetGame(map);

            handleInput(map);

            switch (this.gameState) {
                case GAME_STATE.PLAYING -> {
                    map.draw();
                    this.renderInfoBar();
                    this.gameTime = System.currentTimeMillis() - this.resetTime;

                    map.mapCycle();

                    if (map.changeStage()) this.setNextStage(map);
                    if (map.getResetStage()) this.restartStage(map);
                }
                case GAME_STATE.WINNER -> this.drawWinnerBanner();
            }

            StdDraw.show(1000 / GAME_FPS);
        }
    }

    /*
     * Render the info bar
     * 
     * @return void
     */
    private void renderInfoBar() {
        StdDraw.setPenColor(this.INFO_SECTION_COLOR); // Color of the area

        Map.drawRectangleByCoordinates(INFO_SECTION_COORDS, this.INFO_SECTION_COLOR, true);
        StdDraw.setPenColor(StdDraw.WHITE);

        Map.drawButton("Help", this.HELP_BUTTON_COORDS, StdDraw.WHITE);
        Map.drawButton("Restart", this.RESTART_BUTTON_COORDS, StdDraw.WHITE);
        Map.drawButton("Reset Game", this.RESET_GAME_BUTTON_COORDS, StdDraw.WHITE);

        StdDraw.text(700, 75, "Deaths: " + this.deathNumber);
        StdDraw.text(700, 50, "Stage: " + (this.stageIndex + 1));

        StdDraw.text(100, 50, this.formattedTime());
        StdDraw.text(100, 75, "Level: 1");

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
        double[] mouseCoords = new double[] { StdDraw.mouseX(), StdDraw.mouseY() };

        boolean isXinRange = mouseCoords[0] >= buttonCoords[0] && mouseCoords[0] <= buttonCoords[2];
        boolean isYinRange = mouseCoords[1] >= buttonCoords[1] && mouseCoords[1] <= buttonCoords[3];
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
        boolean stageIndexInRange = this.stageIndex < this.stages.size() - 1;
        if (stageIndexInRange) {
            this.stageIndex++;
            map.setStage(this.getCurrentStage());
            this.isHelpPressed = false;
            this.drawNextStageBanner();
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
        final int[] keyCodes = this.getCurrentStage().getKeyCodes();

        if (this.gameState == GAME_STATE.PLAYING) {
            if (StdDraw.isKeyPressed(keyCodes[0]) || keyCodes[0] == -1) map.movePlayer('R');

            if (StdDraw.isKeyPressed(keyCodes[1]) || keyCodes[1] == -1) map.movePlayer('L');

            if (StdDraw.isKeyPressed(keyCodes[2]) || keyCodes[2] == -1) map.movePlayer('U');

            if (StdDraw.isMousePressed()) {
                if (isButtonPressed(this.HELP_BUTTON_COORDS) && !this.isHelpPressed) this.isHelpPressed = true;

                if (isButtonPressed(this.RESTART_BUTTON_COORDS)) this.restartStage(map);
                else this.isRestartRecentlyPressed = false;

                if (isButtonPressed(this.RESET_GAME_BUTTON_COORDS)) this.resetGame = true;
            } else this.resetStateFlags();
        }

        if (this.gameState == GAME_STATE.WINNER) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_A)) this.resetGame = true;
            if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) System.exit(0);
        }
    }

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

        for (int i = 0; i < lines.length; i++) {
            if (fonts != null && i < fonts.length && fonts[i] != null) {
                StdDraw.setFont(fonts[i]);
            }
            // Calculate spacing based on font size * 1.8
            int fontSize = StdDraw.getFont().getSize();
            int spacing = (int) (fontSize * 1.8);
            int yPosition = 300 + (int) (((lines.length - 1) / 2.0 - i) * spacing);
            StdDraw.text(400, yPosition, lines[i]);
        }

        StdDraw.setFont(tempFont);

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
     * Reset the game
     * 
     * @param Map map
     * 
     * @return void
     */
    private void resetGame(Map map) {
        this.stageIndex = 0;
        this.deathNumber = 0;
        this.gameTime = 0;
        this.resetTime = System.currentTimeMillis();
        this.isHelpPressed = false;
        this.gameState = GAME_STATE.PLAYING;
        map.setStage(this.getCurrentStage());
        map.restartStage();
        this.resetGame = false;
    }

    /*
     * Restart the stage
     * 
     * @param Map map
     * 
     * @return void
     */
    private void restartStage(Map map) {
        System.out.println(this.isRestartRecentlyPressed);
        if (this.isRestartRecentlyPressed) return;
        map.restartStage();
        this.deathNumber++;
        this.isRestartRecentlyPressed = true;
    }

    /*
     * Format the time
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

    public static Color generateRandomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }
}
