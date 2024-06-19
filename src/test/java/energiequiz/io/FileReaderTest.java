package energiequiz.io;

import static energiequiz.model.Constants.BASE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import energiequiz.model.LeaderboardEntry;
import energiequiz.model.Question;
import energiequiz.model.QuestionType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

/**
 *tests for the FileReader class.
 */
public class FileReaderTest {

  private static final String TEST_JSON_PATH =
      BASE_PATH + File.separator + "src" + File.separator + "test" + File.separator
          + "resources" + File.separator + "json" + File.separator;

  private static final Path TEST_NAMES_PATH =
      Path.of(BASE_PATH + "/src/test/resources/csv/names.csv");

  public static final Path TEST_SCORE_PATH =
      Path.of(BASE_PATH + "/src/test/resources/csv/score.csv");

  @Test
  public void testReadQuestions() {
    FileReader fileReader = new FileReader();
    List<Question> list = fileReader.readQuestions(TEST_JSON_PATH, Locale.GERMAN);
    assert (list.size() == 3);
  }

  @Test
  public void testReadQuestionValidity() {
    FileReader fileReader = new FileReader();
    List<Question> list = fileReader.readQuestions(TEST_JSON_PATH, Locale.GERMAN);
    Question q1 = list.get(0);
    assert (q1.getType() == QuestionType.Choice);
    assert (q1.getQuestionText().equals("Question1"));
    List<String> q1a = q1.getAnswers();
    assert (q1a.get(0).equals("Answer 1"));
    assert (q1a.get(1).equals("Answer 2"));
    assert (q1a.get(2).equals("Answer 3"));
    assert (q1.checkAnswer("3"));
    var is1Correct = q1.checkAnswer("3");
    assert (q1.getExplanation("3", is1Correct).equals("exp3"));

    Question q2 = list.get(1);
    assert (q2.getType() == QuestionType.Sorting);
    assert (q2.getQuestionText().equals("Question2"));
    List<String> q2a = q2.getAnswers();
    assert (q2a.get(0).equals("Answer 1"));
    assert (q2a.get(1).equals("Answer 2"));
    assert (q2a.get(2).equals("Answer 3"));
    assert (q2.checkAnswer("123"));
    var is2Correct = q2.checkAnswer("123");
    assert (q2.getExplanation("123", is2Correct).equals("Explanation"));

    Question q3 = list.get(2);
    assert (q3.getType() == QuestionType.Choice);
    assert (q3.getQuestionText().equals("Question3"));
    List<String> q3a = q3.getAnswers();
    assert (q3a.get(0).equals("Answer 1"));
    assert (q3a.get(1).equals("Answer 2"));
    assert (q3a.get(2).equals("Answer 3"));
    assert (q3.checkAnswer("1"));
    var is3Correct = q3.checkAnswer("1");
    assert (q3.getExplanation("1", is3Correct).equals("exp1"));
  }

  @Test
  void testReadScoreCsv() {
    try {
      List<LeaderboardEntry> scores = FileReader.readScoreCsv(TEST_SCORE_PATH);
      assertEquals(3, scores.size());

      assertEquals("Thunderhawks", scores.get(0).getTeamName());
      assertEquals("5000", scores.get(0).getPercentage());

      assertEquals("Spartan Legends", scores.get(1).getTeamName());
      assertEquals("3000", scores.get(1).getPercentage());

      assertEquals("Stealth Stingers", scores.get(2).getTeamName());
      assertEquals("1000", scores.get(2).getPercentage());
    } catch (IOException e) {
      fail("IOException occurred: " + e.getMessage());
    }
  }

  @Test
  void testReadNamesCsv() {
    try {
      List<String> names = FileReader.readNamesCsv(TEST_NAMES_PATH);
      assertEquals(3, names.size());

      assertEquals("Thunderhawks", names.get(0));

      assertEquals("Spartan Legends", names.get(1));

      assertEquals("Stealth Stingers", names.get(2));
    } catch (IOException e) {
      fail("IOException occurred: " + e.getMessage());
    }
  }
}
