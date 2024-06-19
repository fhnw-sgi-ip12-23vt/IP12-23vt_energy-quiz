package energiequiz.model;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.almasb.fxgl.entity.Entity;
import energiequiz.util.FxglTestUtil;
import java.io.File;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * a class to test player creation.
 */
@ExtendWith(MockitoExtension.class)
public class PlayerTest {

  private Player player;
  private Room room;

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }

  @BeforeEach
  void setUp() {
    Platform.runLater(() -> {
      room = new Room("Test Room", "background");
      player = new Player(100, 100, room, true, "player.png");
    });
  }

  @Test
  void testPlayerInitialization() {
    Platform.runLater(() -> {
      Entity entity1 = player.getEntity1();
      Entity entity2 = player.getEntity2();

      assertNotNull(entity1);
      assertNotNull(entity2);

      assertEquals(100, player.getPositionX());
      assertEquals(100, player.getPositionY());
      assertTrue(player.isP1());
      // Check texture
      File file = new File(Constants.TEXTURE_PATH + "player.png");
      assertTrue(file.exists());
    });


  }

  @Test
  void testPlayerMove() {
    Platform.runLater(() -> {
      player.move(10, 0);
      assertEquals(110, player.getPositionX());

      player.move(0, 10);
      assertEquals(110, player.getPositionY());
    });

  }

  @Test
  void testSetMovementDirection() {
    Platform.runLater(() -> {
      int[] newDirection = {1, -1};
      player.setMovementDirection(newDirection);
      assertArrayEquals(newDirection, player.getMovementDirection());
    });

  }

  @Test
  void testMoveWithDirection() {
    Platform.runLater(() -> {
      int[] newDirection = {1, 1};
      player.setMovementDirection(newDirection);
      player.move();
      assertEquals(101, player.getPositionX());
      assertEquals(101, player.getPositionY());
    });

  }

  @Test
  void testSetCurrentRoom() {
    Platform.runLater(() -> {
      Room newRoom = new Room("New Room", "background");
      player.setCurrentRoom(newRoom);
      assertEquals(newRoom, player.getCurrentRoom());
    });

  }
}
