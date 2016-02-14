package dk.zpon.foosball.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by sjuul on 10/31/15.
 */
public class Match {
    @XmlElement(name = "_id")
    private UUID id;
    @XmlElement(name = "PlayerList")
    private List<String> players;
    @XmlElement(name = "TimeStampUtc")
    private Date timeStampUtc;
    @XmlElement(name = "StaticFormationTeam1")
    private boolean staticFormationTeam1;
    @XmlElement(name = "StaticFormationTeam2")
    private boolean staticFormationTeam2;
    @XmlElement(name = "IsThisRandom")
    private boolean isThisRandom;
    @XmlElement(name = "Points")
    private int points;
    @XmlElement(name = "MatchResult")
    private MatchResult matchResult;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public boolean isStaticFormationTeam1() {
        return staticFormationTeam1;
    }

    public void setStaticFormationTeam1(boolean staticFormationTeam1) {
        this.staticFormationTeam1 = staticFormationTeam1;
    }

    public boolean isStaticFormationTeam2() {
        return staticFormationTeam2;
    }

    public void setStaticFormationTeam2(boolean staticFormationTeam2) {
        this.staticFormationTeam2 = staticFormationTeam2;
    }

    public boolean isThisRandom() {
        return isThisRandom;
    }

    public void setIsThisRandom(boolean isThisRandom) {
        this.isThisRandom = isThisRandom;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public MatchResult getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(MatchResult matchResult) {
        this.matchResult = matchResult;
    }

    public Date getTimeStampUtc() {
        return timeStampUtc;
    }

    public void setTimeStampUtc(Date timeStampUtc) {
        this.timeStampUtc = timeStampUtc;
    }
}
