package energiequiz.controller;

import static energiequiz.model.Constants.BLUE_BUTTON_COLOR;
import static energiequiz.model.Constants.GREEN_BUTTON_COLOR;
import static energiequiz.model.Constants.RED_BUTTON_COLOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import energiequiz.model.Question;
import energiequiz.model.QuestionType;
import energiequiz.util.FxglTestUtil;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the QuizUiController class.
 */
public class QuizUiControllerTest {

  private QuizUiController controller;

  private ResourceBundle resourceBundle;

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }

  @BeforeEach
  void setUp() {
    resourceBundle = ResourceBundle.getBundle("strings/bundle", Locale.ENGLISH);
    controller = new QuizUiController(resourceBundle);

    Platform.runLater(() -> {
      controller.question = new Label();
      controller.answer1 = new Label();
      controller.answer2 = new Label();
      controller.answer3 = new Label();
      controller.lblExplanation = new Label();
      controller.rectAnswer1 = new Rectangle();
      controller.rectAnswer2 = new Rectangle();
      controller.rectAnswer3 = new Rectangle();
      controller.circle1 = new Circle();
      controller.circle2 = new Circle();
      controller.circle3 = new Circle();
      controller.imgGreenButton = new ImageView();
      controller.imgRedButton = new ImageView();
      controller.imgBlueButton = new ImageView();
      controller.imgCorrect = new ImageView();
      controller.imgFalse = new ImageView();
    });
  }

  @Test
  void testSetQuiz() {
    Platform.runLater(() -> {
      Question q =
          new Question("Test Question", List.of("Explanation1", "Explanation2", "Explanation3"),
              List.of("Answer1", "Answer2", "Answer3"), "1", QuestionType.Choice);

      controller.setQuiz(q);

      assertTrue(controller.imgCorrect.isVisible());
      assertTrue(controller.imgFalse.isVisible());
      assertFalse(controller.lblExplanation.isVisible());

      assertTrue(controller.rectAnswer1.isVisible());
      assertTrue(controller.rectAnswer2.isVisible());
      assertTrue(controller.rectAnswer3.isVisible());
      assertTrue(controller.question.isVisible());
      assertTrue(controller.circle1.isVisible());
      assertTrue(controller.circle2.isVisible());
      assertTrue(controller.circle3.isVisible());
      assertTrue(controller.imgBlueButton.isVisible());
      assertTrue(controller.imgGreenButton.isVisible());
      assertTrue(controller.imgRedButton.isVisible());
      assertTrue(controller.answer1.isVisible());
      assertTrue(controller.answer2.isVisible());
      assertTrue(controller.answer3.isVisible());

      assertEquals("Test Question", controller.question.getText());
      assertEquals("Answer1", controller.answer1.getText());
      assertEquals("Answer2", controller.answer2.getText());
      assertEquals("Answer3", controller.answer3.getText());
    });
  }

  @Test
  void testShowExplanationCorrect() {
    Platform.runLater(() -> {
      controller.showExplanation("Correct Answer", true, "1");

      assertFalse(controller.rectAnswer1.isVisible());
      assertFalse(controller.rectAnswer2.isVisible());
      assertFalse(controller.rectAnswer3.isVisible());
      assertFalse(controller.question.isVisible());
      assertFalse(controller.circle1.isVisible());
      assertFalse(controller.circle2.isVisible());
      assertFalse(controller.circle3.isVisible());
      assertFalse(controller.imgBlueButton.isVisible());
      assertFalse(controller.imgGreenButton.isVisible());
      assertFalse(controller.imgRedButton.isVisible());
      assertFalse(controller.answer1.isVisible());
      assertFalse(controller.answer2.isVisible());
      assertFalse(controller.answer3.isVisible());

      assertTrue(controller.imgCorrect.isVisible());
      assertFalse(controller.imgFalse.isVisible());
      assertTrue(controller.lblExplanation.isVisible());
      assertEquals("Correct Answer", controller.lblExplanation.getText());
    });
  }

  @Test
  void testShowExplanationIncorrect() {
    Platform.runLater(() -> {
      controller.showExplanation("Incorrect Answer", false, "1");

      assertFalse(controller.rectAnswer1.isVisible());
      assertFalse(controller.rectAnswer2.isVisible());
      assertFalse(controller.rectAnswer3.isVisible());
      assertFalse(controller.question.isVisible());
      assertFalse(controller.circle1.isVisible());
      assertFalse(controller.circle2.isVisible());
      assertFalse(controller.circle3.isVisible());
      assertFalse(controller.imgBlueButton.isVisible());
      assertFalse(controller.imgGreenButton.isVisible());
      assertFalse(controller.imgRedButton.isVisible());
      assertFalse(controller.answer1.isVisible());
      assertFalse(controller.answer2.isVisible());
      assertFalse(controller.answer3.isVisible());

      assertFalse(controller.imgCorrect.isVisible());
      assertTrue(controller.imgFalse.isVisible());
      assertTrue(controller.lblExplanation.isVisible());
      assertEquals("Incorrect Answer", controller.lblExplanation.getText());
    });
  }

  @Test
  void testShowButtonPress() {
    Platform.runLater(() -> {
      controller.showButtonPress("123");

      assertEquals(GREEN_BUTTON_COLOR, controller.circle1.getFill());
      assertEquals(BLUE_BUTTON_COLOR, controller.circle2.getFill());
      assertEquals(RED_BUTTON_COLOR, controller.circle3.getFill());
    });
  }
}
