package dk.zpon.foosball.model;

import java.util.List;

/**
 * Created by sjuul on 10/31/15.
 */
public interface UserRepository {
    public List<User> getUsers();

    public void addUser(User user);
}
