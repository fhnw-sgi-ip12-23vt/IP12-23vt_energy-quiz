package energiequiz.model;

/**
 * A model for a singular Leaderboard Entry, with ranking, team name and
 * percentage of energy remaining.
 */
public class LeaderboardEntry {

  private String ranking;
  private String teamName;
  private String percentage;

  /**
   * Constructor of LeaderboardEntry class.
   *
   * @param ranking The place in the ranking (1 - max score size)
   * @param teamName The name of the team
   * @param percentage The percentage of energy remaining
   */
  public LeaderboardEntry(String ranking, String teamName, String percentage) {
    this.ranking = ranking;
    this.teamName = teamName;
    this.percentage = percentage;
  }

  public String getRanking() {
    return ranking;
  }

  public void setRanking(String ranking) {
    this.ranking = ranking;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getPercentage() {
    return percentage;
  }

  public void setPercentage(String percentage) {
    this.percentage = percentage;
  }
}
