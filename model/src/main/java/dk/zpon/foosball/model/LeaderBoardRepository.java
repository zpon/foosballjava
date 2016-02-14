package dk.zpon.foosball.model;

import org.jvnet.hk2.annotations.Contract;

/**
 * Created by sjuul on 10/31/15.
 */
@Contract
public interface LeaderBoardRepository {
    /**
     * Get leader board view.
     * @return
     */
    public LeaderBoardView getLeaderboardView();

    /**
     * Save leader board view.
     */
    public void saveLeaderBoardView(LeaderBoardView view);
}
