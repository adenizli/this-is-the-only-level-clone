// name surname: Ahmet Sait Denizli
// student ID: 2023400309

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AhmetSaitDenizli {
        public static void main(String[] args) {
                int infinityButton = -1;
                int nullButton = -2;
                // Given Stages
                Stage s1 = new Stage(-22.5, 3.65, 600, 0, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                                "Arrow keys are required",
                                "Arrow keys move player ,press button and enter the second pipe");
                Stage s2 = new Stage(-22.5, 3.65, 600, 1, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
                                "Not always straight forward", "Right and left buttons reversed");

                Stage s3 = new Stage(-100, 6, 1200, 5, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, infinityButton,
                                "A bit bouncy here", "You jump constantly");

                Stage s4 = new Stage(-22.5, 3.65, 600, 13, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                                "Never gonna give you up", "Press button 5 times ");
                s4.setRequiredNumberOfPress(5);

                Stage s5 = new Stage(-22.5, 3.65, 600, 11, KeyEvent.VK_RIGHT, nullButton, KeyEvent.VK_UP,
                                "No returns, no refunds", "You can't go left!");

                // Add the stages to the arraylist
                var stages = new ArrayList<Stage>();
                stages.add(s1);
                stages.add(s2);
                stages.add(s3);
                stages.add(s4);
                stages.add(s5);

                Game game = new Game(stages);
                game.play();
        }

}
