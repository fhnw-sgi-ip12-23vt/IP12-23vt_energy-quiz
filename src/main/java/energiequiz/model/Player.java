package energiequiz.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static energiequiz.model.Constants.WINDOW_WIDTH;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import energiequiz.fxgl.EntityType;
import javafx.geometry.Point2D;

/**
 * Represents a question within the game.
 */
public class Player {
  private Entity entity1;
  private Entity entity2;
  private int positionX;
  private int positionY;
  int playerWidth = 76;
  int playerHeight = 109;
  private int movementSpeed = 1;
  private Room currentRoom;
  private boolean isP1;
  private String filename;


  private int[] movementDirection = {0, 0};

  /**
   * Constructs a player with the specified attributes.
   *
   * @param positionX   the initial X-coordinate position of the player
   * @param positionY   the initial Y-coordinate position of the player
   * @param currentRoom the room the player is currently in
   * @param isP1        indicates whether the player is Player 1
   * @param filename    the filename of the player's texture
   */
  public Player(int positionX, int positionY, Room currentRoom, boolean isP1, String filename) {
    this.positionX = positionX;
    this.positionY = positionY;
    this.currentRoom = currentRoom;
    this.isP1 = isP1;
    this.filename = filename;
    var type = EntityType.PLAYER1;
    if (!isP1) {
      type = EntityType.PLAYER2;
    }

    entity1 = entityBuilder()
        .type(type)
        .at(positionX, positionY)
        // .viewWithBBox(
        //    new Rectangle(playerWidth, playerHeight, Color.TRANSPARENT))
        .with(new CollidableComponent(true))
        .collidable()
        .buildAndAttach();
    entity1.getViewComponent().addChild(texture(filename));


    entity1.setScaleOrigin(new Point2D(0, 0));
    entity2 = entity1.copy();
    entity2.setScaleOrigin(new Point2D(0, 0));
    entity2.translateX(WINDOW_WIDTH);

    if (isP1) {
      entity1.getBoundingBoxComponent().addHitBox(
          new HitBox(BoundingShape.box(playerWidth, playerHeight)));
    } else {
      entity2.getBoundingBoxComponent().addHitBox(
          new HitBox(BoundingShape.box(playerWidth, playerHeight)));
    }

    var scales = Helper.calculateScaling(playerHeight, playerWidth, filename);
    var scaleX = scales[0];
    var scaleY = scales[1];
    entity1.setScaleX(scaleX);
    entity1.setScaleY(scaleY);
    entity2.setScaleX(scaleX);
    entity2.setScaleY(scaleY);
    getGameWorld().addEntity(entity2);
  }


  public Entity getEntity2() {
    return entity2;
  }

  public Entity getEntity1() {
    return entity1;
  }

  public boolean isP1() {
    return isP1;
  }

  public int getPositionX() {
    return positionX;
  }

  public int getPositionY() {
    return positionY;
  }


  public void move(int x, int y) {
    positionX += movementSpeed * x;
    positionY += movementSpeed * y;
  }

  public void move() {
    positionX += movementSpeed * movementDirection[0];
    positionY += movementSpeed * movementDirection[1];
  }

  public void setCurrentRoom(Room currentRoom) {
    this.currentRoom = currentRoom;
  }

  public Room getCurrentRoom() {
    return currentRoom;
  }

  /**
   * Retrieves the hitbox of the player.
   *
   * @return all corners as an Arraylist of integers
   */
  public Hitbox getHitBox() {
    positionX = (int) entity1.getX();
    positionY = (int) entity1.getY();
    return new Hitbox(positionX + playerWidth, positionX,
        positionY + playerHeight, positionY);
  }

  public int[] getMovementDirection() {
    return movementDirection;
  }

  public void setMovementDirection(int[] movementDirection) {
    this.movementDirection = movementDirection;
  }
}
