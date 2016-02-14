package dk.zpon.foosball.server;

import dk.zpon.foosball.dataprovider.LeaderBoardRepositoryMongoDb;
import dk.zpon.foosball.dataprovider.MatchRepositoryMongoDb;
import dk.zpon.foosball.dataprovider.MongoDatabaseSingleton;
import dk.zpon.foosball.dataprovider.UsersRepositoryMongoDb;
import dk.zpon.foosball.logic.*;
import dk.zpon.foosball.model.LeaderBoardRepository;
import dk.zpon.foosball.model.MatchRepository;
import dk.zpon.foosball.model.UserRepository;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import java.io.IOException;

public class DependencyProvider extends AbstractBinder {
    @Override
    protected void configure() {
        // Models
        try {
            bind(LeaderBoardRepositoryMongoDb.class).to(LeaderBoardRepository.class).in(Singleton.class);
            bind(UsersRepositoryMongoDb.class).to(UserRepository.class).to(Singleton.class);
            bind(MatchRepositoryMongoDb.class).to(MatchRepository.class).in(Singleton.class);
            MongoDatabaseSingleton.init();
        } catch (IOException e) {
            throw new RuntimeException("Error during dependency injection");
        }

        // Logic
        bind(AchievementsManagerImpl.class).to(AchievementsManager.class);
        bind(LeaderBoardManagerImpl.class).to(LeaderBoardManager.class);
        bind(MatchManagerImpl.class).to(MatchManager.class);
        bind(UserManagerImpl.class).to(UserManager.class);
    }
}
