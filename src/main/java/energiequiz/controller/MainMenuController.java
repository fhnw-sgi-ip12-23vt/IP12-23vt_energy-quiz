package energiequiz.controller;

import static energiequiz.model.Constants.BLUE_BUTTON_COLOR;
import static energiequiz.model.Constants.GREEN_BUTTON_COLOR;
import static energiequiz.model.Constants.RED_BUTTON_COLOR;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for managing the main menu UI.
 */

public class MainMenuController {

  @FXML
  Rectangle rectStart;
  @FXML
  Rectangle rectTutorial;
  @FXML
  Rectangle rectLanguage;
  @FXML
  Label lblStart;
  @FXML
  ImageView imgCharacter;

  private static final int MAX_CHAR = 2;
  String characterFilename1 = "player1.png";
  String characterFilename2 = "player2.png";
  String relativePath = "src/main/resources/assets/textures/";
  Image character1 = new Image("file:" + relativePath + characterFilename1);
  Image character2 = new Image("file:" + relativePath + characterFilename2);
  int selectedCharacter = 0;

  /**
   * Changes the appearance of the start button and associated UI elements. Sets the label text to
   * "Starting" and adjusts the colors of tutorial and language buttons.
   */
  public void changeStartButton() {
    Platform.runLater(() -> lblStart.setText("Starting"));
    rectTutorial.setFill(Color.color(0.5, 0.5, 0.5));
    rectLanguage.setFill(Color.color(0.5, 0.5, 0.5));
  }

  /**
   * Resets the appearance of all buttons to their default state. Sets the label text back to
   * "Start" and resets the colors of all buttons.
   */
  public void resetButtons() {
    Platform.runLater(() -> lblStart.setText("Start"));
    rectStart.setFill(RED_BUTTON_COLOR);
    rectTutorial.setFill(GREEN_BUTTON_COLOR);
    rectLanguage.setFill(BLUE_BUTTON_COLOR);
  }

  /**
   * Changes the displayed character image based on the direction specified.
   *
   * @param right true to move to the next character image, false to move to the previous character
   *              image.
   */
  public void changeImage(boolean right) {
    if (right) {
      selectedCharacter++;
    } else {
      selectedCharacter--;
    }
    if (selectedCharacter <= 0) {
      selectedCharacter = MAX_CHAR * 10;
    }
    if (selectedCharacter > MAX_CHAR * 10) {
      selectedCharacter = 0;
    }

    if (selectedCharacter <= 10) {
      imgCharacter.setImage(character1);
    } else {
      imgCharacter.setImage(character2);
    }

  }

  /**
   * Retrieves the filename of the currently selected character image.
   *
   * @return the filename of the selected character image.
   */

  public String selectedCharacterFilename() {
    if (selectedCharacter <= 10) {
      return characterFilename1;
    } else {
      return characterFilename2;
    }
  }
}
