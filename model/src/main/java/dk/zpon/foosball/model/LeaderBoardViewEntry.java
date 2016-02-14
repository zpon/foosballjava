package dk.zpon.foosball.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.UUID;

/**
 * Created by sjuul on 10/28/15.
 */
public class LeaderBoardViewEntry {
    @XmlElement(name = "Id")
    private UUID id;
    @XmlElement(name = "NumberOfGames")
    private int numberOfGames;
    @XmlElement(name = "UserName")
    private String username;
    @XmlElement(name = "Wins")
    private int wins;
    @XmlElement(name = "Loasses")
    private int losses;
    @XmlElement(name = "EloRating")
    private int eloRating;
    @XmlElement(name = "Form")
    private String form;

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getEloRating() {
        return eloRating;
    }

    public void setEloRating(int eloRating) {
        this.eloRating = eloRating;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
