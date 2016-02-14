package dk.zpon.foosball.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by sjuul on 10/31/15.
 */
public class MatchResult {
    @XmlElement(name = "Team1Score")
    private int team1Score;
    @XmlElement(name = "Team2Score")
    private int team2Score;

    public MatchResult() {
    }

    public MatchResult(int team1Score, int team2Score) {
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public boolean didTeam1Win() {
        return team1Score > team2Score;
    }
}
