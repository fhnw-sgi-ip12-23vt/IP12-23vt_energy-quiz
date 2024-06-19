package energiequiz.controller;

import static energiequiz.model.Constants.SCORE_SIZE;

import energiequiz.model.LeaderboardEntry;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller class for managing the quiz UI.
 */
public class LeaderboardController {

  @FXML
  TableView tableView;

  @FXML
  TableColumn rankingColumn;

  @FXML
  TableColumn teamColumn;

  @FXML
  TableColumn scoreColumn;
  @FXML
  private ImageView imgLeaderboard;

  private final String relativePath = "src/main/resources/ui/";
  private final String fileLeaderboardDe = "imgLeaderboardDe.png";
  private final String fileLeaderboardFr = "imgLeaderboardFr.png";
  private final String fileLeaderboardEn = "imgLeaderboardEn.png";

  private final Image imgLeaderboardDe = new Image("file:" + relativePath + fileLeaderboardDe);
  private final Image imgLeaderboardEn = new Image("file:" + relativePath + fileLeaderboardEn);
  private final Image imgLeaderboardFr = new Image("file:" + relativePath + fileLeaderboardFr);
  private ResourceBundle resourceBundle;

  public LeaderboardController(ResourceBundle resourceBundle) {
    this.resourceBundle = resourceBundle;
  }

  /**
   * Sets the content of the ranking table.
   *
   * @param content The list of Leaderboard entries for the ranking
   */
  public void setTableContent(List<LeaderboardEntry> content, LeaderboardEntry currentTeam) {
    rankingColumn.setCellValueFactory(
        new PropertyValueFactory<LeaderboardEntry, String>("ranking")
    );

    teamColumn.setCellValueFactory(
        new PropertyValueFactory<LeaderboardEntry, String>("teamName")
    );

    scoreColumn.setCellValueFactory(
        new PropertyValueFactory<LeaderboardEntry, String>("percentage")
    );

    String currentTeamMarker = resourceBundle.getString("label.currentTeamMarker");

    content.forEach(c -> {
      if (c.getTeamName().equals(currentTeam.getTeamName())) {
        c.setTeamName(c.getTeamName() + currentTeamMarker);
      }
      tableView.getItems().add(c);
    });

    if (content.stream()
        .noneMatch(c -> c.getTeamName().equals(currentTeam.getTeamName() + currentTeamMarker))) {
      if (!currentTeam.getTeamName().contains(currentTeamMarker)) {
        currentTeam.setTeamName(currentTeam.getTeamName() + currentTeamMarker);
      }
      tableView.getItems().add(currentTeam);

    }

    switch (resourceBundle.getLocale().getLanguage()) {
      case "de" -> imgLeaderboard.setImage(imgLeaderboardDe);
      case "en" -> imgLeaderboard.setImage(imgLeaderboardEn);
      case "fr" -> imgLeaderboard.setImage(imgLeaderboardFr);
      default -> throw new IllegalStateException("Unexpected value: " + resourceBundle.getLocale());

    }

    tableView.setFixedCellSize(50);
    tableView.setPrefHeight(tableView.getFixedCellSize() * (SCORE_SIZE + 1) + 70);
  }


}
