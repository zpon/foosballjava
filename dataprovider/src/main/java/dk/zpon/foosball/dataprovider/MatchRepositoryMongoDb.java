package dk.zpon.foosball.dataprovider;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import dk.zpon.foosball.model.Match;
import dk.zpon.foosball.model.MatchRepository;
import dk.zpon.foosball.model.MatchResult;
import org.bson.Document;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by sjuul on 10/31/15.
 */
@Service
public class MatchRepositoryMongoDb implements MatchRepository {

    public MatchRepositoryMongoDb() {
        System.out.println("Created");
    }

    @Override
    public void saveMatch(Match match) {
        if (match.getId() == null) {
            match.setId(UUID.randomUUID());
        }

        final Document document = new Document();
        document.append("_id", match.getId());
        document.append("PlayerList", match.getPlayers());
        document.append("StaticFormationTeam1", match.isStaticFormationTeam1());
        document.append("StaticFormationTeam2", match.isStaticFormationTeam2());
        document.append("IsThisRandom", match.isThisRandom());
        document.append("MatchResult", new Document().append("Team1Score", match.getMatchResult().getTeam1Score())
                .append("Team2Score", match.getMatchResult().getTeam2Score()));
        document.append("Points", match.getPoints());
        document.append("TimeStampUtc", match.getTimeStampUtc());

        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();
        final MongoCollection<Document> matchesV2 = database.getCollection("MatchesV2");
        matchesV2.insertOne(document);
    }

    @Override
    public List<Match> getMatches() {
        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();

        final MongoCollection<Document> matchCollection = database.getCollection("MatchesV2");
        final FindIterable<Document> documents = matchCollection.find();

        return createMatchList(documents, -1);
    }

    @Override
    public List<Match> getPlayerMatches(String email) {
        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();

        final MongoCollection<Document> matchCollection = database.getCollection("MatchesV2");
        final FindIterable<Document> playerMatches = matchCollection.find(Filters.eq("PlayerList", email));

        return createMatchList(playerMatches, -1);
    }

    @Override
    public List<Match> getRecentMatches(int numberOfMatches) {
        final MongoDatabase database = MongoDatabaseSingleton.getDatabase();

        final MongoCollection<Document> matchCollection = database.getCollection("MatchesV2");
        final FindIterable<Document> playerMatches = matchCollection.find().sort(Sorts
                .ascending("TimeStampUtc"));

        final List<Match> matchList = createMatchList(playerMatches, numberOfMatches);

        return matchList;
    }

    /**
     * Create list of matches.
     *
     * @param matchDocuments
     * @param numberOfMatches Number of matches in the list. -1 means unlimited.s
     * @return
     */
    private List<Match> createMatchList(FindIterable<Document> matchDocuments, int numberOfMatches) {
        List<Match> matches = new ArrayList<>();

        for (Document matchDocument : matchDocuments) {
            final Match match = new Match();

            match.setTimeStampUtc((Date) matchDocument.get("TimeStampUtc"));
            match.setId((UUID) matchDocument.get("_id"));
            final Object playerList = matchDocument.get("PlayerList");
            if (playerList instanceof List<?>) {
                match.setPlayers((List<String>) playerList);
            }
            match.setStaticFormationTeam1(matchDocument.getBoolean("StaticFormationTeam1"));
            match.setStaticFormationTeam2(matchDocument.getBoolean("StaticFormationTeam2"));
            match.setIsThisRandom(matchDocument.getBoolean("IsThisRandom"));

            final MatchResult matchResult = new MatchResult();
            matchResult.setTeam1Score(((Document) matchDocument.get("MatchResult")).getInteger("Team1Score"));
            matchResult.setTeam2Score(((Document) matchDocument.get("MatchResult")).getInteger("Team2Score"));

            match.setMatchResult(matchResult);
            match.setPoints(matchDocument.getInteger("Points"));

            matches.add(match);

            if (matches.size() == numberOfMatches) {
                break;
            }
        }

        return matches;
    }


}
