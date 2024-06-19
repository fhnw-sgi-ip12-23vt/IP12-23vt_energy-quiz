package energiequiz.components;

import com.pi4j.context.Context;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInput;
import energiequiz.components.base.Component;
import energiequiz.components.base.Pin;
import java.time.Duration;

/**
 * Implementation of a joystick using 5 GPIO up, left, down, right and push  with Pi4J.
 */
public class Joystick extends Component {

  /**
   * Button component for joystick direction up.
   */
  private final SimpleButton buttonNorth;
  /**
   * Button component for joystick direction left.
   */
  private final SimpleButton buttonWest;
  /**
   * Button component for joystick direction down.
   */
  private final SimpleButton buttonSouth;
  /**
   * Button component for joystick direction right.
   */
  private final SimpleButton buttonEast;
  /**
   * Button component for joystick push.
   */
  private final SimpleButton buttonPush;

  /**
   * Creates a new joystick component with 5 custom GPIO address, a joystick with push button.
   *
   * @param pi4j      Pi4J context
   * @param addrNorth GPIO address of button up
   * @param addrWest  GPIO address of button left
   * @param addrSouth GPIO address of button down
   * @param addrEast  GPIO address of button right
   * @param addrPush  GPIO address of button push
   */
  public Joystick(Context pi4j, Pin addrNorth, Pin addrEast, Pin addrSouth, Pin addrWest,
                  Pin addrPush) {
    buttonNorth = new SimpleButton(pi4j, addrNorth, false);
    buttonWest = new SimpleButton(pi4j, addrWest, false);
    buttonSouth = new SimpleButton(pi4j, addrSouth, false);
    buttonEast = new SimpleButton(pi4j, addrEast, false);

    //joystick has push button
    if (addrPush != null) {
      buttonPush = new SimpleButton(pi4j, addrPush, false);
    } else {
      buttonPush = null;
    }
  }

  /**
   * Creates a new joystick component with 4 custom GPIO address, so no push button.
   *
   * @param pi4j      Pi4J context
   * @param addrNorth GPIO address of button up
   * @param addrWest  GPIO address of button left
   * @param addrSouth GPIO address of button down
   * @param addrEast  GPIO address of button right
   */
  public Joystick(Context pi4j, Pin addrNorth, Pin addrEast, Pin addrSouth, Pin addrWest) {
    this(pi4j, addrNorth, addrEast, addrSouth, addrWest, null);
  }

  /**
   * Checks if button north is currently pressed.
   *
   * @return true if button is pressed
   */
  public boolean isNorth() {
    return buttonNorth.isDown();
  }

  /**
   * Checks if button west is currently pressed.
   *
   * @return true if button is pressed
   */
  public boolean isWest() {
    return buttonWest.isDown();
  }

  /**
   * Checks if button south is currently pressed.
   *
   * @return true if button is pressed
   */
  public boolean isSouth() {
    return buttonSouth.isDown();
  }

  /**
   * Checks if button east is currently pressed.
   *
   * @return true if button is pressed
   */
  public boolean isEast() {
    return buttonEast.isDown();
  }

  /**
   * Checks if button push is currently pressed.
   *
   * @return true if button is pressed, False if button is not pressed or button does not exist.
   */
  public boolean isPushed() {
    return pushIsPresent() && buttonPush.isDown();
  }

  /**
   * Checks if button push is currently depressed (= NOT pressed).
   *
   * @return True if button is depressed, False if button is pressed od button does not exits
   */
  public boolean isCenter() {
    return buttonEast.isUp()
        && buttonWest.isUp()
        && buttonNorth.isUp()
        && buttonSouth.isUp()
        && (!pushIsPresent() || (pushIsPresent() && buttonPush.isUp()));
  }


  /**
   * Sets or disables the handler for the onDown event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void onNorth(Runnable task) {
    buttonNorth.onDown(task);
  }

  /**
   * Sets or disables the handler for the whilePressed event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void whileNorth(Runnable task, Duration delay) {
    buttonNorth.whilePressed(task, delay);
  }

  /**
   * Sets or disables the handler for the onDown event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void onWest(Runnable task) {
    buttonWest.onDown(task);
  }

  /**
   * Sets or disables the handler for the whilePressed event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void whileWest(Runnable task, Duration delay) {
    buttonWest.whilePressed(task, delay);
  }

  /**
   * Sets or disables the handler for the onDown event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void onSouth(Runnable task) {
    buttonSouth.onDown(task);
  }

  /**
   * Sets or disables the handler for the whilePressed event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void whileSouth(Runnable task, Duration delay) {
    buttonSouth.whilePressed(task, delay);
  }

  /**
   * Sets or disables the handler for the onDown event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void onEast(Runnable task) {
    buttonEast.onDown(task);
  }

  /**
   * Sets or disables the handler for the onDown event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void onCenter(Runnable task) {
    buttonNorth.onUp(task);
    buttonSouth.onUp(task);
    buttonEast.onUp(task);
    buttonWest.onUp(task);
  }

  /**
   * Sets or disables the handler for the whilePressed event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void whileEast(Runnable task, Duration delay) {
    buttonEast.whilePressed(task, delay);
  }

  /**
   * Sets or disables the handler for the onDown event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param handler Event handler to call or null to disable
   */
  public void onPushDown(Runnable handler) {
    if (pushIsPresent()) {
      buttonPush.onDown(handler);
    } else {
      throw new IllegalStateException(
          "No runnable on pushDown allowed if no push button is present");
    }
  }

  /**
   * Sets or disables the handler for the onUp event.
   * This event gets triggered whenever the button is no longer pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void onPushUp(Runnable task) {
    if (pushIsPresent()) {
      buttonPush.onUp(task);
    } else {
      throw new IllegalStateException(
          "No runnable on pushDown allowed if no push button is present");
    }
  }

  /**
   * Sets or disables the handler for the whilePressed event.
   * This event gets triggered whenever the button is pressed.
   * Only a single event handler can be registered at once.
   *
   * @param task Event handler to call or null to disable
   */
  public void whilePushed(Runnable task, Duration delay) {
    if (pushIsPresent()) {
      buttonPush.whilePressed(task, delay);
    } else {
      throw new IllegalStateException("No push button available, can't do a whilePushed");
    }
  }


  @Override
  public void reset() {
    buttonNorth.reset();
    buttonWest.reset();
    buttonSouth.reset();
    buttonEast.reset();
    if (pushIsPresent()) {
      buttonPush.reset();
    }
  }

  /**
   * checks if the JoyStick is in the initial state.
   *
   * @return  the initial state
   */
  public boolean isInInitialState() {
    return buttonNorth.isInInitialState()
        && buttonWest.isInInitialState()
        && buttonSouth.isInInitialState()
        && buttonEast.isInInitialState()
        && (!pushIsPresent() || (pushIsPresent() && buttonPush.isInInitialState()));
  }

  private boolean pushIsPresent() {
    return buttonPush != null;
  }


  // --------------- for testing --------------------

  MockDigitalInput mockNorth() {
    return buttonNorth.mock();
  }

  MockDigitalInput mockSouth() {
    return buttonSouth.mock();
  }

  MockDigitalInput mockEast() {
    return buttonEast.mock();
  }

  MockDigitalInput mockWest() {
    return buttonWest.mock();
  }

  MockDigitalInput mockPush() {
    if (pushIsPresent()) {
      return buttonPush.mock();
    } else {
      throw new IllegalStateException("No push button available, no DigitalInput available");
    }
  }
}
