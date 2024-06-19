package energiequiz.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static energiequiz.model.Constants.WINDOW_WIDTH;

import com.almasb.fxgl.entity.Entity;


/**
 * Represents a room within the game.
 */
public class Room {
  private boolean completed;
  private String name;
  private String background;
  private Question question;
  private Asset[] assets;
  private int position;
  private Entity room1Background;
  private Entity room2Background;

  /**
   * a room for the game.
   *
   * @param name name of the room
   * @param background filename of the background image
   */
  public Room(String name, String background) {
    this.name = name;
    this.background = background;


    var background1 = entityBuilder()
        .at(0, 0)
        .zIndex(-1000)
        .buildAndAttach();
    if (!background.isEmpty()) {
      background1.getViewComponent().addChild(texture(background));
    }
    var background2 = entityBuilder()
        .at(WINDOW_WIDTH, 0)
        .zIndex(-1000)
        .buildAndAttach();
    if (!background.isEmpty()) {
      background2.getViewComponent().addChild(texture(background));
    }
    this.room1Background = background1;
    this.room2Background = background2;
    room1Background.setVisible(false);
    room2Background.setVisible(false);
  }

  public void setPosition(int x) {
    this.position = x;

  }

  public String getName() {
    return name;
  }

  public int getPosition() {
    return position;
  }


  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public void setAssets(Asset[] assets) {
    this.assets = assets;

  }

  public Asset[] getAssets() {
    return assets;
  }


  public String getBackground() {
    return background;
  }

  public void setRoom1Background(boolean visible) {
    this.room1Background.setVisible(visible);
  }

  public void setRoom2Background(boolean visible) {
    this.room2Background.setVisible(visible);
  }

  /**
   * Checks if the room is completed based on the completion status of its assets.
   */
  public void checkCompleted() {
    boolean completed = true;
    for (int i = 0; i < assets.length; i++) {
      if (assets[i] instanceof Device) {
        if (!((Device) assets[i]).isCompleted()) {
          completed = false;
          break;
        }
      }

    }
    this.completed = completed;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
