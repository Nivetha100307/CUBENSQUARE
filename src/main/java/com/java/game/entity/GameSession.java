package com.java.game.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "game_sessions")
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "target_number", nullable = false)
    private Integer targetNumber;

    @Column(name = "attempts_used", nullable = false)
    private Integer attemptsUsed;

    @Column(name = "max_attempts", nullable = false)
    private Integer maxAttempts;

    @Column(name = "won", nullable = false)
    private Boolean won;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "played_at")
    private LocalDateTime playedAt;

    @OneToMany(mappedBy = "gameSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Guess> guesses;

    // Constructors
    public GameSession() {
        this.playedAt = LocalDateTime.now();
    }

    public GameSession(Player player, Integer targetNumber, Integer maxAttempts) {
        this.player = player;
        this.targetNumber = targetNumber;
        this.maxAttempts = maxAttempts;
        this.attemptsUsed = 0;
        this.won = false;
        this.score = 0;
        this.playedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(Integer targetNumber) {
        this.targetNumber = targetNumber;
    }

    public Integer getAttemptsUsed() {
        return attemptsUsed;
    }

    public void setAttemptsUsed(Integer attemptsUsed) {
        this.attemptsUsed = attemptsUsed;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Boolean getWon() {
        return won;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }

    public List<Guess> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<Guess> guesses) {
        this.guesses = guesses;
    }
}
