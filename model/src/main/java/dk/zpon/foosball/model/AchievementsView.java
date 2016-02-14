package dk.zpon.foosball.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by sjuul on 11/15/15.
 */
public class AchievementsView {
    @XmlElement(name = "Achievements")
    private List<Achievement> achievements;

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
}
