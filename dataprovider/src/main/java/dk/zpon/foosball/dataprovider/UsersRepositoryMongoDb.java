package dk.zpon.foosball.dataprovider;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dk.zpon.foosball.model.User;
import dk.zpon.foosball.model.UserRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sjuul on 10/31/15.
 */
public class UsersRepositoryMongoDb implements UserRepository {

    @Override
    public List<User> getUsers() {
        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();

        final MongoCollection<Document> usersCollection = database.getCollection("Users");
        final FindIterable<Document> documents = usersCollection.find();

        List<User> users = new ArrayList<>();

        for (Document d : documents) {
            final User user = new User();
            user.setUsername(d.getString("Username"));
            user.setEmail(d.getString("Email"));
            user.setGravatarEmail(d.getString("GravatarEmail"));
            user.setId((UUID) d.get("_id"));

            users.add(user);
        }

        return users;
    }

    @Override
    public void addUser(User user) {
        final Document userDocument = new Document();
        userDocument.append("_id", UUID.randomUUID());
        userDocument.append("Email", user.getEmail());
        userDocument.append("Username", user.getUsername());
        userDocument.append("GravatarEmail", user.getGravatarEmail());

        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();
        final MongoCollection<Document> users = database.getCollection("Users");
        users.insertOne(userDocument);
    }
}
