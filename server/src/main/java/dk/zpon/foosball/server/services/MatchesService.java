package dk.zpon.foosball.server.services;

import dk.zpon.foosball.logic.MatchManager;
import dk.zpon.foosball.logic.MatchManagerImpl;
import dk.zpon.foosball.model.Match;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/match/")
public class MatchesService {
    @Inject
    private MatchManager matchManager;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("SaveMatch")
    public void saveMatch(List<Match> matches) {
        matchManager.saveMatches(matches);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("lastgames")
    public List<Match> lastGames(@QueryParam("numberofmatches") int numberOfMatches) {
        return matchManager.getLastGames(numberOfMatches);
    }
}