package energiequiz.controller;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
/**
 * Controller class for managing the tutorial UI.
 */

public class TutorialController {
  @FXML
  private MediaView mediaView;
  private MediaPlayer mediaPlayer;

  private final String pathToTutorial = "/assets/textures/tutorial.mp4";
  /**
   * Starts the tutorial video in the MediaView.
   */

  public void startVideo() {
    Media media = new Media(getClass().getResource(pathToTutorial).toExternalForm());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
    mediaView.setMediaPlayer(mediaPlayer);
  }

  /**
   * Disposes of the mediaPlayer.
   */
  public void dispose() {
    if (mediaPlayer != null) {
      mediaPlayer.dispose();
    }
  }
}
