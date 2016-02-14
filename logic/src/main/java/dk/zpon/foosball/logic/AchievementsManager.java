package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.AchievementsView;
import org.jvnet.hk2.annotations.Contract;

/**
 * Created by sjuul on 12/8/15.
 */
@Contract
public interface AchievementsManager {
    /**
     * @return Achievements view.
     */
    AchievementsView getAchievementsView();
}
