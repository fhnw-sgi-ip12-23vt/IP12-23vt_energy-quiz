package energiequiz.util;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import java.sql.SQLOutput;
import javafx.application.Platform;

/**
 * a util class to setup the FXGL test environment.
 */
public class FxglTestUtil {

  private static Thread gameThread;

  private static boolean fxInitialized = false;

  /**
   * sets up the FXGL environment.
   */
  public static void setUpFxgl() {
    if (!fxInitialized) {
      Platform.startup(() -> {
        fxInitialized = true;
        gameThread =
            new Thread(() -> GameApplication.launch(MockGameApplication.class, new String[0]));
        gameThread.start();
      });
    }
  }

  /**
   * tears down the FXGL environment.
   */
  public static void tearDownFxgl() {
    Platform.runLater(() -> {
      FXGL.getGameController().exit();
    });
    try {
      gameThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * a class for a mock fxgl application.
   */
  public static class MockGameApplication extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
      settings.setWidth(800);
      settings.setHeight(600);
      settings.setTitle("Test");
      settings.setVersion("0.1");
    }
  }
}
