package dk.zpon.foosball.dataprovider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sjuul on 11/2/15.
 */
public class MongoDatabaseSingleton {
    private static MongoClientURI mongoClientURI;
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    public static void init() throws IOException {
        final Properties properties = new Properties();
        final InputStream resourceAsStream = MongoDatabaseSingleton.class.getClassLoader().getResourceAsStream
                ("mongodb.properties");
        if (resourceAsStream == null) {
            throw new IllegalStateException("Unable to load mongodb.properties");
        }
        properties.load(resourceAsStream);
        mongoClientURI = new MongoClientURI(properties.getProperty("mongourl"));
        mongoClient = new MongoClient(mongoClientURI);
        mongoDatabase = mongoClient.getDatabase(mongoClientURI.getDatabase());
    }

    public static MongoDatabase getDatabase() {
        return mongoDatabase;
    }
}