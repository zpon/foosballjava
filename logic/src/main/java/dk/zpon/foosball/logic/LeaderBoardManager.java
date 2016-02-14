package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.LeaderBoardViewEntry;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;

@Contract
public interface LeaderBoardManager {
    /**
     * @return Get leader board view.
     */
    List<LeaderBoardViewEntry> getLeaderBoardViewEntries();
}
