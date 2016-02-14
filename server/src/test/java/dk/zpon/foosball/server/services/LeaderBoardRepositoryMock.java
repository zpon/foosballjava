package dk.zpon.foosball.server.services;

import dk.zpon.foosball.model.LeaderBoardRepository;
import dk.zpon.foosball.model.LeaderBoardView;
import org.jvnet.hk2.annotations.Service;

/**
 * Created by sjuul on 11/16/15.
 */
@Service
public class LeaderBoardRepositoryMock implements LeaderBoardRepository {

    private LeaderBoardView leaderBoardView;

    public void setLeaderboardViewToReturn(LeaderBoardView leaderBoardView) {
        this.leaderBoardView = leaderBoardView;
    }

    @Override
    public LeaderBoardView getLeaderboardView() {
        return leaderBoardView;
    }

    @Override
    public void saveLeaderBoardView(LeaderBoardView view) {

    }
}
