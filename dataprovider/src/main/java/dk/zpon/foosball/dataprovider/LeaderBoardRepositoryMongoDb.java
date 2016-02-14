package dk.zpon.foosball.dataprovider;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import dk.zpon.foosball.model.LeaderBoardRepository;
import dk.zpon.foosball.model.LeaderBoardView;
import dk.zpon.foosball.model.LeaderBoardViewEntry;
import org.bson.Document;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by sjuul on 10/28/15.
 */
@Service
public class LeaderBoardRepositoryMongoDb implements LeaderBoardRepository {
    public LeaderBoardView getLeaderboardView() {
        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();
        final MongoCollection<Document> leaderboardViews = database.getCollection("LeaderboardViews");

        final LeaderBoardView leaderboardView = new LeaderBoardView();
        final ArrayList<LeaderBoardViewEntry> leaderboardViewEntries = new ArrayList<>();

        final FindIterable<Document> documents = leaderboardViews.find();
        for (Document d : documents) {
            List<Document> list = (List<Document>) d.get("Entries");
            for (Document o : list) {
                final LeaderBoardViewEntry leaderboardViewEntry = new LeaderBoardViewEntry();
                leaderboardViewEntry.setNumberOfGames(o.getInteger("NumberOfGames"));
                leaderboardViewEntry.setUsername(o.getString("UserName"));
                leaderboardViewEntry.setEloRating(o.getInteger("EloRating"));
                leaderboardViewEntry.setWins(o.getInteger("Wins"));
                leaderboardViewEntry.setLosses(o.getInteger("Losses"));
                leaderboardViewEntry.setForm(o.getString("Form"));

                leaderboardViewEntries.add(leaderboardViewEntry);
            }
            leaderboardView.setTimestamp((Date) d.get("Timestamp"));
            leaderboardView.setId((UUID) d.get("_id"));
        }

        leaderboardView.setEntries(leaderboardViewEntries);

        return leaderboardView;
    }

    @Override
    public void saveLeaderBoardView(LeaderBoardView view) {
        boolean newView = false;
        if (view.getId() == null) {
            view.setId(UUID.randomUUID());
            newView = true;
        }
        if (view.getTimestamp() == null) {
            view.setTimestamp(new Date());
        }

        final Document leaderBoardDocument = new Document();

        leaderBoardDocument.append("Timestamp", view.getTimestamp());
        leaderBoardDocument.append("_id", view.getId());

        List<Document> entries = new ArrayList<>();
        for (LeaderBoardViewEntry entry : view.getEntries()) {
            final Document entryDocument = new Document();
            entryDocument.append("NumberOfGames", entry.getNumberOfGames());
            entryDocument.append("UserName", entry.getUsername());
            entryDocument.append("EloRating", entry.getEloRating());
            entryDocument.append("Wins", entry.getWins());
            entryDocument.append("Losses", entry.getLosses());
            entryDocument.append("Form", entry.getForm());

            entries.add(entryDocument);
        }

        leaderBoardDocument.append("Entries", entries);

        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();
        final MongoCollection<Document> leaderboardViewsCollection = database.getCollection("LeaderboardViews");

        if (newView) {
            leaderboardViewsCollection.insertOne(leaderBoardDocument);
        } else {
            leaderboardViewsCollection.replaceOne(Filters.eq("_id", view.getId()), leaderBoardDocument);
        }
    }
}
