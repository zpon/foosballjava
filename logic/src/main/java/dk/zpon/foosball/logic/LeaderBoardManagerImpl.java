package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.LeaderBoardRepository;
import dk.zpon.foosball.model.LeaderBoardView;
import dk.zpon.foosball.model.LeaderBoardViewEntry;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

@Service
public class LeaderBoardManagerImpl implements LeaderBoardManager {
    @Inject
    private LeaderBoardRepository leaderBoardRepository;

    @Override
    public List<LeaderBoardViewEntry> getLeaderBoardViewEntries() {
        final LeaderBoardView leaderBoardView = leaderBoardRepository.getLeaderboardView();
        leaderBoardView.getEntries().sort(new Comparator<LeaderBoardViewEntry>() {
            @Override
            public int compare(LeaderBoardViewEntry o1, LeaderBoardViewEntry o2) {
                return -Integer.compare(o1.getEloRating(), o2.getEloRating());
            }
        });
        return leaderBoardView.getEntries();
    }
}
