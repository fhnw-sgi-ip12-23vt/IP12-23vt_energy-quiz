package energiequiz.model;

/**
 * Enumeration representing different states of a device.
 */
public enum DeviceState {
  Angry(2, "angry"),
  On(1, "on"),
  Off(0, "off");

  private int value;
  private String name;

  private DeviceState(int value, String name) {
    this.value = value;
    this.name = name;
  }

  public int getValue() {
    return value;
  }

  public String getName() {
    return name;
  }
}
