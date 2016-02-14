package dk.zpon.foosball.server.services;

import dk.zpon.foosball.dataprovider.UsersRepositoryMongoDb;
import dk.zpon.foosball.logic.AchievementsManager;
import dk.zpon.foosball.logic.AchievementsManagerImpl;
import dk.zpon.foosball.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.jvnet.hk2.testing.junit.HK2Runner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AchievementsServiceTest extends HK2Runner {

    @Inject
    private LeaderBoardRepository leaderBoardRepository;
    @Inject
    private MatchRepository matchRepository;
    @Inject
    private AchievementsManager achievementsManager;

    @Test
    public void testGetAchievementsView() throws Exception {
        assertNotNull(leaderBoardRepository);

        final LeaderBoardView leaderBoardView = new LeaderBoardView();
        final ArrayList<LeaderBoardViewEntry> leaderBoardViewEntries = new ArrayList<>();

        final LeaderBoardViewEntry leaderBoardViewEntry = new LeaderBoardViewEntry();
        leaderBoardViewEntry.setNumberOfGames(7);
        leaderBoardViewEntry.setWins(6);
        leaderBoardViewEntry.setLosses(1);
        leaderBoardViewEntry.setUsername("A A");
        leaderBoardViewEntries.add(leaderBoardViewEntry);

        final LeaderBoardViewEntry leaderBoardViewEntry2 = new LeaderBoardViewEntry();
        leaderBoardViewEntry2.setNumberOfGames(6);
        leaderBoardViewEntry2.setWins(1);
        leaderBoardViewEntry2.setLosses(5);
        leaderBoardViewEntry2.setUsername("B B");
        leaderBoardViewEntries.add(leaderBoardViewEntry2);

        leaderBoardView.setEntries(leaderBoardViewEntries);

        ((LeaderBoardRepositoryMock) leaderBoardRepository).setLeaderboardViewToReturn(leaderBoardView);

        List<Match> matches = new ArrayList<>();
        {
            final Match match = new Match();
            final MatchResult matchResult = new MatchResult();
            matchResult.setTeam1Score(10);
            matchResult.setTeam2Score(1);
            match.setMatchResult(matchResult);
            match.setPlayers(Arrays.asList("A A", "C C", "E E", "D D"));
            match.setTimeStampUtc(new Date());
            matches.add(match);
        }

        {
            final Match match = new Match();
            final MatchResult matchResult = new MatchResult();
            matchResult.setTeam1Score(1);
            matchResult.setTeam2Score(10);
            match.setMatchResult(matchResult);
            match.setPlayers(Arrays.asList("A A", "C C", "B B", "D D"));
            match.setTimeStampUtc(new Date());
            matches.add(match);
        }

        for (int i = 0; i <= 5; i++) {
            final Match match = new Match();
            final MatchResult matchResult = new MatchResult();
            matchResult.setTeam1Score(10);
            matchResult.setTeam2Score(1);
            match.setMatchResult(matchResult);
            match.setPlayers(Arrays.asList("A A", "C C", "B B", "D D"));
            match.setTimeStampUtc(new Date());
            matches.add(match);
        }



        ((MatchRepositoryMock) matchRepository).setMatches(matches);

        final AchievementsView achievementsView = achievementsManager.getAchievementsView();

        Achievement mostWins = null;
        Achievement mostGames = null;
        Achievement longestWinStreak = null;
        Achievement longestLossStreak = null;
        Achievement bestWinRatio = null;
        for (Achievement achievement : achievementsView.getAchievements()) {
            if (achievement.getHeadline().equals("Most wins")) {
                mostWins = achievement;
            } else if (achievement.getHeadline().equals("Most games")) {
                mostGames = achievement;
            } else if (achievement.getHeadline().equals("Longest win streak")) {
                longestWinStreak = achievement;
            } else if (achievement.getHeadline().equals("Longest loss streak")) {
                longestLossStreak = achievement;
            } else if (achievement.getHeadline().equals("Best win ratio")) {
                bestWinRatio = achievement;
            }
        }
        assertNotNull(mostWins);
        assertEquals("A A", mostWins.getUserName());
        assertNotNull(mostGames);
        assertEquals("A A", mostGames.getUserName());
        assertNotNull(bestWinRatio);
        assertEquals("A A", bestWinRatio.getUserName());
        assertNotNull(longestWinStreak);
        assertEquals("A A", longestWinStreak.getUserName());
        assertNotNull(longestLossStreak);
        assertEquals("B B", longestLossStreak.getUserName());
    }
}