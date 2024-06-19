package energiequiz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import energiequiz.fxgl.EntityType;
import energiequiz.util.FxglTestUtil;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * a class to test asset creation.
 */
@ExtendWith(MockitoExtension.class)
public class AssetTest {

  private Asset asset;

  private final String filename = "test.png";
  private final int posX = 100;
  private final int posY = 100;
  private final int width = 50;
  private final int height = 50;

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }


  @BeforeEach
  void setUp() {
    Platform.runLater(() -> {
      asset = new Asset(filename, posX, posY, width, height);
    });
  }

  @Test
  public void testConstructor() {
    Platform.runLater(() -> {
      assertEquals(filename, asset.getFilename());
      assertEquals(posX, asset.getPositionX());
      assertEquals(posY, asset.getPositionY());
      assertEquals(width, asset.getWidth());
      assertEquals(height, asset.getHeight());
      assertNotNull(asset.getHitBox());
    });

  }

  @Test
  public void testEntity1Creation() {
    Platform.runLater(() -> {
      Entity entity1 = asset.getEntity1();
      assertNotNull(entity1);
      assertEquals(EntityType.ASSET, entity1.getType());
      assertTrue(entity1.hasComponent(CollidableComponent.class));
      assertEquals(posX, entity1.getPosition().getX(), 0.1);
      assertEquals(posY, entity1.getPosition().getY(), 0.1);
      assertEquals(width, entity1.getBoundingBoxComponent().getWidth(), 0.1);
      assertEquals(height, entity1.getBoundingBoxComponent().getHeight(), 0.1);
    });

  }

  @Test
  public void testEntity2Creation() {
    Platform.runLater(() -> {
      Entity entity2 = asset.getEntity2();
      assertNotNull(entity2);
      assertEquals(EntityType.ASSET, entity2.getType());
      assertTrue(entity2.hasComponent(CollidableComponent.class));
      assertEquals(posX + Constants.WINDOW_WIDTH, entity2.getPosition().getX(), 0.1);
      assertEquals(posY, entity2.getPosition().getY(), 0.1);
      assertEquals(width, entity2.getBoundingBoxComponent().getWidth(), 0.1);
      assertEquals(height, entity2.getBoundingBoxComponent().getHeight(), 0.1);
    });

  }

  @Test
  public void testSetEntity1() {
    Platform.runLater(() -> {
      Entity newEntity1 = new Entity();
      asset.setEntity1(newEntity1);
      assertEquals(newEntity1, asset.getEntity1());
    });
  }

  @Test
  public void testSetEntity2() {
    Platform.runLater(() -> {
      Entity newEntity2 = new Entity();
      asset.setEntity2(newEntity2);
      assertEquals(newEntity2, asset.getEntity2());
    });
  }
}
