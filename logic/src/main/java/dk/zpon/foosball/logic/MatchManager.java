package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.Match;

import java.util.List;

public interface MatchManager {
    /**
     * Save new match.
     *
     * @param matches Match to be saved.
     */
    void saveMatches(List<Match> matches);

    /**
     * Get latest games.
     *
     * @param numberOfMatches Max number of games to return.
     * @return Latest games.
     */
    List<Match> getLastGames(int numberOfMatches);
}
