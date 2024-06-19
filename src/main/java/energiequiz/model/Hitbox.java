package energiequiz.model;

/**
 * Represents a hitbox for collision detection.
 */
public class Hitbox {
  public final int rightBorder;
  public final int leftBorder;
  public final int bottomBorder;
  public final int topBorder;

  /**
   * Constructs a hitbox with the specified boundaries.
   *
   * @param maxX the maximum X-coordinate of the hitbox
   * @param minX the minimum X-coordinate of the hitbox
   * @param maxY the maximum Y-coordinate of the hitbox
   * @param minY the minimum Y-coordinate of the hitbox
   */
  public Hitbox(int maxX, int minX, int maxY, int minY) {
    this.leftBorder = minX;
    this.rightBorder = maxX;
    this.topBorder = minY;
    this.bottomBorder = maxY;
  }

  public int getRight() {
    return rightBorder;
  }

  public int getLeft() {
    return leftBorder;
  }

  public int getTop() {
    return bottomBorder;
  }

  public int getBottom() {
    return topBorder;
  }

  @Override
  public String toString() {
    return "Hitbox{"
        + "Right=" + rightBorder
        + ", Left=" + leftBorder
        + ", Bottom=" + bottomBorder
        + ", TOP=" + topBorder
        + '}';
  }
}
