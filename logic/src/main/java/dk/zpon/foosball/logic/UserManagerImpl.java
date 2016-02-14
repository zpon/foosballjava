package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.Match;
import dk.zpon.foosball.model.MatchRepository;
import dk.zpon.foosball.model.User;
import dk.zpon.foosball.model.UserRepository;

import javax.inject.Inject;
import java.util.List;

public class UserManagerImpl implements UserManager {
    @Inject
    private UserRepository userRepository;
    @Inject
    private MatchRepository matchRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public void createUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    public List<Match> getPlayerMatches(String email) {
        return matchRepository.getPlayerMatches(email);
    }
}
