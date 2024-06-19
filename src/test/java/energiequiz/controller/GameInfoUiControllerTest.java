package energiequiz.controller;

import static energiequiz.model.Constants.ORANGE_ENERGY_STRING;
import static energiequiz.model.Constants.STARTING_ENERGY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import energiequiz.model.Room;
import energiequiz.util.FxglTestUtil;
import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * a class to test the GameInfoUiController.
 */
public class GameInfoUiControllerTest {

  private GameInfoUiController controller = new GameInfoUiController();

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }


  @BeforeEach
  void setUp() {
    // Initialize components in the controller
    Platform.runLater(() -> {
      controller.energyBar = new ProgressBar();
      controller.lblEnergyPercent = new Label();
      controller.gaugeEnergyConsumption = new Gauge();
      controller.room1 = new Rectangle();
      controller.room2 = new Rectangle();
      controller.room3 = new Rectangle();
      controller.room4 = new Rectangle();
      controller.room5 = new Rectangle();
      controller.imgCurrentRoom = new ImageView();
    });

  }

  @Test
  void testUpdateEnergyBar() {
    Platform.runLater(() -> {
      double remainingEnergy = STARTING_ENERGY / 2;
      controller.updateEnergyBar(remainingEnergy, 60);
      double percent = remainingEnergy / (double) STARTING_ENERGY;
      assertEquals(percent, controller.energyBar.getProgress(), 0.01);
      assertEquals((int) (percent * 100) + "%", controller.lblEnergyPercent.getText());
      assertEquals(60, controller.gaugeEnergyConsumption.getValue());
      assertEquals("-fx-accent:" + ORANGE_ENERGY_STRING, controller.energyBar.getStyle());
    });
  }

  @Test
  void testUpdateRoomDisplay() {
    Platform.runLater(() -> {
      Room[] rooms = new Room[5];
      for (int i = 0; i < 5; i++) {
        rooms[i] = new Room("test", "test");
        rooms[i].setCompleted(i % 2 == 0);
      }
      Room currentRoom = rooms[2];

      controller.updateRoomDisplay(rooms, currentRoom);

      assertEquals(Color.color(0, 1, 0), controller.room1.getFill());
      assertEquals(Color.color(1, 0, 0), controller.room2.getFill());
      assertEquals(Color.color(0, 1, 0), controller.room3.getFill());
      assertEquals(Color.color(1, 0, 0), controller.room4.getFill());
      assertEquals(Color.color(0, 1, 0), controller.room5.getFill());
      assertEquals(605 - (2 * 40), controller.imgCurrentRoom.getLayoutY());
    });
  }
}
