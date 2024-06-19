package energiequiz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


class LeaderboardEntryTest {

  private LeaderboardEntry entry;

  @BeforeEach
  void setUp() {
    entry = new LeaderboardEntry("1", "Team A", "75%");
  }

  @Test
  void testGetRanking() {
    assertEquals("1", entry.getRanking());
  }

  @Test
  void testSetRanking() {
    entry.setRanking("2");
    assertEquals("2", entry.getRanking());
  }

  @Test
  void testGetTeamName() {
    assertEquals("Team A", entry.getTeamName());
  }

  @Test
  void testSetTeamName() {
    entry.setTeamName("Team B");
    assertEquals("Team B", entry.getTeamName());
  }

  @Test
  void testGetPercentage() {
    assertEquals("75%", entry.getPercentage());
  }

  @Test
  void testSetPercentage() {
    entry.setPercentage("80%");
    assertEquals("80%", entry.getPercentage());
  }
}
