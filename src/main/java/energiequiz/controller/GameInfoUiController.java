package energiequiz.controller;

import static energiequiz.model.Constants.GREEN_ENERGY_STRING;
import static energiequiz.model.Constants.ORANGE_ENERGY_STRING;
import static energiequiz.model.Constants.RED_ENERGY_STRING;
import static energiequiz.model.Constants.STARTING_ENERGY;

import energiequiz.model.Room;
import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for managing the game info UI.
 */

public class GameInfoUiController {
  @FXML
  ProgressBar energyBar;
  @FXML
  Label lblConsumption;
  @FXML
  Label lblEnergyPercent;
  @FXML
  Rectangle room1;
  @FXML
  Rectangle room2;
  @FXML
  Rectangle room3;
  @FXML
  Rectangle room4;
  @FXML
  Rectangle room5;
  @FXML
  ImageView imgCurrentRoom;
  @FXML
  Gauge gaugeEnergyConsumption;

  /**
   * Updates the energy bar and consumption label.
   *
   * @param remainingEnergy the remaining energy
   * @param consumption     the current consumption
   */

  public void updateEnergyBar(double remainingEnergy, double consumption) {
    double percent = remainingEnergy / STARTING_ENERGY;
    energyBar.setProgress(percent);
    //label updates need to run on the JavaFx thread
    Platform.runLater(() -> {
      lblEnergyPercent.setText((int) (percent * 100) + "%");
    });

    if (gaugeEnergyConsumption != null) {
      Platform.runLater(() -> {
        gaugeEnergyConsumption.setValue(consumption);
        //setForegroundBaseColor ist text schrift von consumption
        if (consumption >= 5 && consumption < 7) {
          gaugeEnergyConsumption.setForegroundBaseColor(Color.ORANGE);
        } else if (consumption >= 7) {
          gaugeEnergyConsumption.setForegroundBaseColor(Color.RED);
        } else {
          gaugeEnergyConsumption.setForegroundBaseColor(Color.GREEN);
        }
      });
    }

    if (percent > 0.5) {
      energyBar.setStyle("-fx-accent:" + GREEN_ENERGY_STRING);
    } else if (percent > 0.25) {
      energyBar.setStyle("-fx-accent:" + ORANGE_ENERGY_STRING);
    } else {
      energyBar.setStyle("-fx-accent:" + RED_ENERGY_STRING);
    }


  }


  /**
   * Updates the room display based on the completion status and current room.
   *
   * @param rooms       array of all rooms in the game.
   * @param currentRoom the current room the player is in.
   */

  public void updateRoomDisplay(Room[] rooms, Room currentRoom) {
    Rectangle[] roomRects = {room1, room2, room3, room4};
    for (int i = 0; i < roomRects.length; i++) {
      if (roomRects[i] != null) {
        if (rooms[i].isCompleted()) {
          roomRects[i].setFill(Color.color(0, 1, 0));
        } else {
          roomRects[i].setFill(Color.color(1, 0, 0));
        }
      }
      if (rooms[i].equals(currentRoom)) {
        imgCurrentRoom.setVisible(true);
        imgCurrentRoom.setLayoutY(555 - (i * 40));
      }

    }
  }

}
