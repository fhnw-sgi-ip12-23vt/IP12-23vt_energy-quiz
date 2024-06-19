package energiequiz.controller;

import static com.almasb.fxgl.dsl.FXGLForKtKt.addUINode;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.removeUINode;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static energiequiz.model.Constants.BORDER_WIDTH;
import static energiequiz.model.Constants.DOOR_BOTTOM_HITBOX;
import static energiequiz.model.Constants.DOOR_TOP_HITBOX;
import static energiequiz.model.Constants.DOOR_WIDTH;
import static energiequiz.model.Constants.FXML_PATH;
import static energiequiz.model.Constants.HEIGHT_DIFFERENCE;
import static energiequiz.model.Constants.MOVEMENT_SPEED;
import static energiequiz.model.Constants.NAMES_PATH;
import static energiequiz.model.Constants.PLAYER_HEIGHT;
import static energiequiz.model.Constants.QUIZ_FRAME_OFFSET_X;
import static energiequiz.model.Constants.QUIZ_FRAME_OFFSET_Y;
import static energiequiz.model.Constants.SCORE_PATH;
import static energiequiz.model.Constants.SCORE_SIZE;
import static energiequiz.model.Constants.STARTING_ENERGY;
import static energiequiz.model.Constants.WINDOW_HEIGHT;
import static energiequiz.model.Constants.WINDOW_WIDTH;
import static java.util.Comparator.comparingDouble;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import energiequiz.fxgl.EntityType;
import energiequiz.io.FileReader;
import energiequiz.io.FileWriter;
import energiequiz.io.InputHandler;
import energiequiz.model.Asset;
import energiequiz.model.Device;
import energiequiz.model.Direction;
import energiequiz.model.Hitbox;
import energiequiz.model.LeaderboardEntry;
import energiequiz.model.Player;
import energiequiz.model.Question;
import energiequiz.model.QuestionType;
import energiequiz.model.Room;
import energiequiz.model.ScreenState;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * This class represents the main game logic for the energy quiz game. It handles the game state,
 * player movements, interactions with the game world, and the game UI. It also manages the game's
 * timer and energy consumption mechanics.
 */

public class Game {

  private Room[] rooms = new Room[4];
  private Player player1;
  private Player player2;
  private boolean quiz1Active = false;
  private boolean quiz2Active = false;
  private ScreenState state1 = ScreenState.Game;
  private ScreenState state2 = ScreenState.Game;
  private Timer timer;
  private Date endTime;
  private com.almasb.fxgl.time.Timer fxglTimer;
  private String buttonOrder1 = "";
  private String buttonOrder2 = "";
  private boolean quiz1Correct;
  private boolean quiz2Correct;
  private Device p1Device;
  private Device p2Device;
  private double remainingEnergy;
  private GameInfoUiController gameInfoUiController1;
  private GameInfoUiController gameInfoUiController2;
  private QuizUiController quizUiController1;
  private QuizUiController quizUiController2;
  private MainMenuController mainMenuController1;
  private MainMenuController mainMenuController2;
  private LeaderboardController leaderboardController1;
  private LeaderboardController leaderboardController2;
  private TutorialController tutorialController1;
  private TutorialController tutorialController2;
  private Parent quizRoot1;
  private Parent quizRoot2;
  private Parent tutorialRoot1;
  private Parent tutorialRoot2;
  private Parent mainMenuRoot1;
  private Parent mainMenuRoot2;
  private Parent leaderboardRoot1;
  private Parent leaderboardRoot2;
  private Parent gameInfoUiRoot1;
  private Parent gameInfoUiRoot2;
  private Parent languageSelectRoot1;
  private Parent languageSelectRoot2;
  private Entity door1;
  private Entity door2;
  private boolean p1Ready = false;
  private boolean p2Ready = false;

  private Asset[] previousAssets1 = new Asset[0];
  private Asset[] previousAssets2 = new Asset[0];

  private boolean gameFinished = false;

  private LeaderboardEntry currentEntry;

  private ResourceBundle resourceBundle = ResourceBundle.getBundle("strings/bundle", Locale.GERMAN);

  private List<Entity> borders1 = new ArrayList<>();
  private List<Entity> borders2 = new ArrayList<>();

  /**
   * Constructor for the Game class. Initializes the game state, creates the game UI, and sets up
   * the game world.
   */

  public Game() {
    if (System.getProperty("os.name").startsWith("Linux")) {
      new InputHandler(this);
    }
    initGame();
  }

  /**
   * Initiates the UIs and game states.
   */
  public void initGame() {
    remainingEnergy = STARTING_ENERGY;
    createGameInfoUi();
    createQuizUi();
    createTutorialUi();
    createLanguageSelect();
    createMainMenu();
    createLeaderboardUi();
    createDoor();
    changeState1(ScreenState.MainMenu);
    changeState2(ScreenState.MainMenu);

    this.borders1 = buildBorder(70 - BORDER_WIDTH, WINDOW_WIDTH - 100,
        0 - BORDER_WIDTH, WINDOW_HEIGHT - 10);
    this.borders2 = buildBorder(WINDOW_WIDTH + 70  - BORDER_WIDTH, WINDOW_WIDTH * 2 - 100,
        0 - BORDER_WIDTH, WINDOW_HEIGHT - 10);
  }

  private List<Entity> buildBorder(int minX, int maxX, int minY, int maxY) {
    List<Entity> borders = new ArrayList<>();
    var borderShapeTop = new Rectangle(WINDOW_WIDTH, BORDER_WIDTH);
    borderShapeTop.setFill(Color.TRANSPARENT);
    borderShapeTop.setOpacity(0.5);

    var borderTop = entityBuilder()
        .at(minX, minY)
        .viewWithBBox(borderShapeTop)
        .with(new CollidableComponent(true))
        .collidable()
        .type(EntityType.BORDER)
        .buildAndAttach();

    borders.add(borderTop);

    var borderShapeBot = new Rectangle(WINDOW_WIDTH, 30);
    borderShapeBot.setFill(Color.TRANSPARENT);
    borderShapeBot.setOpacity(0.5);

    var borderBot = entityBuilder()
        .at(minX, maxY)
        .viewWithBBox(borderShapeBot)
        .with(new CollidableComponent(true))
        .collidable()
        .type(EntityType.BORDER)
        .buildAndAttach();

    borders.add(borderBot);

    var borderShapeL = new Rectangle(10, WINDOW_HEIGHT);
    borderShapeL.setFill(Color.TRANSPARENT);
    borderShapeL.setOpacity(0.5);

    var borderL = entityBuilder()
        .at(minX, minY)
        .viewWithBBox(borderShapeL)
        .with(new CollidableComponent(true))
        .collidable()
        .type(EntityType.BORDER)
        .buildAndAttach();

    borders.add(borderL);

    var borderShapeR = new Rectangle(10, WINDOW_HEIGHT);
    borderShapeR.setFill(Color.TRANSPARENT);
    borderShapeR.setOpacity(0.5);

    var borderR = entityBuilder()
        .at(maxX, minY)
        .viewWithBBox(borderShapeR)
        .with(new CollidableComponent(true))
        .collidable()
        .type(EntityType.BORDER)
        .buildAndAttach();

    borders.add(borderR);

    return borders;
  }

  /**
   * Starts the game by initializing the game world and starting the game timer.
   */

  public void startGame() {
    Random r = new Random();
    FileReader fileReader = new FileReader();
    List<Question> questionArrayList = fileReader.readQuestions(resourceBundle.getLocale());
    List<Room> roomArrayList = fileReader.readRooms();
    for (int i = 0; i < rooms.length; i++) {
      int rand = r.nextInt(roomArrayList.size());
      Room room = roomArrayList.get(rand);
      roomArrayList.remove(rand);
      room.setPosition(i);
      room.checkCompleted(); //if room has no devices
      rooms[i] = room;
      Asset[] assets = rooms[i].getAssets();
      for (int k = 0; k < assets.length; k++) {
        if (assets[k] instanceof Device) {
          rand = r.nextInt(questionArrayList.size());
          Question q = questionArrayList.get(rand);
          ((Device) assets[k]).setQuestion(q);
          questionArrayList.remove(rand);

        }

      }
    }
    player1 = new Player(220, 466, rooms[0], true, mainMenuController1.selectedCharacterFilename());
    player2 =
        new Player(380, 466, rooms[0], false, mainMenuController2.selectedCharacterFilename());
    gameInfoUiController1.updateRoomDisplay(rooms, player1.getCurrentRoom());
    gameInfoUiController2.updateRoomDisplay(rooms, player2.getCurrentRoom());
    for (Room room : rooms) {
      for (Asset a : room.getAssets()) {
        if (a instanceof Device) {
          ((Device) a).addPlayer(player1);
          ((Device) a).addPlayer(player2);
        }
      }
    }
    timer = new Timer();
    TimerTask timerTask1 = new TimerTask() {
      @Override
      public void run() {
        if (remainingEnergy - getCurrentEnergyConsumption() >= 0) {
          remainingEnergy -= getCurrentEnergyConsumption();
        } else {
          remainingEnergy = 0;
        }
        gameInfoUiController1.updateEnergyBar(remainingEnergy, getCurrentEnergyConsumption());
        gameInfoUiController2.updateEnergyBar(remainingEnergy, getCurrentEnergyConsumption());
        if (remainingEnergy == 0) {
          timer.cancel();
        }
      }
    };
    timer.schedule(timerTask1, 1000, 1000);

    loadRoom();

    fxglTimer = getGameTimer();

    fxglTimer.runAtInterval(() -> {
      if (
          !gameFinished
              && (isFinished() || remainingEnergy == 0)
              && (!quiz1Active && !quiz2Active)
      ) {
        gameFinished = true;
        saveScore();
      }
    }, Duration.millis(300));
  }

  /**
   * Handles player input for moving their character in the game world.
   *
   * @param direction The direction of the movement ("up", "down", "left", "right").
   * @param isP1      A boolean indicating whether the input is for player 1.
   */

  public void handleDirectionInput(String direction, boolean isP1) {
    if (isP1) {
      switch (state1) {
        case Game -> handlePlayerMovement(direction, player1);
        case MainMenu -> handleCharacterChange(direction, mainMenuController1);
        default -> { }
      }
    } else {
      switch (state2) {
        case Game -> handlePlayerMovement(direction, player2);
        case MainMenu -> handleCharacterChange(direction, mainMenuController2);
        default -> { }
      }
    }
  }

  private void handlePlayerMovement(String direction, Player player) {
    List<Entity> borders = new ArrayList<>();
    Entity playerEntity;
    if (player.isP1()) {
      borders = borders1;
      playerEntity = player.getEntity1();
    } else {
      borders = borders2;
      playerEntity = player.getEntity2();
    }
    if (direction.equals("right")) {
      if (!checkBorderCollision(Direction.RIGHT, playerEntity, borders)
          && !checkAssetCollision(Direction.RIGHT, player)) {
        player.getEntity1().translateX(MOVEMENT_SPEED);
        player.getEntity2().translateX(MOVEMENT_SPEED);
      }
    } else if (direction.equals("left")) {
      if (!checkBorderCollision(Direction.LEFT, playerEntity, borders)
          && !checkAssetCollision(Direction.LEFT, player)
      ) {
        player.getEntity1().translateX(-MOVEMENT_SPEED);
        player.getEntity2().translateX(-MOVEMENT_SPEED);
      }
    } else if (direction.equals("up")) {
      if (!checkBorderCollision(Direction.UP, playerEntity, borders)
          && !checkAssetCollision(Direction.UP, player)
      ) {
        player.getEntity1().translateY(-MOVEMENT_SPEED);
        player.getEntity2().translateY(-MOVEMENT_SPEED);
      }
    } else if (direction.equals("down")) {
      if (!checkBorderCollision(Direction.DOWN, playerEntity, borders)
          && !checkAssetCollision(Direction.DOWN, player)
      ) {
        player.getEntity1().translateY(MOVEMENT_SPEED);
        player.getEntity2().translateY(MOVEMENT_SPEED);
      }
    }
  }

  private void handleCharacterChange(String direction, MainMenuController mainMenuController) {
    if (direction.equals("right")) {
      mainMenuController.changeImage(true);
    } else if (direction.equals("left")) {
      mainMenuController.changeImage(false);
    }
  }

  /**
   * Handles player button input.
   *
   * @param button The button that was pressed.
   * @param isP1   A boolean indicating whether the input is for player 1.
   */

  public void handleButtonInput(String button, boolean isP1) {
    if (isP1) {
      switch (state1) {

        case MainMenu -> {
          if (button.equals("1")) {
            p1Ready = true;
            mainMenuController1.changeStartButton();
            if (p1Ready && p2Ready) {
              initRound();
            }
          } else if (button.equals("2") && !p1Ready) {
            changeState1(ScreenState.Tutorial);
            tutorialController1.startVideo();
          } else if (button.equals("3") && !p1Ready) {
            changeState1(ScreenState.LanguageSelect);
          }
        }
        case LanguageSelect -> {
          selectLocale(button);
        }
        case Tutorial -> {
          tutorialController1.dispose();
          changeState1(ScreenState.MainMenu);
        }
        case Game -> {
          checkForDoor(player1);
          checkForQuiz(player1);
          loadRoom();
        }
        case Quiz -> {
          var currentDevice = getP1Device();
          var questiontype = currentDevice.getQuestion().getType();
          if (questiontype == QuestionType.Choice) {
            checkAnswer(currentDevice, button, "1");
          } else if (questiontype == QuestionType.Sorting) {
            addButtonPress(button, "1", currentDevice);
          }
        }
        case QuizResult -> {
          finishExplanation("1");
        }
        case Leaderboard -> {
          restartGame();
        }
        default -> throw new IllegalStateException("Unexpected value: " + state1);
      }

    } else {
      switch (state2) {

        case MainMenu -> {
          if (button.equals("1")) {
            p2Ready = true;
            mainMenuController2.changeStartButton();
            if (p1Ready && p2Ready) {
              initRound();
            }
          } else if (button.equals("2") && !p2Ready) {
            changeState2(ScreenState.Tutorial);
            tutorialController2.startVideo();
          } else if (button.equals("3") && !p2Ready) {
            changeState2(ScreenState.LanguageSelect);
          }
        }
        case LanguageSelect -> {
          selectLocale(button);
        }
        case Tutorial -> {
          tutorialController2.dispose();
          changeState2(ScreenState.MainMenu);
        }
        case Game -> {
          checkForDoor(player2);
          checkForQuiz(player2);
          loadRoom();
        }
        case Quiz -> {
          var currentDevice = getP2Device();
          var questiontype = currentDevice.getQuestion().getType();
          if (questiontype == QuestionType.Choice) {
            checkAnswer(currentDevice, button, "2");
          } else if (questiontype == QuestionType.Sorting) {
            addButtonPress(button, "2", currentDevice);
          }
        }
        case QuizResult -> {
          finishExplanation("2");
        }
        case Leaderboard -> {
          restartGame();
        }
        default -> throw new IllegalStateException("Unexpected value: " + state2);
      }
    }
  }

  private void initRound() {
    changeState1(ScreenState.Game);
    changeState2(ScreenState.Game);
    addUINode(gameInfoUiRoot1, WINDOW_WIDTH - 100, 0);
    addUINode(gameInfoUiRoot2, (2 * WINDOW_WIDTH) - 100, 0);
    mainMenuController1.resetButtons();
    mainMenuController2.resetButtons();
    startGame();
  }

  private void selectLocale(String buttonInput) {
    switch (buttonInput) {
      case "1" -> resourceBundle = ResourceBundle.getBundle("strings/bundle", Locale.GERMAN);
      case "2" -> resourceBundle = ResourceBundle.getBundle("strings/bundle", Locale.ENGLISH);
      case "3" -> resourceBundle = ResourceBundle.getBundle("strings/bundle", Locale.FRENCH);
      default -> throw new IllegalStateException("Unexpected value: " + state2);
    }
    getGameScene().clearUINodes();
    initGame();
  }

  private void restartGame() {
    getGameScene().clearUINodes();
    ArrayList<Entity> entities = (ArrayList<Entity>) getGameWorld().getEntities().clone();
    getGameWorld().removeEntities(entities);
    timer.cancel();
    fxglTimer.clear();
    p1Ready = false;
    quiz1Active = false;
    p2Ready = false;
    quiz2Active = false;
    gameFinished = false;
    initGame();
  }

  /**
   * Loads the assets for the current room in the game world.
   */

  public void loadRoom() {
    var rooms = getRooms();
    for (int i = 0; i < rooms.length; i++) {
      var assets = rooms[i].getAssets();
      rooms[i].setRoom1Background(false);
      rooms[i].setRoom2Background(false);
      for (int j = 0; j < assets.length; j++) {
        assets[j].getEntity1().setType(EntityType.DISABLED);
        assets[j].getEntity1().setVisible(false);
        assets[j].getEntity2().setType(EntityType.DISABLED);
        assets[j].getEntity2().setVisible(false);
      } //set everything invisible
    }
    if (!(previousAssets1.length < 1)) {
      clearAssets(previousAssets1);
    }
    getPlayer1().getCurrentRoom().setRoom1Background(true);
    var assets1 = getPlayer1().getCurrentRoom().getAssets();
    this.previousAssets1 = assets1.clone();
    for (int i = 0; i < assets1.length; i++) {
      assets1[i].getEntity1().setVisible(true);

      if (assets1[i] instanceof Device currentDevice) {
        assets1[i].getEntity1().setType(EntityType.DEVICE);

        if (!currentDevice.isCompleted() && currentDevice.isInteractable(player1)) {
          currentDevice.setMarker(player1.getCurrentRoom().getName(),
              player2.getCurrentRoom().getName(), 1, player1, player2);
        }

      } else {
        assets1[i].getEntity1().setType(EntityType.ASSET);
      }

    }
    getPlayer2().getCurrentRoom().setRoom2Background(true);
    var assets2 = getPlayer2().getCurrentRoom().getAssets();
    this.previousAssets2 = assets2.clone();
    for (int i = 0; i < assets2.length; i++) {
      assets2[i].getEntity2().setVisible(true);
      if (assets2[i] instanceof Device currentDevice) {
        assets2[i].getEntity2().setType(EntityType.DEVICE);

        if (!currentDevice.isCompleted()) {
          currentDevice.setMarker(player2.getCurrentRoom().getName(),
              player1.getCurrentRoom().getName(), 2, player1, player2);
        }

      } else {
        assets2[i].getEntity2().setType(EntityType.ASSET);
      }
    }

    openDoor();
  }

  /**
   * All Devices are set invisible.
   */

  private void clearAssets(Asset[] assets) {
    for (Asset asset : assets) {
      if (asset instanceof Device device) {
        device.getMarker1().setVisible(false);
        device.getMarker2().setVisible(false);
      }
    }
  }

  /**
   * Creates a door entity for the game.
   */

  private void createDoor() {
    door1 = entityBuilder()
        .type(EntityType.DOOR)
        .at(WINDOW_WIDTH / 2 - DOOR_WIDTH / 2, 0)
        .viewWithBBox("door_closed.png")
        .with(new CollidableComponent(true))
        .buildAndAttach();
    door2 = door1.copy();
    door2.translateX(WINDOW_WIDTH);
    getGameWorld().addEntity(door2);
  }

  /**
   * Opens the door entity in the game world.
   */

  private void openDoor() {
    door1.setVisible(true);
    door2.setVisible(true);
    changeDoorEntity(player1.getCurrentRoom().isCompleted(), door1);
    changeDoorEntity(player2.getCurrentRoom().isCompleted(), door2);
    displayPlayer();
  }

  private void changeDoorEntity(boolean isRoomCompleted, Entity door) {
    if (isRoomCompleted) {
      door.getViewComponent().clearChildren();
      door.getViewComponent().addChild(texture("door_open.png"));
    } else {
      door.getViewComponent().clearChildren();
      door.getViewComponent().addChild(texture("door_closed.png"));
    }
  }

  /**
   * displays the players.
   */

  private void displayPlayer() {
    //remove and add all player entities,so that they are displayed in the foreground.
    getGameWorld().removeEntity(player1.getEntity1());
    getGameWorld().addEntity(player1.getEntity1());
    getGameWorld().removeEntity(player1.getEntity2());
    getGameWorld().addEntity(player1.getEntity2());
    getGameWorld().removeEntity(player2.getEntity1());
    getGameWorld().addEntity(player2.getEntity1());
    getGameWorld().removeEntity(player2.getEntity2());
    getGameWorld().addEntity(player2.getEntity2());
    if (player1.getCurrentRoom() == player2.getCurrentRoom()) {
      player1.getEntity1().setVisible(true);
      player2.getEntity1().setVisible(true);
      player1.getEntity2().setVisible(true);
      player2.getEntity2().setVisible(true);
    } else {
      player1.getEntity1().setVisible(true);
      player2.getEntity2().setVisible(true);
      player1.getEntity2().setVisible(false);
      player2.getEntity1().setVisible(false);
    }


  }

  /**
   * Creates the user interface for game information, initializing two instances of
   * GameInfoUiController and loading the corresponding FXML file for display. This method sets up
   * the UI for displaying game-related information.
   */

  private void createGameInfoUi() {
    gameInfoUiController1 = new GameInfoUiController();
    gameInfoUiController2 = new GameInfoUiController();

    File file = new File(FXML_PATH + "gameInfoUI.fxml");
    URL url;
    try {
      url = file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    FXMLLoader loader1 = new FXMLLoader(url, resourceBundle);
    FXMLLoader loader2 = new FXMLLoader(url, resourceBundle);
    loader1.setController(gameInfoUiController1);
    loader2.setController(gameInfoUiController2);
    try {
      gameInfoUiRoot1 = loader1.load();
      gameInfoUiRoot2 = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createLeaderboardUi() {
    leaderboardController1 = new LeaderboardController(resourceBundle);
    leaderboardController2 = new LeaderboardController(resourceBundle);

    File file = new File(FXML_PATH + "leaderboard.fxml");
    URL url;
    try {
      url = file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    FXMLLoader loader1 = new FXMLLoader(url, resourceBundle);
    FXMLLoader loader2 = new FXMLLoader(url, resourceBundle);
    loader1.setController(leaderboardController1);
    loader2.setController(leaderboardController2);
    try {
      leaderboardRoot1 = loader1.load();
      leaderboardRoot2 = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates the user interface for the quiz, initializing two instances of QuizUiController and
   * loading the corresponding FXML file for display. This method sets up the UI for conducting
   * quizzes during the game.
   */

  private void createQuizUi() {
    quizUiController1 = new QuizUiController(resourceBundle);
    quizUiController2 = new QuizUiController(resourceBundle);

    File file = new File(FXML_PATH + "quizUI.fxml");
    URL url;
    try {
      url = file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    FXMLLoader loader1 = new FXMLLoader(url, resourceBundle);
    FXMLLoader loader2 = new FXMLLoader(url, resourceBundle);
    loader1.setController(quizUiController1);
    loader2.setController(quizUiController2);
    try {
      quizRoot1 = loader1.load();
      quizRoot2 = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createTutorialUi() {
    tutorialController1 = new TutorialController();
    tutorialController2 = new TutorialController();
    File file = new File(FXML_PATH + "tutorialUI.fxml");
    URL url;
    try {
      url = file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    FXMLLoader loader1 = new FXMLLoader(url, resourceBundle);
    FXMLLoader loader2 = new FXMLLoader(url, resourceBundle);
    loader1.setController(tutorialController1);
    loader2.setController(tutorialController2);
    try {
      tutorialRoot1 = loader1.load();
      tutorialRoot2 = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createLanguageSelect() {
    File file = new File(FXML_PATH + "languageSelectUI.fxml");
    URL url;
    try {
      url = file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    FXMLLoader loader1 = new FXMLLoader(url, resourceBundle);
    FXMLLoader loader2 = new FXMLLoader(url, resourceBundle);
    try {
      languageSelectRoot1 = loader1.load();
      languageSelectRoot2 = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates the main menu user interface, initializing two instances of MainMenuController and
   * loading the corresponding FXML file for display. This method sets up the main menu UI for
   * interacting with game options.
   */

  private void createMainMenu() {
    mainMenuController1 = new MainMenuController();
    mainMenuController2 = new MainMenuController();

    File file = new File(FXML_PATH + "mainMenu.fxml");
    URL url;
    try {
      url = file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    FXMLLoader loader1 = new FXMLLoader(url, resourceBundle);
    FXMLLoader loader2 = new FXMLLoader(url, resourceBundle);
    loader1.setController(mainMenuController1);
    loader2.setController(mainMenuController2);
    try {
      mainMenuRoot1 = loader1.load();
      mainMenuRoot2 = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a quiz for a specific player and updates the game state accordingly.
   *
   * @param isP1 true if the quiz is for player 1, false otherwise
   * @param q    the Question object representing the quiz question
   */

  private void loadQuiz(boolean isP1, Question q) {
    if (isP1) {
      quizUiController1.setQuiz(q);
      changeState1(ScreenState.Quiz);
    } else {
      quizUiController2.setQuiz(q);
      changeState2(ScreenState.Quiz);
    }
  }

  /**
   * Retrieves the current energy consumption of all devices in the rooms.
   *
   * @return the total energy consumption
   */

  public double getCurrentEnergyConsumption() {
    double sum = 0;
    for (int i = 0; i < rooms.length; i++) {
      Asset[] assets = rooms[i].getAssets();
      for (int k = 0; k < assets.length; k++) {
        if (assets[k] instanceof Device) {
          sum += ((Device) assets[k]).getEnergyConsumption();
        }
      }
    }
    return sum;
  }

  /**
   * Retrieves the player object representing player 2.
   *
   * @return the Player object for player 2
   */

  public Player getPlayer2() {
    return player2;
  }

  /**
   * Retrieves the player object representing player 1.
   *
   * @return the Player object for player 1
   */

  public Player getPlayer1() {
    return player1;
  }

  /**
   * Retrieves the array of rooms in the game.
   *
   * @return the array of Room objects
   */

  public Room[] getRooms() {
    return rooms;
  }


  /**
   * Changes the state of the UI for player 1 based on the specified ScreenState.
   *
   * @param screenState the new ScreenState for player 1's UI
   */

  public void changeState1(ScreenState screenState) {
    switch (screenState) {
      case MainMenu -> {
        Platform.runLater(() -> {
          removeUINode(mainMenuRoot1);
          addUINode(mainMenuRoot1);
          removeUINode(languageSelectRoot1);
          removeUINode(tutorialRoot1);
          if (p1Ready) {
            mainMenuController1.changeStartButton();
          }
        });

      }
      case Tutorial -> {
        Platform.runLater(() -> {
          removeUINode(tutorialRoot1);
          addUINode(tutorialRoot1);
          removeUINode(mainMenuRoot1);
        });
      }
      case Game -> {
        Platform.runLater(() -> {
          removeUINode(mainMenuRoot1);
          removeUINode(languageSelectRoot1);
          removeUINode(quizRoot1);
        });

      }
      case Quiz -> {
        Platform.runLater(() -> {
          removeUINode(quizRoot1);
          addUINode(quizRoot1, QUIZ_FRAME_OFFSET_X, QUIZ_FRAME_OFFSET_Y);
        });

      }
      case QuizResult -> {
      }
      case Leaderboard -> {
        Platform.runLater(() -> {
          leaderboardController1.setTableContent(getScores(), currentEntry);
          removeUINode(leaderboardRoot1);
          addUINode(leaderboardRoot1);
        });
      }
      case LanguageSelect -> {
        Platform.runLater(() -> {
          removeUINode(languageSelectRoot1);
          addUINode(languageSelectRoot1);
          removeUINode(mainMenuRoot1);
        });

      }
      default -> throw new IllegalStateException("Unexpected value: " + screenState);
    }
    state1 = screenState;
  }

  /**
   * Changes the state of the UI for player 2 based on the specified ScreenState.
   *
   * @param screenState the new ScreenState for player 2's UI
   */

  public void changeState2(ScreenState screenState) {
    switch (screenState) {

      case MainMenu -> {
        Platform.runLater(() -> {
          removeUINode(mainMenuRoot2);
          addUINode(mainMenuRoot2, WINDOW_WIDTH, 0);
          removeUINode(languageSelectRoot2);
          removeUINode(tutorialRoot2);
          if (p2Ready) {
            mainMenuController2.changeStartButton();
          }
        });

      }
      case Tutorial -> {
        Platform.runLater(() -> {
          removeUINode(tutorialRoot2);
          addUINode(tutorialRoot2, WINDOW_WIDTH, 0);
          removeUINode(mainMenuRoot2);
        });
      }
      case Game -> {
        Platform.runLater(() -> {
          removeUINode(mainMenuRoot2);
          removeUINode(quizRoot2);
          removeUINode(languageSelectRoot2);
        });

      }
      case Quiz -> {
        Platform.runLater(() -> {
          removeUINode(quizRoot2);
          addUINode(quizRoot2, WINDOW_WIDTH + QUIZ_FRAME_OFFSET_X, QUIZ_FRAME_OFFSET_Y);
        });

      }
      case QuizResult -> {
      }
      case Leaderboard -> {
        Platform.runLater(() -> {
          leaderboardController2.setTableContent(getScores(), currentEntry);
          removeUINode(leaderboardRoot2);
          addUINode(leaderboardRoot2, WINDOW_WIDTH, 0);
        });
      }
      case LanguageSelect -> {
        Platform.runLater(() -> {
          removeUINode(languageSelectRoot2);
          addUINode(languageSelectRoot2, WINDOW_WIDTH, 0);
          removeUINode(mainMenuRoot2);
        });

      }
      default -> throw new IllegalStateException("Unexpected value: " + screenState);
    }
    state2 = screenState;
  }

  /**
   * Checks for collision between two hitboxes.
   *
   * @param h1 the first Hitbox
   * @param h2 the second Hitbox
   * @return true if a collision is detected, false otherwise
   */

  public boolean checkCollision(Hitbox h1, Hitbox h2) {
    if ((h1.leftBorder >= h2.leftBorder && h1.leftBorder <= h2.rightBorder)
        || (h1.rightBorder <= h2.rightBorder && h1.rightBorder >= h2.leftBorder)
        || (h1.leftBorder <= h2.leftBorder && h1.rightBorder >= h2.rightBorder)) {
      return (h1.topBorder <= h2.bottomBorder && h1.topBorder >= h2.topBorder)
          || (h1.bottomBorder <= h2.bottomBorder && h1.bottomBorder >= h2.topBorder)
          || (h1.topBorder <= h2.topBorder && h1.bottomBorder >= h2.bottomBorder);

    }
    return false;
  }

  private boolean checkAssetCollision(Direction direction, Player player) {

    BoundingBoxComponent playerBox;
    if (player.isP1()) {
      playerBox = player.getEntity1().getBoundingBoxComponent();
    } else {
      playerBox = player.getEntity2().getBoundingBoxComponent();
    }
    var assets = player.getCurrentRoom().getAssets();
    for (Asset a : assets) {
      BoundingBoxComponent boxAsset;
      if (player.isP1()) {
        boxAsset = a.getEntity1().getBoundingBoxComponent();
      } else {
        boxAsset = a.getEntity2().getBoundingBoxComponent();
      }

      if (checkBoundingBoxCollision(direction, boxAsset, playerBox)) {
        return true;
      }
    }
    return false;
  }

  private boolean checkBorderCollision(Direction direction, Entity player, List<Entity> borders) {
    var borderTop = borders.get(0);
    var borderBot = borders.get(1);
    var borderL = borders.get(2);
    var borderR = borders.get(3);

    var playerBox = player.getBoundingBoxComponent();
    var isColliding = false;
    switch (direction) {
      case UP -> isColliding = checkBoundingBoxCollision(direction,
          borderTop.getBoundingBoxComponent(), playerBox);
      case DOWN -> isColliding = checkBoundingBoxCollision(direction,
          borderBot.getBoundingBoxComponent(), playerBox);
      case LEFT -> isColliding = checkBoundingBoxCollision(direction,
          borderL.getBoundingBoxComponent(), playerBox);
      case RIGHT -> isColliding = checkBoundingBoxCollision(direction,
          borderR.getBoundingBoxComponent(), playerBox);
      default -> {
        return false;
      }
    }
    return isColliding;
  }

  private boolean checkBoundingBoxCollision(Direction direction,
                                            BoundingBoxComponent box,
                                            BoundingBoxComponent playerBox) {

    double playerNextX = playerBox.transform.getX();
    double playerNextY = playerBox.transform.getY();

    // Calculate the player's next position based on the direction
    switch (direction) {
      case UP:
        playerNextY -= MOVEMENT_SPEED;  // Move up by 1 unit
        break;
      case DOWN:
        playerNextY += MOVEMENT_SPEED;  // Move down by 1 unit
        break;
      case LEFT:
        playerNextX -= MOVEMENT_SPEED;  // Move left by 1 unit
        break;
      case RIGHT:
        playerNextX += MOVEMENT_SPEED;  // Move right by 1 unit
        break;
      default: break;
    }

    // Check for collision
    boolean exOverlap = playerNextX < box.transform.getX() + box.getWidth() && playerNextX
        + playerBox.getWidth() > box.transform.getX();
    boolean whyOverlap = playerNextY < box.transform.getY() + box.getHeight() && playerNextY
        + playerBox.getHeight() > box.transform.getY();
    if (exOverlap && whyOverlap) {
      if (direction.equals(Direction.LEFT)) {
        return !(box.transform.getX() - (playerNextX + playerBox.getWidth()) > 0);
      } else if (direction.equals(Direction.RIGHT)) {
        return ((box.transform.getX() + box.getWidth()) - playerNextX) > 0;
      } else if (direction.equals(Direction.UP)) {
        return (((box.transform.getY() + box.getHeight()) - playerNextY) > 0);
      } else if (direction.equals(Direction.DOWN)) {
        return !((box.transform.getY() - (playerNextY + playerBox.getHeight())) > 0);
      }
    }
    return false;
  }

  /**
   * Checks if there is a device at the players location.
   *
   * @param p a Player object
   */

  public void checkForQuiz(Player p) {
    var playerHitbox = p.getHitBox();

    Asset[] assets = p.getCurrentRoom().getAssets();
    //checks for the position of every asset
    for (int i = 0; i < assets.length; i++) {
      if (assets[i] instanceof Device && ((Device) assets[i]).isInteractable(p)) {
        var assetInteractionHitbox = ((Device) assets[i]).getInteractionHitbox();
        if (checkCollision(playerHitbox, assetInteractionHitbox)) {
          if (p.isP1()) {
            quiz1Active = true;
            loadQuiz(true, ((Device) assets[i]).getQuestion());
            ((Device) assets[i]).setInUse(true);
            ((Device) assets[i]).setLastInteractor(p);
            ((Device) assets[i]).addInteractor(p);
            p1Device = (Device) assets[i];
          } else {
            quiz2Active = true;
            loadQuiz(false, ((Device) assets[i]).getQuestion());
            ((Device) assets[i]).setInUse(true);
            ((Device) assets[i]).setLastInteractor(p);
            ((Device) assets[i]).addInteractor(p);
            p2Device = (Device) assets[i];
          }
          break;
        }
      }
    }
  }

  /**
   * Checks if the player is interacting with a door to move between rooms.
   *
   * @param p the Player object interacting with the door
   */

  public void checkForDoor(Player p) {
    Room croom = p.getCurrentRoom();
    Room[] rooms = this.rooms;
    int positionCurrentRoom = croom.getPosition();

    Hitbox playerHitbox = p.getHitBox();
    Hitbox doorBottomHitbox =
        new Hitbox(DOOR_BOTTOM_HITBOX.getLeft(), DOOR_BOTTOM_HITBOX.getRight(),
            DOOR_BOTTOM_HITBOX.getBottom(), DOOR_BOTTOM_HITBOX.getTop());


    if ((positionCurrentRoom - 1) >= 0) {
      if (checkCollision(playerHitbox, doorBottomHitbox)) {
        p.setCurrentRoom(rooms[positionCurrentRoom - 1]);
        p.getEntity1().setX(DOOR_TOP_HITBOX.leftBorder);
        p.getEntity2().setX(DOOR_TOP_HITBOX.leftBorder + WINDOW_WIDTH);
        p.getEntity1().setY(50);
        p.getEntity2().setY(50);
        updateRoomUi(p);
      }
    }
    Hitbox doorTopHitbox = new Hitbox(DOOR_TOP_HITBOX.getLeft(), DOOR_TOP_HITBOX.getRight(),
        DOOR_TOP_HITBOX.getBottom(), DOOR_TOP_HITBOX.getTop());
    if ((positionCurrentRoom + 1) < rooms.length && p.getCurrentRoom().isCompleted()) {
      if (checkCollision(playerHitbox, doorTopHitbox)) {
        p.setCurrentRoom(rooms[positionCurrentRoom + 1]);
        p.getEntity1().setX(DOOR_BOTTOM_HITBOX.leftBorder);
        p.getEntity2().setX(DOOR_BOTTOM_HITBOX.leftBorder + WINDOW_WIDTH);
        p.getEntity1().setY(WINDOW_HEIGHT - HEIGHT_DIFFERENCE / 2 - PLAYER_HEIGHT);
        p.getEntity2().setY(WINDOW_HEIGHT - HEIGHT_DIFFERENCE / 2 - PLAYER_HEIGHT);
        updateRoomUi(p);
      }
    }
    if ((positionCurrentRoom + 1) == rooms.length && p.getCurrentRoom().isCompleted()) {
      //todo end the game
    }
  }

  /**
   * Updates the room display in the game information UI based on the player's current room.
   *
   * @param p the Player object whose room display needs updating
   */

  private void updateRoomUi(Player p) {
    if (p.isP1()) {
      gameInfoUiController1.updateRoomDisplay(rooms, p.getCurrentRoom());
    } else {
      gameInfoUiController2.updateRoomDisplay(rooms, p.getCurrentRoom());
    }
  }


  /**
   * Handles button press events during a quiz session.
   *
   * @param buttonPress the button pressed
   * @param player      the player responsible for the button press
   * @param device      the Device object associated with the quiz
   */

  private void addButtonPress(String buttonPress, String player, Device device) {
    if (player.equals("1")) {
      if (!buttonOrder1.contains(buttonPress)) {
        buttonOrder1 += buttonPress;
        quizUiController1.showButtonPress(buttonOrder1);
        if (buttonOrder1.length() == 3) {
          checkAnswer(device, buttonOrder1, "1");
          buttonOrder1 = "";
        }

      }
    } else if (player.equals("2")) {
      if (!buttonOrder2.contains(buttonPress)) {
        buttonOrder2 += buttonPress;
        quizUiController2.showButtonPress(buttonOrder2);
        if (buttonOrder2.length() == 3) {
          checkAnswer(device, buttonOrder2, "2");
          buttonOrder2 = "";
        }

      }
    }
  }

  /**
   * Checks if the answer provided during a quiz session is correct.
   *
   * @param device the Device object associated with the quiz
   * @param answer the answer provided by the player
   * @param player the player attempting the quiz
   */

  private void checkAnswer(Device device, String answer, String player) {
    var isAnswerCorrect = device.getQuestion().checkAnswer(answer);
    device.changeState(isAnswerCorrect);
    if (player.equals("1")) {
      quiz1Correct = isAnswerCorrect;
      changeState1(ScreenState.QuizResult);
      player1.getCurrentRoom().checkCompleted();
      quizUiController1.showExplanation(
          device.getQuestion().getExplanation(answer, isAnswerCorrect),
          isAnswerCorrect, answer);
    } else if (player.equals("2")) {
      quiz2Correct = isAnswerCorrect;
      changeState2(ScreenState.QuizResult);
      player2.getCurrentRoom().checkCompleted();
      quizUiController2.showExplanation(
          device.getQuestion().getExplanation(answer, isAnswerCorrect),
          isAnswerCorrect, answer);
    }
    gameInfoUiController1.updateRoomDisplay(rooms, player1.getCurrentRoom());
    gameInfoUiController2.updateRoomDisplay(rooms, player2.getCurrentRoom());
    openDoor();
  }

  /**
   * Finishes displaying the quiz result and updates game states accordingly.
   *
   * @param player the player for whom the quiz result is being finalized
   */

  private void finishExplanation(String player) {
    if (player.equals("1")) {
      changeState1(ScreenState.Game);
      setQuiz1Active(false);
      p1Device.setInUse(false);
    } else if (player.equals("2")) {
      changeState2(ScreenState.Game);
      setQuiz2Active(false);
      p2Device.setInUse(false);
    }
  }


  /**
   * Checks if quiz 1 is currently active.
   *
   * @return true if quiz 1 is active, otherwise false
   */

  public boolean isQuiz1Active() {
    return quiz1Active;
  }

  /**
   * Checks if quiz 2 is currently active.
   *
   * @return true if quiz 2 is active, otherwise false
   */

  public boolean isQuiz2Active() {
    return quiz2Active;
  }

  /**
   * Sets the active state of quiz 1.
   *
   * @param quiz1Active true to activate quiz 1, false otherwise
   */

  public void setQuiz1Active(boolean quiz1Active) {
    this.quiz1Active = quiz1Active;
  }

  /**
   * Sets the active state of quiz 2.
   *
   * @param quiz2Active true to activate quiz 2, false otherwise
   */

  public void setQuiz2Active(boolean quiz2Active) {
    this.quiz2Active = quiz2Active;
  }

  /**
   * Retrieves the device associated with player 1.
   *
   * @return the Device object associated with player 1
   */

  public Device getP1Device() {
    return p1Device;
  }

  /**
   * Retrieves the device associated with player 2.
   *
   * @return the Device object associated with player 2
   */

  public Device getP2Device() {
    return p2Device;
  }

  /**
   * Saves the final game score with a randomly generated team name. The score is based on the
   * remaining energy in the game.
   */

  private void saveScore() {
    Random rand = new Random();
    try {
      ArrayList<String[]> scores = new ArrayList<>();

      if (Files.exists(SCORE_PATH)) {
        scores = new ArrayList<>(FileReader.readScoreCsv().stream()
            .map(s -> new String[] {s.getTeamName(), "" + s.getPercentage()}).toList()
        );
      }
      List<String> names = FileReader.readNamesCsv(NAMES_PATH);
      List<String[]> finalScores = scores;
      names = names.stream()
          .filter(n -> finalScores.stream().noneMatch(s -> s[0].equals(n))).toList();
      var score = (double) 100 / STARTING_ENERGY * remainingEnergy;
      DecimalFormat df = new DecimalFormat("#.###");
      df.setRoundingMode(RoundingMode.CEILING);

      currentEntry = new LeaderboardEntry(
          ">" + SCORE_SIZE,
          names.get(rand.nextInt(names.size() - 1)),
          df.format(score)
      );
      scores.add(new String[] {currentEntry.getTeamName(), currentEntry.getPercentage()});
      scores = new ArrayList<>(scores.stream()
          .sorted(comparingDouble(d -> -Double.parseDouble(d[1])))
          .limit(SCORE_SIZE)
          .toList()
      );
      List<String[]> dataLines = new ArrayList<>(scores);
      FileWriter.writeScoresToCsv(dataLines, SCORE_PATH);

      changeState1(ScreenState.Leaderboard);
      changeState2(ScreenState.Leaderboard);
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }


  /**
   * Returns a list of all currently saved Leaderboard Entries.
   *
   * @return A list of all Entries in the leaderboard csv
   */
  public List<LeaderboardEntry> getScores() {
    try {
      return FileReader.readScoreCsv();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }


  /**
   * Checks if all rooms in the game have been completed.
   *
   * @return true if all rooms are completed, otherwise false
   */

  public boolean isFinished() {
    var numComplete = 0;
    for (Room room : rooms
    ) {
      if (room.isCompleted()) {
        numComplete++;
      }
    }
    if (numComplete == rooms.length) {
      return true;
    }
    return false;
  }
}

