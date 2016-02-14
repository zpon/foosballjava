package dk.zpon.foosball.server.services;

import dk.zpon.foosball.model.Match;
import dk.zpon.foosball.model.MatchRepository;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public class MatchRepositoryMock implements MatchRepository {

    private List<Match> matches;

    public MatchRepositoryMock() {

    }

    @Override
    public void saveMatch(Match match) {

    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public List<Match> getMatches() {
        return matches;
    }

    @Override
    public List<Match> getPlayerMatches(String email) {
        return null;
    }

    @Override
    public List<Match> getRecentMatches(int numberOfMatches) {
        return null;
    }
}
