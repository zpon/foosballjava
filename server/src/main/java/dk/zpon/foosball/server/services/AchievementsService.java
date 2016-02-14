package dk.zpon.foosball.server.services;

import dk.zpon.foosball.logic.AchievementsManager;
import dk.zpon.foosball.logic.AchievementsManagerImpl;
import dk.zpon.foosball.model.*;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/achievements")
@Service
public class AchievementsService {

    @Inject
    private AchievementsManager achievementsManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("index")
    public AchievementsView getAchievementsView() {
        return achievementsManager.getAchievementsView();
    }
}
