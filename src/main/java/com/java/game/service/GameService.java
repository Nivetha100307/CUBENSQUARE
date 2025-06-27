package com.java.game.service;

import com.java.game.entity.Player;
import com.java.game.entity.GameSession;
import com.java.game.entity.Guess;
import com.java.game.repository.PlayerRepository;
import com.java.game.repository.GameSessionRepository;
import com.java.game.repository.GuessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private GuessRepository guessRepository;

    public Player getOrCreatePlayer(String playerName) {
        return playerRepository.findByPlayerName(playerName)
                .orElseGet(() -> {
                    Player newPlayer = new Player(playerName);
                    return playerRepository.save(newPlayer);
                });
    }

    public GameSession createGameSession(Player player, int targetNumber, int maxAttempts) {
        GameSession session = new GameSession(player, targetNumber, maxAttempts);
        return gameSessionRepository.save(session);
    }

    public GameSession updateGameSession(GameSession session) {
        return gameSessionRepository.save(session);
    }

    public Guess saveGuess(GameSession session, int attemptNumber, int guessValue, String result) {
        Guess guess = new Guess(session, attemptNumber, guessValue, result);
        return guessRepository.save(guess);
    }

    public void showPlayerStats(Player player) {
        Long totalGames = gameSessionRepository.countByPlayer(player);
        Long gamesWon = gameSessionRepository.countWonGamesByPlayer(player);
        Double avgScore = gameSessionRepository.getAverageScoreByPlayer(player);
        Integer bestScore = gameSessionRepository.getMaxScoreByPlayer(player);

        System.out.println("\n=== YOUR STATISTICS ===");
        System.out.println("Total Games: " + (totalGames != null ? totalGames : 0));
        System.out.println("Games Won: " + (gamesWon != null ? gamesWon : 0));
        System.out.println("Average Score: " + String.format("%.1f", avgScore != null ? avgScore : 0.0));
        System.out.println("Best Score: " + (bestScore != null ? bestScore : 0));

        if (totalGames != null && totalGames > 0) {
            double winRate = (gamesWon != null ? gamesWon : 0) * 100.0 / totalGames;
            System.out.println("Win Rate: " + String.format("%.1f%%", winRate));
        }
    }
}