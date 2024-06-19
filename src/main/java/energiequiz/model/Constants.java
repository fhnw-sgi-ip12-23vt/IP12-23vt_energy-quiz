package energiequiz.model;

import java.io.File;
import java.nio.file.Path;
import javafx.scene.paint.Color;

/**
 * Contains constants used in the game.
 */
public class Constants {
  public static final int MARGIN_P2 = 500;
  public static final String BASE_PATH = System.getProperty("user.dir");
  public static final String JSONPATH =
      BASE_PATH + File.separator + "src" + File.separator + "main" + File.separator
          + "resources" + File.separator + "json" + File.separator;
  public static final String TEXTURE_PATH =
      BASE_PATH + File.separator + "src" + File.separator + "main" + File.separator
          + "resources" + File.separator + "assets" + File.separator + "textures" + File.separator;
  public static final String FXML_PATH =
      BASE_PATH + File.separator + "src" + File.separator + "main" + File.separator
          + "resources" + File.separator + "fxml" + File.separator;
  public static final double STARTING_ENERGY = 1700;
  public static final int WINDOW_HEIGHT = 600; //500
  public static final int WINDOW_WIDTH = 800; //600
  public static final int MOVEMENT_SPEED = 10;
  public static final int GAME_WIDTH = WINDOW_WIDTH - 200;
  public static final int GAME_HEIGHT = WINDOW_HEIGHT - 100;
  public static final int WIDTH_DIFFERENCE = WINDOW_WIDTH - GAME_WIDTH;
  public static final int HEIGHT_DIFFERENCE = WINDOW_HEIGHT - GAME_HEIGHT;
  public static final int PLAYER_HEIGHT = 120;
  public static final int PLAYER_WIDTH = 80;
  public static final int DOOR_WIDTH = 80;
  public static final int DOOR_HEIGHT = 40;
  public static final int QUIZ_FRAME_OFFSET_X = 10;
  public static final int QUIZ_FRAME_OFFSET_Y = 25;
  public static final String SCORE_FILE = "score.csv";
  public static final String MARKER_FILE = "marker_Device.png";
  public static final int MARKER_MARGIN = 5;
  public static final int KNOCK_BACK_MARGIN = 20;
  public static final int INTERACTION_MARGIN = 25;
  public static final int BORDER_WIDTH = 20;
  public static final Path SCORE_PATH = Path.of(BASE_PATH + "/src/main/resources/csv/score.csv");
  public static final Path LOG_PATH = Path.of(BASE_PATH + "/src/main/resources/csv");
  public static final int SCORE_SIZE = 5;
  public static final Path NAMES_PATH = Path.of(BASE_PATH + "/src/main/resources/csv/names.csv");
  public static final Path USED_NAMES_PATH =
      Path.of(BASE_PATH + "/src/main/resources/csv/usednames.csv");
  public static final Hitbox DOOR_TOP_HITBOX = new Hitbox(
      WINDOW_WIDTH / 2 + DOOR_WIDTH / 2, WINDOW_WIDTH / 2 - DOOR_WIDTH / 2,
      DOOR_HEIGHT + HEIGHT_DIFFERENCE / 2, 0 + HEIGHT_DIFFERENCE / 2);
  public static final Hitbox DOOR_BOTTOM_HITBOX = new Hitbox(
      WINDOW_WIDTH / 2 + DOOR_WIDTH / 2, WINDOW_WIDTH / 2 - DOOR_WIDTH / 2,
      WINDOW_HEIGHT - HEIGHT_DIFFERENCE / 2, WINDOW_HEIGHT - DOOR_HEIGHT - HEIGHT_DIFFERENCE / 2);

  /**
   * Colours in hex code
   * Bsp. 0xFF41B429
   * FF transparency, 41 red, B4 green, 29 blue
   */
  public static final int GREEN_ENERGY = 0xFF00FF00;
  public static final String GREEN_ENERGY_STRING = "#00FF00";
  public static final int ORANGE_ENERGY = 0xFFFFb900;
  public static final String ORANGE_ENERGY_STRING = "#FFb900";
  public static final int RED_ENERGY = 0xFFEC0E0A;
  public static final String RED_ENERGY_STRING = "#EC0E0A";
  public static final int RED_BUTTON = 0xFFFF1C18; //255 28 24
  public static final Color RED_BUTTON_COLOR = Color.color(1, 0.098, 0.094); //255 28 24
  public static final int GREEN_BUTTON = 0xFF16C019; //22 192 25
  public static final Color GREEN_BUTTON_COLOR = Color.color(0.086, 0.752, 0.098); //22 192 25
  public static final int BLUE_BUTTON = 0xFF384CFB; //56 76 251
  public static final Color BLUE_BUTTON_COLOR = Color.color(0.219, 0.298, 0.984); //56 76 251
  public static final int BLACK = 0xFF000000;
  public static final int WHITE = 0xFFFFFFFF;
  public static final int DEFAULT_TEXT = 0xFF000000;
  public static final int DOOR_YELLOW = 0x88FFD61E;
  public static final int FLOOR_WOOD = 0xFF8E5A36;

}
