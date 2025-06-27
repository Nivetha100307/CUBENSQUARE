// 3. src/main/java/com/java/game/entity/Guess.java
package com.java.game.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "guesses")
public class Guess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private GameSession gameSession;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(name = "guess_value", nullable = false)
    private Integer guessValue;

    @Column(name = "result", nullable = false, length = 20)
    private String result;

    @Column(name = "guessed_at")
    private LocalDateTime guessedAt;

    // Constructors
    public Guess() {
        this.guessedAt = LocalDateTime.now();
    }

    public Guess(GameSession gameSession, Integer attemptNumber, Integer guessValue, String result) {
        this.gameSession = gameSession;
        this.attemptNumber = attemptNumber;
        this.guessValue = guessValue;
        this.result = result;
        this.guessedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Integer getGuessValue() {
        return guessValue;
    }

    public void setGuessValue(Integer guessValue) {
        this.guessValue = guessValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getGuessedAt() {
        return guessedAt;
    }

    public void setGuessedAt(LocalDateTime guessedAt) {
        this.guessedAt = guessedAt;
    }
}