package energiequiz.fxgl;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKey;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKeyDown;
import static energiequiz.model.Constants.WINDOW_HEIGHT;
import static energiequiz.model.Constants.WINDOW_WIDTH;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import energiequiz.controller.Game;
import javafx.scene.input.KeyCode;

/**
 * Main class for the FXGL application.
 */

public class FxglApp extends GameApplication {
  private Game game;


  @Override
  protected void initSettings(GameSettings settings) {
    settings.setFullScreenAllowed(true);
    settings.setManualResizeEnabled(true);
    //settings.setFullScreenFromStart(true);
    settings.setProfilingEnabled(false);
    settings.setWidth(2 * WINDOW_WIDTH);
    settings.setHeight(WINDOW_HEIGHT);
    settings.setTitle("Energie App");
    settings.setVersion("0.1");
  }


  @Override
  protected void initInput() {
    onKey(KeyCode.D, () -> {
      game.handleDirectionInput("right", true);
      return null;
    });

    onKey(KeyCode.A, () -> {
      game.handleDirectionInput("left", true);
      return null;
    });

    onKey(KeyCode.W, () -> {
      game.handleDirectionInput("up", true);
      return null;
    });

    onKey(KeyCode.S, () -> {
      game.handleDirectionInput("down", true);
      return null;
    });
    onKey(KeyCode.RIGHT, () -> {
      game.handleDirectionInput("right", false);
      return null;
    });

    onKey(KeyCode.LEFT, () -> {
      game.handleDirectionInput("left", false);
      return null;
    });

    onKey(KeyCode.UP, () -> {
      game.handleDirectionInput("up", false);
      return null;
    });

    onKey(KeyCode.DOWN, () -> {
      game.handleDirectionInput("down", false);
      return null;
    });

    onKeyDown(KeyCode.F, () -> {
      game.checkForDoor(game.getPlayer1());
      if (!game.isQuiz1Active()) {
        game.checkForQuiz(game.getPlayer1());
      }
      game.loadRoom();
      return null;
    });
    onKeyDown(KeyCode.G, () -> {
      game.checkForDoor(game.getPlayer2());
      if (!game.isQuiz2Active()) {
        game.checkForQuiz(game.getPlayer2());
      }
      game.loadRoom();

      return null;
    });
    onKeyDown(KeyCode.DIGIT1, () -> {
      game.handleButtonInput("1", true);
      return null;
    });
    onKeyDown(KeyCode.DIGIT2, () -> {
      game.handleButtonInput("2", true);
      return null;
    });
    onKeyDown(KeyCode.DIGIT3, () -> {
      game.handleButtonInput("3", true);
      return null;
    });
    onKeyDown(KeyCode.DIGIT4, () -> {
      game.handleButtonInput("1", false);
      return null;
    });
    onKeyDown(KeyCode.DIGIT5, () -> {
      game.handleButtonInput("2", false);
      return null;
    });

    onKeyDown(KeyCode.DIGIT6, () -> {
      game.handleButtonInput("3", false);
      return null;
    });

  }


  @Override
  protected void initGame() {
    game = new Game();

  }


  @Override
  protected void initPhysics() {

  }

  public static void main(String[] args) {
    launch(args);
  }
}




