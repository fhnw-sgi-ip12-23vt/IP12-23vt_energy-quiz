package energiequiz.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static energiequiz.model.Constants.INTERACTION_MARGIN;
import static energiequiz.model.Constants.MARKER_FILE;
import static energiequiz.model.Constants.MARKER_MARGIN;
import static energiequiz.model.Constants.TEXTURE_PATH;
import static energiequiz.model.Constants.WINDOW_WIDTH;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import energiequiz.fxgl.EntityType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a device within the game.
 */
public class Device extends Asset {
  private DeviceState state = DeviceState.On;
  private Question question;
  private boolean completed;
  private boolean inUse;
  private double energyConsumption;
  private Player lastInteractor;
  private Set<Player> interactors = new HashSet<>();
  private int interacted = 0;
  private List<Player> players = new ArrayList<>();

  private Entity marker1;
  private Entity marker2;

  /**
   * Constructs a device with the specified attributes.
   *
   * @param filename         the filename of the device texture
   * @param posX             the X-coordinate position of the device
   * @param posY             the Y-coordinate position of the device
   * @param energyConsumption the energy consumption of the device
   */
  public Device(String filename, int posX, int posY, int width, int height,
                double energyConsumption) {
    super(filename, posX, posY, width, height);
    var scale = Helper.getImageSize(getFilename());
    super.setImageWidth((int) scale[0]);
    super.setImageHeight((int) scale[1]);
    this.energyConsumption = energyConsumption;
    completed = false;
    inUse = false;


    var entity1 = entityBuilder().type(EntityType.DEVICE)
        .viewWithBBox(new Rectangle(width, height, Color.TRANSPARENT))
        .at(getPositionX(), getPositionY()).with(new CollidableComponent(true)).collidable()
        .buildAndAttach();
    entity1.getViewComponent().addChild(texture(getFilename()));
    entity1.setScaleOrigin(new Point2D(0, 0));
    entity1.setVisible(false);
    var entity2 = entityBuilder().type(EntityType.DEVICE)
        .viewWithBBox(new Rectangle(width, height, Color.TRANSPARENT))
        .at(getPositionX() + WINDOW_WIDTH, getPositionY()).with(new CollidableComponent(true))
        .collidable().buildAndAttach();
    entity2.getViewComponent().addChild(texture(getFilename()));
    entity2.setScaleOrigin(new Point2D(0, 0));

    entity2.setVisible(false);

    var scaling = Helper.calculateScaling(getHeight(), getWidth(), getFilename());
    entity1.setScaleX(scaling[0]);
    entity1.setScaleY(scaling[1]);
    entity2.setScaleX(scaling[0]);
    entity2.setScaleY(scaling[1]);

    setEntity1(entity1);
    setEntity2(entity2);

    this.marker1 = FXGL.entityBuilder()
        .at(entity1.getX() - MARKER_MARGIN,
            entity1.getY() - MARKER_MARGIN)
        .view(MARKER_FILE)
        .scaleOrigin(0, 0)
        .zIndex(100)
        .buildAndAttach();
    var marker1Scale = Helper.calculateScaling(width + MARKER_MARGIN * 2,
        height + MARKER_MARGIN * 2, MARKER_FILE);
    marker1.setScaleX(marker1Scale[1]);
    marker1.setScaleY(marker1Scale[0]);
    marker1.setVisible(false);

    this.marker2 = FXGL.entityBuilder()
        .at(entity2.getX() - MARKER_MARGIN,
            entity2.getY() - MARKER_MARGIN)
        .view(MARKER_FILE)
        .scaleOrigin(0, 0)
        .zIndex(100)
        .buildAndAttach();
    var marker2Scale = Helper.calculateScaling(width + MARKER_MARGIN * 2,
        height + MARKER_MARGIN * 2, MARKER_FILE);
    marker2.setScaleX(marker2Scale[1]);
    marker2.setScaleY(marker2Scale[0]);
    marker2.setVisible(false);
  }

  public Player getLastInteractor() {
    return lastInteractor;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public DeviceState getState() {
    return state;
  }

  public void setState(DeviceState state) {
    this.state = state;
  }

  public boolean isCompleted() {
    return completed;
  }

  public double getEnergyConsumption() {
    return energyConsumption * state.getValue();
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public boolean isInUse() {
    return inUse;
  }

  public void setInUse(boolean inUse) {
    this.inUse = inUse;
  }

  /**
   * Retrieves the interaction hitbox of the device.
   *
   * @return the interaction hitbox of the device
   */
  public Hitbox getInteractionHitbox() {

    var h = new Hitbox((int) getEntity1().getX() + getWidth(), (int) getEntity1().getX(),
        (int) getEntity1().getY() + getHeight(), (int) getEntity1().getY());;
    return new Hitbox(h.rightBorder + INTERACTION_MARGIN, h.leftBorder - INTERACTION_MARGIN,
        h.bottomBorder + INTERACTION_MARGIN, h.topBorder - INTERACTION_MARGIN);
  }

  /**
   * Determines if the device is interactable by the given player.
   *
   * @param p the player attempting to interact with the device
   * @return true if the device is interactable, otherwise false
   */
  public boolean isInteractable(Player p) {
    if (completed) {
      return false;
    }
    if (interactors.contains(p)) {
      return false;
    } else {
      return !inUse;
    }

  }


  /**
   * Changes the state of the device based on the correctness of interaction.
   *
   * @param correct true if the interaction was correct, otherwise false
   */
  public void changeState(boolean correct) {
    if (correct) {
      switch (state) {
        case On -> {
          state = DeviceState.Off;
          completed = true;
        }
        case Angry -> {
          state = DeviceState.On;
        }
        default -> {
        }
      }
    } else {
      switch (state) {
        case On -> {
          state = DeviceState.Angry;
        }
        default -> {

        }
      }
    }
    interacted++;
    if (interacted >= 2) {
      completed = true;
    }
    changeTexture();

  }

  private void changeTexture() {
    getEntity1().getViewComponent().clearChildren();
    getEntity2().getViewComponent().clearChildren();
    getEntity1().getViewComponent().addChild(texture(getFilename()));
    getEntity2().getViewComponent().addChild(texture(getFilename()));
    if (lastInteractor != null) {
      if (lastInteractor.isP1()) {
        this.marker1.setVisible(false);
        if (!players.get(1).isP1()) {
          if (players.get(0).getCurrentRoom().equals(players.get(1).getCurrentRoom())) {
            this.marker2.setVisible(true);
          }
        }
      } else {
        this.marker2.setVisible(false);
        if (players.get(0).isP1()) {
          if (players.get(1).getCurrentRoom().equals(players.get(0).getCurrentRoom())) {
            this.marker1.setVisible(true);
          }
        }
      }
    }
    if (completed) {
      this.marker2.setVisible(false);
      this.marker1.setVisible(false);
    }
  }

  public void setLastInteractor(Player lastInteractor) {
    this.lastInteractor = lastInteractor;
  }

  public void addInteractor(Player player) {
    this.interactors.add(player);
  }

  public Entity getMarker1() {
    return marker1;
  }

  public Entity getMarker2() {
    return marker2;
  }

  /**
   * Sets the visibility of arrows indicating interaction with the device.
   *
   * @param originalRoom the name of the original room
   * @param otherRoom    the name of the other room
   * @param playerNum    the player number (1 or 2)
   */
  public void setMarker(String originalRoom, String otherRoom, int playerNum,
                        Player player1, Player player2) {
    if (originalRoom.equals(otherRoom)) {
      if (isInteractable(player1)) {
        this.marker1.setVisible(true);
      }
      if (isInteractable(player2)) {
        this.marker2.setVisible(true);
      }

    } else {
      if (playerNum == 1) {
        if (isInteractable(player1)) {
          this.marker1.setVisible(true);
        }
        this.marker2.setVisible(false);
      } else {
        if (isInteractable(player2)) {
          this.marker2.setVisible(true);
        }
        this.marker1.setVisible(false);
      }
    }
  }

  /**
   * Adds a player to the list of players in the game.
   *
   * @param player a player
   */
  public void addPlayer(Player player) {
    if (! players.contains(player)) {
      this.players.add(player);
    }

  }

  @Override
  public String getFilename() {
    String[] fileNameParts = super.getFilename().split("\\.");
    String ret = fileNameParts[0] + "_" + getState().getName() + "." + fileNameParts[1];
    if (new File(TEXTURE_PATH + ret).exists()) {
      return ret;
    } else if (new File(TEXTURE_PATH + fileNameParts[0] + "_on." + fileNameParts[1]).exists()) {
      return fileNameParts[0] + "_on." + fileNameParts[1];
    } else {
      return super.getFilename();
    }

  }
}
