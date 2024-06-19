package energiequiz.io;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import energiequiz.components.Joystick;
import energiequiz.components.base.Pin;
import energiequiz.controller.Game;
import java.time.Duration;
import java.util.Properties;
import javafx.application.Platform;

/**
 * Handles input from various digital inputs such as buttons and joysticks.
 */
public class InputHandler {
  private DigitalInput button1Green;
  private DigitalInput button2Green;
  private DigitalInput button1Blue;
  private DigitalInput button2Blue;
  private DigitalInput button1Red;
  private DigitalInput button2Red;

  private Context pi4j;

  private Game game;

  /**
   * Constructs an InputHandler with the specified game instance.
   *
   * @param game the game instance
   */
  public InputHandler(Game game) {
    this.game = game;
    final var piGpio = PiGpio.newNativeInstance();
    pi4j = Pi4J.newContextBuilder().noAutoDetect().add(new RaspberryPiPlatform() {
      @Override
      protected String[] getProviders() {
        return new String[] {};
      }
    }).add(PiGpioDigitalInputProvider.newInstance(piGpio),
        PiGpioDigitalOutputProvider.newInstance(piGpio), PiGpioPwmProvider.newInstance(piGpio),
        PiGpioSerialProvider.newInstance(piGpio), PiGpioSpiProvider.newInstance(piGpio),
        LinuxFsI2CProvider.newInstance()).build();
    setGreenButton1();
    setGreenButton2();
    setBlueButton1();
    setBlueButton2();
    setRedButton1();
    setRedButton2();
    setupJoyStick1();
    setupJoyStick2();
  }

  /**
   * Sets up the configuration for green button of player 1.
   */
  public void setGreenButton1() {
    Properties properties = new Properties();
    properties.put("id", "btn1-green");
    properties.put("address", 19);
    properties.put("pull", "UP");
    properties.put("name", "BUTTON1-GREEN");

    var config = DigitalInput.newConfigBuilder(pi4j).load(properties).build();
    button1Green = pi4j.din().create(config);

    button1Green.addListener(e -> {
      if (e.state() == DigitalState.LOW) {
        Platform.runLater(() -> game.handleButtonInput("1", true));
      }
    });
  }

  /**
   * Sets up the configuration for green button of player 2.
   */
  public void setGreenButton2() {
    Properties properties = new Properties();
    properties.put("id", "btn2-green");
    properties.put("address", 25);
    properties.put("pull", "UP");
    properties.put("name", "BUTTON2-GREEN");

    var config = DigitalInput.newConfigBuilder(pi4j).load(properties).build();
    button2Green = pi4j.din().create(config);

    button2Green.addListener(e -> {
      if (e.state() == DigitalState.LOW) {
        Platform.runLater(() -> game.handleButtonInput("1", false));
      }
    });
  }

  /**
   * Sets up the configuration for blue button of player 1.
   */
  public void setBlueButton1() {
    Properties properties = new Properties();
    properties.put("id", "btn1-blue");
    properties.put("address", 20);
    properties.put("pull", "UP");
    properties.put("name", "BUTTON1-BLUE");

    var config = DigitalInput.newConfigBuilder(pi4j).load(properties).build();
    button1Blue = pi4j.din().create(config);

    button1Blue.addListener(e -> {
      if (e.state() == DigitalState.LOW) {
        Platform.runLater(() -> game.handleButtonInput("2", true));
      }
    });
  }

  /**
   * Sets up the configuration for blue button of player 2.
   */
  public void setBlueButton2() {
    Properties properties = new Properties();
    properties.put("id", "btn2-blue");
    properties.put("address", 23);
    properties.put("pull", "UP");
    properties.put("name", "BUTTON2-BLUE");

    var config = DigitalInput.newConfigBuilder(pi4j).load(properties).build();
    button2Blue = pi4j.din().create(config);

    button2Blue.addListener(e -> {
      if (e.state() == DigitalState.LOW) {
        Platform.runLater(() -> game.handleButtonInput("2", false));
      }
    });
  }

  /**
   * Sets up the configuration for red button of player 1.
   */
  public void setRedButton1() {
    Properties properties = new Properties();
    properties.put("id", "btn1-red");
    properties.put("address", 12);
    properties.put("pull", "UP");
    properties.put("name", "BUTTON1-RED");

    var config = DigitalInput.newConfigBuilder(pi4j).load(properties).build();
    button1Red = pi4j.din().create(config);

    button1Red.addListener(e -> {
      if (e.state() == DigitalState.LOW) {
        Platform.runLater(() -> game.handleButtonInput("3", true));
      }
    });
  }

  /**
   * Sets up the configuration for red button of player 2.
   */
  public void setRedButton2() {
    Properties properties = new Properties();
    properties.put("id", "btn2-red");
    properties.put("address", 22);
    properties.put("pull", "UP");
    properties.put("name", "BUTTON2-RED");

    var config = DigitalInput.newConfigBuilder(pi4j).load(properties).build();
    button2Red = pi4j.din().create(config);

    button2Red.addListener(e -> {
      if (e.state() == DigitalState.LOW) {
        Platform.runLater(() -> game.handleButtonInput("3", false));
      }
    });
  }

  /**
   * Sets up the configuration for joystick 1.
   */
  public void setupJoyStick1() {
    Joystick joystick = new Joystick(pi4j, Pin.PWM13, Pin.D21, Pin.D27, Pin.D26);
    joystick.whileNorth(() -> Platform.runLater(() ->
        game.handleDirectionInput("up", true)),   Duration.ofMillis(50));
    joystick.whileSouth(() -> Platform.runLater(() ->
        game.handleDirectionInput("down", true)), Duration.ofMillis(50));
    joystick.whileWest(() -> Platform.runLater(() ->
        game.handleDirectionInput("left", true)),  Duration.ofMillis(50));
    joystick.whileEast(() -> Platform.runLater(() ->
        game.handleDirectionInput("right", true)), Duration.ofMillis(50));
  }

  /**
   * Sets up the configuration for joystick 2.
   */
  public void setupJoyStick2() {
    Joystick joystick = new Joystick(pi4j, Pin.MISO, Pin.D6, Pin.D24, Pin.MOSI);
    joystick.whileNorth(() -> Platform.runLater(() ->
        game.handleDirectionInput("up", false)),   Duration.ofMillis(50));
    joystick.whileSouth(() -> Platform.runLater(() ->
        game.handleDirectionInput("down", false)), Duration.ofMillis(50));
    joystick.whileWest(() -> Platform.runLater(() ->
        game.handleDirectionInput("left", false)),  Duration.ofMillis(50));
    joystick.whileEast(() -> Platform.runLater(() ->
        game.handleDirectionInput("right", false)), Duration.ofMillis(50));
  }
}
