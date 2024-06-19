package energiequiz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import energiequiz.util.FxglTestUtil;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * a class to test questions.
 */
public class QuestionTest {

  private Question sortingQuestion;
  private Question choiceQuestion;

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }

  @BeforeEach
  void setUp() {
    List<String> sortingExplanations =
        Arrays.asList("Explanation 1", "Explanation 2", "Explanation 3", "General Explanation");
    List<String> sortingAnswers = Arrays.asList("Answer 1", "Answer 2", "Answer 3");
    sortingQuestion =
        new Question("Sort these in order.", sortingExplanations, sortingAnswers, "123",
            QuestionType.Sorting);

    List<String> choiceExplanations =
        Arrays.asList("Explanation 1", "Explanation 2", "Explanation 3", "General Explanation");
    List<String> choiceAnswers = Arrays.asList("Choice 1", "Choice 2", "Choice 3");
    choiceQuestion =
        new Question("Choose the correct option.", choiceExplanations, choiceAnswers, "1",
            QuestionType.Choice);
  }

  @Test
  void testSortingQuestion_GetQuestionText() {
    assertEquals("Sort these in order.", sortingQuestion.getQuestionText());
  }

  @Test
  void testChoiceQuestion_GetQuestionText() {
    assertEquals("Choose the correct option.", choiceQuestion.getQuestionText());
  }

  @Test
  void testSortingQuestion_GetExplanation_CorrectAnswer() {
    String explanation = sortingQuestion.getExplanation("123", true);
    assertEquals("General Explanation", explanation);
  }

  @Test
  void testSortingQuestion_GetExplanation_IncorrectAnswer() {
    String explanation = sortingQuestion.getExplanation("321", false);
    assertEquals("Explanation 1", explanation);
  }

  @Test
  void testSortingQuestion_GetExplanation_PartiallyCorrectAnswer() {
    String explanation = sortingQuestion.getExplanation("132", false);
    assertEquals("Explanation 2", explanation);  // First out-of-order character
  }

  @Test
  void testChoiceQuestion_GetExplanation_CorrectAnswer() {
    String explanation = choiceQuestion.getExplanation("1", true);
    assertEquals("Explanation 1", explanation);
  }

  @Test
  void testChoiceQuestion_GetExplanation_IncorrectAnswer() {
    String explanation = choiceQuestion.getExplanation("2", false);
    assertEquals("Explanation 2", explanation);
  }

  @Test
  void testSortingQuestion_GetAnswer() {
    assertEquals("Answer 1", sortingQuestion.getAnswer(0));
    assertEquals("Answer 2", sortingQuestion.getAnswer(1));
    assertEquals("Answer 3", sortingQuestion.getAnswer(2));
  }

  @Test
  void testChoiceQuestion_GetAnswer() {
    assertEquals("Choice 1", choiceQuestion.getAnswer(0));
    assertEquals("Choice 2", choiceQuestion.getAnswer(1));
    assertEquals("Choice 3", choiceQuestion.getAnswer(2));
  }

  @Test
  void testSortingQuestion_GetAnswers() {
    List<String> answers = sortingQuestion.getAnswers();
    assertEquals(3, answers.size());
    assertEquals("Answer 1", answers.get(0));
    assertEquals("Answer 2", answers.get(1));
    assertEquals("Answer 3", answers.get(2));
  }

  @Test
  void testChoiceQuestion_GetAnswers() {
    List<String> answers = choiceQuestion.getAnswers();
    assertEquals(3, answers.size());
    assertEquals("Choice 1", answers.get(0));
    assertEquals("Choice 2", answers.get(1));
    assertEquals("Choice 3", answers.get(2));
  }

  @Test
  void testSortingQuestion_GetType() {
    assertEquals(QuestionType.Sorting, sortingQuestion.getType());
  }

  @Test
  void testChoiceQuestion_GetType() {
    assertEquals(QuestionType.Choice, choiceQuestion.getType());
  }

  @Test
  void testSortingQuestion_CheckAnswer_Correct() {
    assertTrue(sortingQuestion.checkAnswer("123"));
  }

  @Test
  void testSortingQuestion_CheckAnswer_Incorrect() {
    assertFalse(sortingQuestion.checkAnswer("321"));
  }

  @Test
  void testSortingQuestion_CheckAnswer_PartiallyCorrect() {
    assertFalse(sortingQuestion.checkAnswer("132"));
  }

  @Test
  void testChoiceQuestion_CheckAnswer_Correct() {
    assertTrue(choiceQuestion.checkAnswer("1"));
  }

  @Test
  void testChoiceQuestion_CheckAnswer_Incorrect() {
    assertFalse(choiceQuestion.checkAnswer("2"));
  }
}
