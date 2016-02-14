package dk.zpon.foosball.server.services;

import dk.zpon.foosball.logic.LeaderBoardManager;
import dk.zpon.foosball.logic.LeaderBoardManagerImpl;
import dk.zpon.foosball.model.LeaderBoardViewEntry;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/leaderboard")
public class LeaderBoardService {
    @Inject
    private LeaderBoardManager leaderBoardManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("index")
    public List<LeaderBoardViewEntry> index() {
        return leaderBoardManager.getLeaderBoardViewEntries();
    }
}
