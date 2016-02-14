package dk.zpon.foosball.server.services;

import dk.zpon.foosball.logic.UserManager;
import dk.zpon.foosball.logic.UserManagerImpl;
import dk.zpon.foosball.model.Match;
import dk.zpon.foosball.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/player")
public class UsersService {
    @Inject
    UserManager userManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("GetUsers")
    public List<User> getUsers() {
        return userManager.getUsers();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("CreateUser")
    public void createUser(User user) {
        userManager.createUser(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("GetPlayerMatches")
    public List<Match> getPlayerMatches(@QueryParam("email") String email) {
        return userManager.getPlayerMatches(email);
    }
}
