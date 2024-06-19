package energiequiz.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static energiequiz.model.Constants.WINDOW_WIDTH;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import energiequiz.fxgl.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents an asset within the game.
 */
public class Asset {
  private final String filename;
  private final int positionX;
  private final int positionY;
  private int imageWidth;
  private int imageHeight;
  private final int width;
  private final int height;
  private Hitbox hitbox;
  private Entity entity1;
  private Entity entity2;

  /**
   * Constructs an asset with the specified attributes.
   *
   * @param filename   the filename of the asset texture
   * @param posX       the X-coordinate position of the asset
   * @param posY       the Y-coordinate position of the asset
   */
  public Asset(final String filename, final int posX, final int posY, int width, int height) {
    this.filename = filename;
    this.positionX = posX;
    this.positionY = posY;
    this.width = width;
    this.height = height;
    var imageSize = Helper.getImageSize(filename);
    this.imageWidth = (int) imageSize[0];
    this.imageHeight = (int) imageSize[1];


    if (!(this instanceof Device)) {
      entity1 = entityBuilder().type(EntityType.ASSET).at(positionX, positionY)
          .viewWithBBox(new Rectangle(width, height, Color.TRANSPARENT))
          .with(new CollidableComponent(true)).collidable().buildAndAttach();
      entity1.getViewComponent().addChild(texture(getFilename()));
      entity1.setScaleOrigin(new Point2D(0, 0));
      var scaling = Helper.calculateScaling(height, width, filename);
      entity1.setScaleX(scaling[0]);
      entity1.setScaleY(scaling[1]);
      entity1.setVisible(false);
      entity2 = entityBuilder().type(EntityType.ASSET).at(positionX + WINDOW_WIDTH, positionY)
          .viewWithBBox(new Rectangle(width, height, Color.TRANSPARENT))
          .with(new CollidableComponent(true)).collidable().buildAndAttach();
      entity2.getViewComponent().addChild(texture(getFilename()));
      entity2.setScaleOrigin(new Point2D(0, 0));
      entity2.setScaleX(scaling[0]);
      entity2.setScaleY(scaling[1]);
      entity2.setVisible(false);
      this.hitbox = new Hitbox((int) entity1.getX() + imageWidth, (int) entity1.getX(),
          (int) entity1.getY() + imageHeight, (int) entity1.getY());
    }

  }

  public Entity getEntity1() {
    return entity1;
  }

  public Entity getEntity2() {
    return entity2;
  }

  public String getFilename() {
    return filename;
  }

  public int getPositionX() {
    return positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  public int getImageWidth() {
    return imageWidth;
  }

  public int getImageHeight() {
    return imageHeight;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setEntity1(Entity entity1) {
    this.entity1 = entity1;
  }

  public void setEntity2(Entity entity2) {
    this.entity2 = entity2;
  }

  /**
   * Retrieves the hitbox of the asset.
   *
   * @return the hitbox of the asset
   */
  public Hitbox getHitBox() {
    return hitbox;
  }

  public void setImageWidth(int imageWidth) {
    this.imageWidth = imageWidth;
  }

  public void setImageHeight(int imageHeight) {
    this.imageHeight = imageHeight;
  }
}
