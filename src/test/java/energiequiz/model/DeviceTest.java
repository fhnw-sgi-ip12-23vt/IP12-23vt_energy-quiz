package energiequiz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.almasb.fxgl.entity.Entity;
import energiequiz.util.FxglTestUtil;
import java.util.ArrayList;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * a class to test device creation.
 */
@ExtendWith(MockitoExtension.class)
public class DeviceTest {

  private Device device;
  private Player player;
  private Room room;

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }


  @BeforeEach
  void setUp() {
    Platform.runLater(() -> {
      room = new Room("Room1", "A test room");
      player = new Player(100, 100, room, true, "player.png");
      device = new Device("lamp.png", 100, 100, 80, 120, 10);
    });

  }

  @Test
  void testDeviceInitialization() {
    Platform.runLater(() -> {
      assertEquals(100, device.getPositionX());
      assertEquals(100, device.getPositionY());
      assertEquals(80, device.getWidth());
      assertEquals(120, device.getHeight());
      assertEquals(10, device.getEnergyConsumption());

      // Check entity properties
      Entity entity1 = device.getEntity1();
      Entity entity2 = device.getEntity2();
      assertNotNull(entity1);
      assertNotNull(entity2);
    });


  }

  @Test
  void testSetGetState() {
    Platform.runLater(() -> {
      device.setState(DeviceState.Off);
      assertEquals(DeviceState.Off, device.getState());

      device.setState(DeviceState.Angry);
      assertEquals(DeviceState.Angry, device.getState());
    });

  }

  @Test
  void testSetGetQuestion() {
    Platform.runLater(() -> {
      Question question =
          new Question("Question?", new ArrayList<>(), new ArrayList<>(), "1",
              QuestionType.Choice);
      device.setQuestion(question);
      assertEquals(question, device.getQuestion());
    });

  }

  @Test
  void testSetGetCompleted() {
    Platform.runLater(() -> {
      device.setCompleted(true);
      assertTrue(device.isCompleted());

      device.setCompleted(false);
      assertFalse(device.isCompleted());
    });

  }

  @Test
  void testSetGetInUse() {
    Platform.runLater(() -> {
      device.setInUse(true);
      assertTrue(device.isInUse());

      device.setInUse(false);
      assertFalse(device.isInUse());
    });

  }

  @Test
  void testIsInteractable() {
    Platform.runLater(() -> {
      device.setCompleted(true);
      assertFalse(device.isInteractable(player));

      device.setCompleted(false);
      assertTrue(device.isInteractable(player));

      device.setLastInteractor(player);
      assertFalse(device.isInteractable(player));

      Player anotherPlayer = new Player(150, 150, room, false, "player2.png");
      assertTrue(device.isInteractable(anotherPlayer));

      device.setInUse(true);
      assertFalse(device.isInteractable(anotherPlayer));
    });


  }

  @Test
  void testChangeState() {
    Platform.runLater(() -> {
      device.changeState(true);
      assertEquals(DeviceState.Off, device.getState());

      device.setState(DeviceState.On);
      device.changeState(false);
      assertEquals(DeviceState.Angry, device.getState());

      device.changeState(true);
      assertEquals(DeviceState.On, device.getState());
    });

  }

  @Test
  void testSetLastInteractor() {
    Platform.runLater(() -> {
      device.setLastInteractor(player);
      assertEquals(player, device.getLastInteractor());
    });

  }

  @Test
  void testSetArrows() {
    Platform.runLater(() -> {
      device.setMarker("Room1", "Room1", 1, player, player);
      assertTrue(device.getMarker1().isVisible());
      assertTrue(device.getMarker2().isVisible());

      device.setMarker("Room1", "Room2", 1, player, player);
      assertTrue(device.getMarker1().isVisible());
      assertFalse(device.getMarker2().isVisible());

      device.setMarker("Room1", "Room2", 2, player, player);
      assertFalse(device.getMarker1().isVisible());
      assertTrue(device.getMarker2().isVisible());
    });

  }

  @Test
  void testGetFilename() {
    Platform.runLater(() -> {
      device.setState(DeviceState.On);
      assertEquals("lamp_on.png", device.getFilename());

      device.setState(DeviceState.Off);
      assertEquals("lamp_off.png", device.getFilename());

      device.setState(DeviceState.Angry);
      assertEquals("lamp_angry.png", device.getFilename());
    });

  }
}
