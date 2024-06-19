package energiequiz.model;

import static energiequiz.model.Constants.TEXTURE_PATH;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Helper methods.
 */
public class Helper {
  /**
   * calculates the scaling based on size form json and image size.
   *
   * @param assetHeight height based on json
   * @param assetWidth height based on json
   * @param filename name of the image file
   * @return scaling as array [0] = scale x, [1] = scale y
   */
  public static double[] calculateScaling(int assetHeight, int assetWidth, String filename) {
    File file = new File(TEXTURE_PATH + filename);
    Image image = new Image(file.toURI().toString());
    ImageView imageView = new ImageView(image);
    double imgWidth = imageView.getBoundsInParent().getWidth();
    double imgHeight = imageView.getBoundsInParent().getHeight();
    var scaleX = assetWidth / imgWidth;
    var scaleY = assetHeight / imgHeight;
    return new double[]{ scaleX, scaleY };
  }

  /**
   * gets the size of the image of the specified file.
   *
   * @param filename name of the file to check
   * @return image width and height as double array
   */
  public static double[] getImageSize(String filename) {
    File file = new File(TEXTURE_PATH + filename);
    Image image = new Image(file.toURI().toString());
    ImageView imageView = new ImageView(image);
    double imgWidth = imageView.getBoundsInParent().getWidth();
    double imgHeight = imageView.getBoundsInParent().getHeight();
    return new double[]{imgWidth, imgHeight};
  }
}
