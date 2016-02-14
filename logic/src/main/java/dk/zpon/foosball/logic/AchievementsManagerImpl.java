package dk.zpon.foosball.logic;

import dk.zpon.foosball.model.*;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class AchievementsManagerImpl implements AchievementsManager {

    @Inject
    private LeaderBoardRepository leaderBoardRepository;
    @Inject
    private MatchRepository matchRepository;

    @Override
    public AchievementsView getAchievementsView() {
        assert leaderBoardRepository != null : "LeaderBoardRepository is null";
        LeaderBoardView leaderboardView = leaderBoardRepository.getLeaderboardView();
        List<Match> matches = matchRepository.getMatches();
        matches.sort((o1, o2) -> o1.getTimeStampUtc().compareTo(o2.getTimeStampUtc()));

        final GetStreakResult winnerStreak = getStreak(matches, leaderboardView.getEntries(), true);
        final GetStreakResult looserStreak = getStreak(matches, leaderboardView.getEntries(), false);

        List<LeaderBoardViewEntry> entries = leaderboardView.getEntries();
        // Most games
        entries.sort((o1, o2) -> Integer.compare(o2.getNumberOfGames(), o1.getNumberOfGames()));
        LeaderBoardViewEntry mostGames = entries.get(0);
        // Most wins
        entries.sort((o1, o2) -> Integer.compare(o2.getWins(), o1.getWins()));
        LeaderBoardViewEntry mostWins = entries.get(0);
        // Best ratio
        entries.sort((o1, o2) -> Double.compare(((double) o2.getWins() / (double) o2.getNumberOfGames()), ((double)
                o1.getWins() / (double) o1.getNumberOfGames())));
        LeaderBoardViewEntry bestRatio = entries.get(0);


        List<String> flawlessVictoryWinners = new ArrayList<>();
        List<String> flawlessVictoryLoosers = new ArrayList<>();
        getFlawlessVictory(matches, flawlessVictoryWinners, flawlessVictoryLoosers);

        List<Achievement> achievements = new ArrayList<Achievement>();
        achievements.add(new Achievement("Most games", mostGames.getUsername(), Integer.toString(mostGames
                .getNumberOfGames()), "Games"));
        achievements.add(new Achievement("Most wins", mostWins.getUsername(), Integer.toString(mostWins
                .getNumberOfGames()), "Games"));
        achievements.add(new Achievement("Best win ratio", bestRatio.getUsername(), Integer.toString(((int) ((double)
                ((double) bestRatio.getWins() / (double) bestRatio.getNumberOfGames()) * 100))), "Ratio"));
        achievements.add(new Achievement("Longest win streak", winnerStreak.getLeaderBoardViewEntry().getUsername(),
                Integer.toString(winnerStreak.getStreak()), "Games"));
        achievements.add(new Achievement("Longest loss streak", looserStreak.getLeaderBoardViewEntry().getUsername(),
                Integer.toString(looserStreak.getStreak()), "Games"));
        achievements.add(new Achievement("Flawless victory (10-0 win)", "Winners: " + String.join(" and ",
                flawlessVictoryWinners), String.join(" and ", flawlessVictoryLoosers), "Loosers"));

        final AchievementsView achievementsView = new AchievementsView();
        achievementsView.setAchievements(achievements);

        return achievementsView;
    }

    private GetStreakResult getStreak(List<Match> matches, List<LeaderBoardViewEntry> entries, boolean winStreak) {
        LeaderBoardViewEntry player = null;
        int currentStreak = 0;
        int streak = 0;

        for (LeaderBoardViewEntry e : entries) {
            int count = 0;
            for (Match match : matches) {
                count++;
                if (getPlayers(match, winStreak).contains(e.getUsername())) {
                    currentStreak++;
                }

                if (getPlayers(match, !winStreak).contains(e.getUsername()) || count == matches.size()) {
                    if (currentStreak > streak) {
                        streak = currentStreak;
                        player = e;
                    }

                    currentStreak = 0;
                }
            }

            currentStreak = 0;
        }

        return new GetStreakResult(streak, player);
    }

    private String getPlayers(Match m, boolean winners) {
        if (m.getMatchResult().didTeam1Win() == winners) {
            return m.getPlayers().get(0) + ";" + m.getPlayers().get(1);
        }

        return m.getPlayers().get(2) + ";" + m.getPlayers().get(3);
    }

    private void getFlawlessVictory(List<Match> matches, List<String> winners, List<String> loosers) {
        final Comparator<Match> timeComparator = Comparator.comparing(Match::getTimeStampUtc);
        final Iterator<Match> orderedMatches = matches.stream().filter(match -> match.getPlayers().size() == 4).sorted
                (timeComparator.reversed()).iterator();

        while (orderedMatches.hasNext()) {
            final Match match = orderedMatches.next();
            if (match.getMatchResult().getTeam1Score() == 0) {
                winners.addAll(match.getPlayers().subList(2, 3));
                loosers.addAll(match.getPlayers().subList(0, 1));
                return;
            } else if (match.getMatchResult().getTeam2Score() == 0) {
                winners.addAll(match.getPlayers().subList(0, 1));
                loosers.addAll(match.getPlayers().subList(2, 3));
                return;
            }
        }
    }

    private class GetStreakResult {
        int streak;
        LeaderBoardViewEntry leaderBoardViewEntry;

        GetStreakResult(int streak, LeaderBoardViewEntry leaderBoardViewEntry) {
            this.streak = streak;
            this.leaderBoardViewEntry = leaderBoardViewEntry;
        }

        public int getStreak() {
            return streak;
        }

        public LeaderBoardViewEntry getLeaderBoardViewEntry() {
            return leaderBoardViewEntry;
        }
    }
}
