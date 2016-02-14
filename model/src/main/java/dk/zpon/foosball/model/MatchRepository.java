package dk.zpon.foosball.model;

import org.jvnet.hk2.annotations.Contract;

import java.util.List;

/**
 * Created by sjuul on 10/31/15.
 */
@Contract
public interface MatchRepository {

    /**
     * Save new match.
     *
     * @param match
     */
    public void saveMatch(Match match);

    /**
     * Get all matches.
     *
     * @return
     */
    public List<Match> getMatches();

    /**
     * Get matches of player.
     *
     * @param email
     * @return
     */
    public List<Match> getPlayerMatches(String email);

    /**
     * Get a given number of recent matches.
     *
     * @param numberOfMatches
     * @return
     */
    public List<Match> getRecentMatches(int numberOfMatches);
}
