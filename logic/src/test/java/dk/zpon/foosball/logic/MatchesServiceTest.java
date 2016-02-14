package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.LeaderBoardView;
import dk.zpon.foosball.model.LeaderBoardViewEntry;
import dk.zpon.foosball.model.Match;
import dk.zpon.foosball.model.MatchResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by sjuul on 11/1/15.
 */
public class MatchesServiceTest {

    @Test
    public void testCalculateRating() throws Exception {
        final MatchManagerImpl matchManager = new MatchManagerImpl();

        // If player1 wins and has a much higher rating than player2, he gets zero points (minumum).
        assertEquals(0, matchManager.calculateRating(1900, 1500, true), 0.1);
        // If player1 wins and has a much lower rating than player2, he gets 40 points (maximum).
        assertEquals(40, matchManager.calculateRating(1500, 1900, true), 0.1);
    }

    @Test
    public void testUpdateExistingLeaderboardEntry() throws Exception {
        final String username = "foo@bar.com";

        final LeaderBoardViewEntry leaderBoardViewEntry = new LeaderBoardViewEntry();
        leaderBoardViewEntry.setUsername(username);
        leaderBoardViewEntry.setNumberOfGames(5);
        leaderBoardViewEntry.setWins(1);
        leaderBoardViewEntry.setLosses(4);
        leaderBoardViewEntry.setEloRating(1520);
        leaderBoardViewEntry.setForm("WLLLL");
        final ArrayList<LeaderBoardViewEntry> leaderBoardViewEntries = new ArrayList<>(1);
        leaderBoardViewEntries.add(leaderBoardViewEntry);

        final Match match = new Match();
        match.setPoints(15);
        match.setTimeStampUtc(new Date(2015 - 1900, 9, 9));
        match.setMatchResult(new MatchResult(1, 10));

        final MatchManagerImpl matchManager = new MatchManagerImpl();
        matchManager.updateExistingLeaderboardEntry(username, leaderBoardViewEntries, 15, true);

        Assert.assertEquals(1520 + 15, leaderBoardViewEntry.getEloRating());
        Assert.assertEquals(6, leaderBoardViewEntry.getNumberOfGames());
        Assert.assertEquals(2, leaderBoardViewEntry.getWins());
        Assert.assertEquals("LLLLW", leaderBoardViewEntry.getForm());
    }

    @Test
    public void testAddMatchToLeaderboard() throws Exception {
        final Match match = new Match();
        match.setTimeStampUtc(new Date(2015 - 1900, 9, 9));
        match.setMatchResult(new MatchResult(1, 10));
        final ArrayList<String> players = new ArrayList<>(4);
        players.add("anders@and.com");
        players.add("rip@and.com");
        players.add("rap@and.com");
        players.add("rup@and.com");
        match.setPlayers(players);

        final LeaderBoardView leaderBoardView = new LeaderBoardView();
        leaderBoardView.setEntries(new ArrayList<>());

        final MatchManagerImpl matchManager = new MatchManagerImpl();
        matchManager.addMatchToLeaderboard(leaderBoardView, match);

        Assert.assertEquals(4, leaderBoardView.getEntries().size());
        final LeaderBoardViewEntry andersAnd = leaderBoardView.getEntries().stream().filter(leaderBoardViewEntry -> {
            return leaderBoardViewEntry.getUsername().equals("anders@and.com");
        }).findFirst().get();
        Assert.assertEquals(1500 - 20, andersAnd.getEloRating());
        Assert.assertEquals(1, andersAnd.getLosses());

        final LeaderBoardViewEntry rapAnd = leaderBoardView.getEntries().stream().filter(leaderBoardViewEntry -> {
            return leaderBoardViewEntry.getUsername().equals("rap@and.com");
        }).findFirst().get();
        Assert.assertEquals(1500 + 20, rapAnd.getEloRating());
        Assert.assertEquals(1, rapAnd.getWins());

        Assert.assertEquals(20, match.getPoints());
    }
}