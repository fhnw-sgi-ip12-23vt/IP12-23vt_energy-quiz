package energiequiz.controller;

import static energiequiz.model.Constants.BLUE_BUTTON_COLOR;
import static energiequiz.model.Constants.GREEN_BUTTON_COLOR;
import static energiequiz.model.Constants.RED_BUTTON_COLOR;

import energiequiz.model.Question;
import energiequiz.model.QuestionType;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for managing the quiz UI.
 */
public class QuizUiController {

  @FXML
  Label question;
  @FXML
  Label answer1;
  @FXML
  Label answer2;
  @FXML
  Label answer3;
  @FXML
  Label lblExplanation;
  @FXML
  Rectangle rectAnswer1;
  @FXML
  Rectangle rectAnswer2;
  @FXML
  Rectangle rectAnswer3;
  @FXML
  Circle circle1;
  @FXML
  Circle circle2;
  @FXML
  Circle circle3;
  @FXML
  ImageView imgGreenButton;
  @FXML
  ImageView imgRedButton;
  @FXML
  ImageView imgBlueButton;
  @FXML
  ImageView imgCorrect;
  @FXML
  ImageView imgFalse;

  private final String relativePath = "src/main/resources/ui/";

  private final String fileNameCorrectDe = "imgCorrectDe.png";
  private final String fileNameCorrectFr = "imgCorrectFr.png";
  private final String fileNameCorrectEn = "imgCorrectEn.png";

  private final String fileNameFalseDe = "imgFalseDe.png";
  private final String fileNameFalseFr = "imgFalseFr.png";
  private final String fileNameFalseEn = "imgFalseEn.png";

  private final Image imgCorrectDe = new Image("file:" + relativePath + fileNameCorrectDe);
  private final Image imgCorrectEn = new Image("file:" + relativePath + fileNameCorrectEn);
  private final Image imgCorrectFr = new Image("file:" + relativePath + fileNameCorrectFr);

  private final Image imgFalseDe = new Image("file:" + relativePath + fileNameFalseDe);
  private final Image imgFalseEn = new Image("file:" + relativePath + fileNameFalseEn);
  private final Image imgFalseFr = new Image("file:" + relativePath + fileNameFalseFr);


  private ResourceBundle resourceBundle;

  public QuizUiController(ResourceBundle resourceBundle) {
    this.resourceBundle = resourceBundle;
  }

  /**
   * Sets the quiz question and answer choices on the UI.
   *
   * @param q The Question object containing the quiz question and answer choices.
   */
  public void setQuiz(Question q) {
    imgCorrect.setVisible(false);
    imgFalse.setVisible(false);
    lblExplanation.setVisible(false);

    rectAnswer1.setVisible(true);
    rectAnswer2.setVisible(true);
    rectAnswer3.setVisible(true);
    question.setVisible(true);
    circle1.setVisible(true);
    circle2.setVisible(true);
    circle3.setVisible(true);
    imgBlueButton.setVisible(true);
    imgGreenButton.setVisible(true);
    imgRedButton.setVisible(true);
    answer1.setVisible(true);
    answer2.setVisible(true);
    answer3.setVisible(true);

    question.setText(q.getQuestionText());
    answer1.setText(q.getAnswer(0));
    answer2.setText(q.getAnswer(1));
    answer3.setText(q.getAnswer(2));
    if (q.getType() == QuestionType.Sorting) {
      circle1.setVisible(true);
      circle2.setVisible(true);
      circle3.setVisible(true);
      circle1.setFill(Color.color(1, 1, 1));
      circle2.setFill(Color.color(1, 1, 1));
      circle3.setFill(Color.color(1, 1, 1));
    } else {
      circle1.setVisible(false);
      circle2.setVisible(false);
      circle3.setVisible(false);
    }
  }

  /**
   * Displays the explanation and feedback after a user selects an answer.
   *
   * @param explanation The explanation text to display.
   * @param correct     true if the selected answer is correct, false otherwise.
   * @param answer      The index of the selected answer (as a String).
   */

  public void showExplanation(String explanation, boolean correct, String answer) {

    rectAnswer1.setVisible(false);
    rectAnswer2.setVisible(false);
    rectAnswer3.setVisible(false);
    question.setVisible(false);
    circle1.setVisible(false);
    circle2.setVisible(false);
    circle3.setVisible(false);
    imgBlueButton.setVisible(false);
    imgGreenButton.setVisible(false);
    imgRedButton.setVisible(false);
    answer1.setVisible(false);
    answer2.setVisible(false);
    answer3.setVisible(false);

    if (correct) {
      switch (resourceBundle.getLocale().getLanguage()) {
        case "de" -> imgCorrect.setImage(imgCorrectDe);
        case "en" -> imgCorrect.setImage(imgCorrectEn);
        case "fr" -> imgCorrect.setImage(imgCorrectFr);
        default ->
            throw new IllegalStateException("Unexpected value: " + resourceBundle.getLocale());

      }
      imgCorrect.setVisible(true);
    } else {
      switch (resourceBundle.getLocale().getLanguage()) {
        case "de" -> imgFalse.setImage(imgFalseDe);
        case "en" -> imgFalse.setImage(imgFalseEn);
        case "fr" -> imgFalse.setImage(imgFalseFr);
        default ->
            throw new IllegalStateException("Unexpected value: " + resourceBundle.getLocale());
      }

      imgFalse.setVisible(true);
    }


    lblExplanation.setVisible(true);
    lblExplanation.setText(explanation);
  }

  /**
   * Highlights the selected answer buttons based on the specified button order.
   *
   * @param buttonOrder A String representing the order of selected answer buttons (e.g., "123").
   */

  public void showButtonPress(String buttonOrder) {
    Circle[] circles = {circle1, circle2, circle3};
    for (int i = 0; i < buttonOrder.length(); i++) {
      switch (buttonOrder.charAt(i)) {
        case '1' -> {
          circles[i].setFill(GREEN_BUTTON_COLOR);
        }
        case '2' -> {
          circles[i].setFill(BLUE_BUTTON_COLOR);
        }
        case '3' -> {
          circles[i].setFill(RED_BUTTON_COLOR);
        }
        default -> throw new IllegalStateException("Unexpected value: " + buttonOrder.charAt(i));
      }
    }
  }

}
