package energiequiz.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import energiequiz.model.LeaderboardEntry;
import energiequiz.util.FxglTestUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * a class to test the LeaderboardController.
 */
public class LeaderboardControllerTest {

  private LeaderboardController controller;
  private TableView<LeaderboardEntry> tableView;
  private TableColumn<LeaderboardEntry, String> rankingColumn;
  private TableColumn<LeaderboardEntry, String> teamColumn;
  private TableColumn<LeaderboardEntry, String> scoreColumn;
  private ResourceBundle resourceBundle;

  @BeforeAll
  public static void setUpClass() {
    FxglTestUtil.setUpFxgl();
  }

  @BeforeEach
  void setUp() {
    resourceBundle = ResourceBundle.getBundle("strings/bundle", Locale.ENGLISH);
    controller = new LeaderboardController(resourceBundle);

    tableView = new TableView<>();
    rankingColumn = new TableColumn<>("Ranking");
    teamColumn = new TableColumn<>("Team Name");
    scoreColumn = new TableColumn<>("Score");

    tableView.getColumns().addAll(rankingColumn, teamColumn, scoreColumn);

    rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
    teamColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
    scoreColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));

    controller.tableView = tableView;
    controller.rankingColumn = rankingColumn;
    controller.teamColumn = teamColumn;
    controller.scoreColumn = scoreColumn;

    Platform.runLater(() -> tableView.getItems().clear());
  }

  @Test
  void testSetTableContent() {
    Platform.runLater(() -> {
      List<LeaderboardEntry> content = new ArrayList<>();
      content.add(new LeaderboardEntry("1", "Team A", "75%"));
      content.add(new LeaderboardEntry("2", "Team B", "65%"));
      content.add(new LeaderboardEntry("3", "Team C", "55%"));

      LeaderboardEntry currentTeam = new LeaderboardEntry("4", "Team D", "50%");

      controller.setTableContent(content, currentTeam);

      ObservableList<LeaderboardEntry> items = tableView.getItems();

      assertEquals(4, items.size());
      assertEquals("1", items.get(0).getRanking());
      assertEquals("Team A", items.get(0).getTeamName());
      assertEquals("75%", items.get(0).getPercentage());

      assertEquals("2", items.get(1).getRanking());
      assertEquals("Team B", items.get(1).getTeamName());
      assertEquals("65%", items.get(1).getPercentage());

      assertEquals("3", items.get(2).getRanking());
      assertEquals("Team C", items.get(2).getTeamName());
      assertEquals("55%", items.get(2).getPercentage());

      assertEquals("4", items.get(3).getRanking());
      assertEquals("Team D" + resourceBundle.getString("label.currentTeamMarker"),
          items.get(3).getTeamName());
      assertEquals("50%", items.get(3).getPercentage());
    });
  }
}
