package energiequiz.controller;

import static energiequiz.model.Constants.BLUE_BUTTON_COLOR;
import static energiequiz.model.Constants.GREEN_BUTTON_COLOR;
import static energiequiz.model.Constants.RED_BUTTON_COLOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import energiequiz.util.FxglTestUtil;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for the MainMenuController class.
 */
@ExtendWith(MockitoExtension.class)
public class MainMenuControllerTest {

  private MainMenuController controller = new MainMenuController();

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }

  @BeforeEach
  void setUp() {
    Platform.runLater(() -> {
      controller.rectStart = new Rectangle();
      controller.rectTutorial = new Rectangle();
      controller.rectLanguage = new Rectangle();
      controller.lblStart = new Label();
      controller.imgCharacter = new ImageView();

      controller.characterFilename1 = "player1.png";
      controller.characterFilename2 = "player2.png";
      controller.character1 = new Image("file:src/test/resources/assets/textures/player1.png");
      controller.character2 = new Image("file:src/test/resources/assets/textures/player2.png");
    });
  }

  @Test
  void testChangeStartButton() {
    Platform.runLater(() -> {
      controller.changeStartButton();
      assertEquals("Starting", controller.lblStart.getText());
      assertEquals(Color.color(0.5, 0.5, 0.5), controller.rectTutorial.getFill());
      assertEquals(Color.color(0.5, 0.5, 0.5), controller.rectLanguage.getFill());
    });
  }

  @Test
  void testResetButtons() {
    Platform.runLater(() -> {
      controller.resetButtons();
      assertEquals("Start", controller.lblStart.getText());
      assertEquals(RED_BUTTON_COLOR, controller.rectStart.getFill());
      assertEquals(GREEN_BUTTON_COLOR, controller.rectTutorial.getFill());
      assertEquals(BLUE_BUTTON_COLOR, controller.rectLanguage.getFill());
    });
  }

  @Test
  void testChangeImageRight() {
    Platform.runLater(() -> {
      controller.changeImage(true);
      assertEquals(controller.character1, controller.imgCharacter.getImage());

      controller.changeImage(true);
      assertEquals(controller.character2, controller.imgCharacter.getImage());
    });
  }

  @Test
  void testChangeImageLeft() {
    Platform.runLater(() -> {
      controller.changeImage(false);
      assertEquals(controller.character2, controller.imgCharacter.getImage());

      controller.changeImage(false);
      assertEquals(controller.character1, controller.imgCharacter.getImage());
    });
  }

  @Test
  void testSelectedCharacterFilename() {
    Platform.runLater(() -> {
      controller.selectedCharacter = 5;
      assertEquals("player1.png", controller.selectedCharacterFilename());

      controller.selectedCharacter = 15;
      assertEquals("player2.png", controller.selectedCharacterFilename());
    });
  }
}
