package energiequiz.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import energiequiz.util.FxglTestUtil;
import java.util.List;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * a class to test room creation.
 */
@ExtendWith(MockitoExtension.class)
class RoomTest {

  private Room room;
  private Question question1;
  private Question question2;
  private Device device1;
  private Device device2;

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }

  @BeforeEach
  void setUp() {
    Platform.runLater(() -> {
      room = new Room("Test Room", "test_background.png");
      question1 = new Question(
          "Sorting Question",
          List.of("Explanation 1", "Explanation 2", "Explanation 3", "Explanation"),
          List.of("1", "2", "3"),
          "123",
          QuestionType.Sorting
      );

      question2 = new Question(
          "Choice Question",
          List.of("Explanation 1", "Explanation 2", "Explanation 3", "Explanation"),
          List.of("Answer 1", "Answer 2", "Answer 3"),
          "1",
          QuestionType.Choice
      );
      device1 = new Device("device1.png", 100, 100, 50, 50, 10);
      device2 = new Device("device2.png", 200, 200, 50, 50, 15);
    });

  }

  @Test
  void testGetName() {
    Platform.runLater(() -> {
      assertEquals("Test Room", room.getName());
    });

  }

  @Test
  void testGetBackground() {
    Platform.runLater(() -> {
      assertEquals("test_background.png", room.getBackground());
    });

  }

  @Test
  void testSetAndGetPosition() {
    Platform.runLater(() -> {
      room.setPosition(5);
      assertEquals(5, room.getPosition());
    });

  }

  @Test
  void testSetAndGetQuestion() {
    Platform.runLater(() -> {
      room.setQuestion(question1);
      assertEquals(question1, room.getQuestion());

      room.setQuestion(question2);
      assertEquals(question2, room.getQuestion());
    });

  }

  @Test
  void testSetAndGetAssets() {
    Platform.runLater(() -> {
      Asset[] assets = {device1, device2};
      room.setAssets(assets);
      assertArrayEquals(assets, room.getAssets());
    });

  }

  @Test
  void testCheckCompleted_AllDevicesCompleted() {
    Platform.runLater(() -> {
      device1.setCompleted(true);
      device2.setCompleted(true);

      room.setAssets(new Asset[]{device1, device2});
      room.checkCompleted();
      assertTrue(room.isCompleted());
    });

  }

  @Test
  void testCheckCompleted_SomeDevicesNotCompleted() {
    Platform.runLater(() -> {
      device1.setCompleted(true);
      device2.setCompleted(false);

      room.setAssets(new Asset[]{device1, device2});
      room.checkCompleted();
      assertFalse(room.isCompleted());
    });

  }

  @Test
  void testIsCompleted() {
    Platform.runLater(() -> {
      room.setCompleted(true);
      assertTrue(room.isCompleted());

      room.setCompleted(false);
      assertFalse(room.isCompleted());
    });

  }
}
