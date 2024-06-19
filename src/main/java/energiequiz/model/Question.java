package energiequiz.model;

import java.util.List;

/**
 * Represents a question within the game.
 */
public class Question {
  private String questionText;
  private List<String> explanations;
  private List<String> answers;
  private String correctAnswer;
  private QuestionType type;

  /**
   * Constructs a question with the specified attributes.
   *
   * @param questionText  the text of the question
   * @param explanations   additional information about the question
   * @param answers       list of possible answers
   * @param correctAnswer the correct answer
   * @param type          the type of question
   */
  public Question(String questionText, List<String> explanations, List<String> answers,
                  String correctAnswer, QuestionType type) {
    this.questionText = questionText;
    this.explanations = explanations;
    this.answers = answers;
    this.correctAnswer = correctAnswer;
    this.type = type;
  }

  public String getQuestionText() {
    return questionText;
  }

  /**
   * returns the Explanation.
   *
   * @param answer  the selected answer
   * @param isAnsCorrect   was the answer correct
   */
  public String getExplanation(String answer, boolean isAnsCorrect) {
    int i = Integer.parseInt(answer);
    if (i > 3) {
      if (!isAnsCorrect) {
        var firstWrong = firstOutOfOrder("" + i);
        return explanations.get(firstWrong);
      } else {
        return explanations.get(3);
      }
    } else {
      return explanations.get(i - 1);
    }
  }

  private int firstOutOfOrder(String answer) {
    int i = 0;
    while (i < answer.length() && answer.charAt(i) == this.correctAnswer.charAt(i)) {
      i++;
    }
    return i;
  }

  public String getAnswer(int i) {
    return answers.get(i);
  }

  public List<String> getAnswers() {
    return answers;
  }

  public QuestionType getType() {
    return type;
  }

  public boolean checkAnswer(String answer) {
    return answer.equals(correctAnswer);
  }
}
