package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.Match;
import dk.zpon.foosball.model.User;

import java.util.List;

public interface UserManager {
    /**
     * @return Get all users.
     */
    List<User> getUsers();

    /**
     * Create new user.
     *
     * @param user User to be created.
     */
    void createUser(User user);

    /**
     * Get matches played by user.
     *
     * @param email Email of user to find matches for.
     * @return Matches played by user.
     */
    List<Match> getPlayerMatches(String email);
}
