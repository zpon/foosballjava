package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.*;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MatchManagerImpl implements MatchManager {
    @Inject
    private MatchRepository matchRepository;
    @Inject
    private LeaderBoardRepository leaderBoardRepository;

    @Override
    public void saveMatches(List<Match> matches) {
        for (Match match : matches) {
            if (match.getTimeStampUtc() == null) {
                match.setTimeStampUtc(new Date());
            }

            final LeaderBoardView latestLeaderboardView = getLatestLeaderBoardView();
            addMatchToLeaderboard(latestLeaderboardView, match);

            matchRepository.saveMatch(match);

            leaderBoardRepository.saveLeaderBoardView(latestLeaderboardView);
        }
    }

    @Override
    public List<Match> getLastGames(int numberOfMatches) {
        return matchRepository.getRecentMatches(numberOfMatches);
    }

    //
    // Helper methods
    //

    private LeaderBoardView getLatestLeaderBoardView() {
        LeaderBoardView result = leaderBoardRepository.getLeaderboardView();

        if (result == null) {
            return recalculateLeaderBoard();
        }

        sortEntries(result);

        return result;
    }

    private LeaderBoardView recalculateLeaderBoard() {
        final List<Match> matches = matchRepository.getMatches();
        matches.sort(new Comparator<Match>() {
            @Override
            public int compare(Match o1, Match o2) {
                if (o2.getTimeStampUtc() == null && o1.getTimeStampUtc() == null) {
                    return 0;
                } else if (o1.getTimeStampUtc() == null) {
                    return -1;
                } else if (o2.getTimeStampUtc() == null) {
                    return 1;
                }

                return o1.getTimeStampUtc().compareTo(o2.getTimeStampUtc());
            }
        });

        final LeaderBoardView leaderBoardView = new LeaderBoardView();
        for (Match match : matches) {
            addMatchToLeaderboard(leaderBoardView, match);
            matchRepository.saveMatch(match);
        }

        sortEntries(leaderBoardView);
        leaderBoardRepository.saveLeaderBoardView(leaderBoardView);

        return leaderBoardView;
    }

    private void sortEntries(LeaderBoardView leaderBoardView) {
        leaderBoardView.getEntries().sort(new Comparator<LeaderBoardViewEntry>() {
            @Override
            public int compare(LeaderBoardViewEntry o1, LeaderBoardViewEntry o2) {
                return -Integer.compare(o1.getEloRating(), o2.getEloRating());
            }
        });
    }

    /**
     * Add match to leader board.
     *
     * @param leaderboardView Lead to add match to.
     * @param match           Match to be added.
     */
    void addMatchToLeaderboard(LeaderBoardView leaderboardView, Match match) {
        List<LeaderBoardViewEntry> leaderboardEntries = leaderboardView.getEntries();

        //Team1
        String player1 = match.getPlayers().get(0);
        final LeaderBoardViewEntry existingPlayer1 = leaderboardEntries.stream().filter(leaderBoardViewEntry ->
                leaderBoardViewEntry.getUsername().equals(player1)).findFirst().orElse(null);

        String player2 = match.getPlayers().get(1);
        final LeaderBoardViewEntry existingPlayer2 = leaderboardEntries.stream().filter(leaderBoardViewEntry ->
                leaderBoardViewEntry.getUsername().equals(player2)).findFirst().orElse(null);

        int team1AvgElo = existingPlayer1 != null ? existingPlayer1.getEloRating() : 1500;
        team1AvgElo += existingPlayer2 != null ? existingPlayer2.getEloRating() : 1500;

        //Team2
        String player3 = match.getPlayers().get(2);
        final LeaderBoardViewEntry existingPlayer3 = leaderboardEntries.stream().filter(leaderBoardViewEntry ->
                leaderBoardViewEntry.getUsername().equals(player3)).findFirst().orElse(null);
        final String player4 = match.getPlayers().get(3);
        final LeaderBoardViewEntry existingPlayer4 = leaderboardEntries.stream().filter(leaderBoardViewEntry ->
                leaderBoardViewEntry.getUsername().equals(player4)).findFirst().orElse(null);

        int team2AvgElo = existingPlayer3 != null ? existingPlayer3.getEloRating() : 1500;
        team2AvgElo += existingPlayer4 != null ? existingPlayer4.getEloRating() : 1500;

        double result = calculateRating(team1AvgElo / 2, team2AvgElo / 2, match.getMatchResult().didTeam1Win());

        match.setPoints((int) result);

        if (existingPlayer1 == null) {
            leaderboardEntries.add(createPlayer(player1, result, match.getMatchResult().didTeam1Win()));
        } else {
            updateExistingLeaderboardEntry(existingPlayer1.getUsername(), leaderboardEntries, result,
                    match.getMatchResult().didTeam1Win());
        }

        if (existingPlayer2 == null) {
            leaderboardEntries.add(createPlayer(player2, result, match.getMatchResult().didTeam1Win()));
        } else {
            updateExistingLeaderboardEntry(existingPlayer2.getUsername(), leaderboardEntries, result,
                    match.getMatchResult().didTeam1Win());
        }

        if (existingPlayer3 == null) {
            leaderboardEntries.add(createPlayer(player3, result, !match.getMatchResult().didTeam1Win()));
        } else {
            updateExistingLeaderboardEntry(existingPlayer3.getUsername(), leaderboardEntries, result,
                    !match.getMatchResult().didTeam1Win());
        }

        if (existingPlayer4 == null) {
            leaderboardEntries.add(createPlayer(player4, result, !match.getMatchResult().didTeam1Win()));
        } else {
            updateExistingLeaderboardEntry(existingPlayer4.getUsername(), leaderboardEntries, result,
                    !match.getMatchResult().didTeam1Win());
        }
    }

    private LeaderBoardViewEntry createPlayer(String playerName, double result, boolean won) {
        final LeaderBoardViewEntry leaderBoardViewEntry = new LeaderBoardViewEntry();

        leaderBoardViewEntry.setUsername(playerName);
        leaderBoardViewEntry.setEloRating(won ? 1500 + (int) result : 1500 - (int) result);
        leaderBoardViewEntry.setNumberOfGames(1);
        leaderBoardViewEntry.setWins(won ? 1 : 0);
        leaderBoardViewEntry.setLosses(won ? 0 : 1);
        leaderBoardViewEntry.setForm(won ? "W" : "L");

        return leaderBoardViewEntry;
    }

    double calculateRating(int player1, int player2, boolean playerOneWin) {
        // If the winner has a much higher rating than the looser, the winner will not get any points, e.g. if
        // player1 wins and has 1900 points, and player2 only has 1500 points, then player1 gets zero points. See
        // MatchesServiceTest#testCalculateRating for example.

        final double medium = 20;
        final double diversification = 20;
        final double minRating = medium - diversification;
        final double maxRating = medium + diversification;

        double diff;

        if (playerOneWin) {
            diff = player1 - player2;
        } else {
            diff = player2 - player1;
        }

        double result = (medium * diversification - diff) / medium + minRating;

        if (result > maxRating) {
            result = maxRating;
        } else if (result < minRating) {
            result = minRating;
        }

        return result;
    }

    void updateExistingLeaderboardEntry(String playerName, List<LeaderBoardViewEntry> leaderboardEntries,
                                               double result, boolean won) {
        LeaderBoardViewEntry playerEntry = leaderboardEntries.stream().filter(leaderBoardViewEntry ->
                leaderBoardViewEntry.getUsername().equals(playerName)).findFirst().get();

        playerEntry.setEloRating(playerEntry.getEloRating() + (won ? (int) result : -(int) result));
        playerEntry.setNumberOfGames(playerEntry.getNumberOfGames() + 1);

        if (playerEntry.getForm().length() >= 5) {
            playerEntry.setForm(playerEntry.getForm().substring(1));
        }
        playerEntry.setForm(playerEntry.getForm() + (won ? "W" : "L"));

        if (won) {
            playerEntry.setWins(playerEntry.getWins() + 1);
        } else {
            playerEntry.setLosses(playerEntry.getLosses() + 1);
        }
    }
}
