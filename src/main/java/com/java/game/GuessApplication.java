package com.java.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.java.game.service.GameService;
import com.java.game.entity.Player;
import com.java.game.entity.GameSession;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class GuessApplication implements CommandLineRunner {

	@Autowired
	private GameService gameService;

	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(GuessApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Welcome to the Number Guessing Game!");
		System.out.println("=====================================");

		// Get player name
		System.out.print("Enter your name: ");
		String playerName = scanner.nextLine().trim();
		Player player = gameService.getOrCreatePlayer(playerName);

		System.out.println("Hello, " + playerName + "! Let's play!");

		boolean playAgain = true;
		while (playAgain) {
			playGame(player);

			// Show stats after each game
			gameService.showPlayerStats(player);

			// Ask if player wants to play again
			System.out.print("\nWould you like to play again? (y/n): ");
			String response = scanner.nextLine().toLowerCase();
			if (!response.equals("y") && !response.equals("yes")) {
				playAgain = false;
				System.out.println("\nThanks for playing! Goodbye! 👋");
			}
		}

		scanner.close();
		System.exit(0);
	}

	private void playGame(Player player) {
		int targetNumber = random.nextInt(100) + 1;
		int maxAttempts = 7;
		int attempts = 0;
		boolean hasWon = false;

		System.out.println("\nI'm thinking of a number between 1 and 100.");
		System.out.println("You have " + maxAttempts + " attempts to guess it!");

		// Create game session
		GameSession session = gameService.createGameSession(player, targetNumber, maxAttempts);

		while (attempts < maxAttempts && !hasWon) {
			System.out.print("\nAttempt " + (attempts + 1) + "/" + maxAttempts + " - Enter your guess: ");
			try {
				int guess = scanner.nextInt();
				scanner.nextLine(); // Clear buffer
				attempts++;

				String result;
				if (guess == targetNumber) {
					hasWon = true;
					result = "correct";
					System.out.println("🎉 Congratulations! You guessed it!");
					System.out.println("The number was " + targetNumber);
					System.out.println("You won in " + attempts + " attempts!");

					// Calculate and display score
					int score = Math.max(0, (maxAttempts - attempts + 1) * 10);
					System.out.println("Your score: " + score + " points");

					// Update session
					session.setWon(true);
					session.setScore(score);

				} else if (guess < targetNumber) {
					result = "too_low";
					System.out.println("Too low! Try a higher number.");
					if (attempts < maxAttempts) {
						System.out.println("Attempts remaining: " + (maxAttempts - attempts));
					}
				} else {
					result = "too_high";
					System.out.println("Too high! Try a lower number.");
					if (attempts < maxAttempts) {
						System.out.println("Attempts remaining: " + (maxAttempts - attempts));
					}
				}

				// Save guess and update session
				gameService.saveGuess(session, attempts, guess, result);
				session.setAttemptsUsed(attempts);
				gameService.updateGameSession(session);

			} catch (Exception e) {
				System.out.println("Please enter a valid number!");
				scanner.nextLine(); // Clear the invalid input
			}
		}

		if (!hasWon) {
			System.out.println("\n💔 Game Over! You've used all your attempts.");
			System.out.println("The number was: " + targetNumber);

			// Update final session
			session.setWon(false);
			session.setScore(0);
			session.setAttemptsUsed(attempts);
			gameService.updateGameSession(session);
		}
	}
}