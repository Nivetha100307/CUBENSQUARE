package com.java.game.repository;

import com.java.game.entity.Guess;
import com.java.game.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuessRepository extends JpaRepository<Guess, Long> {
    List<Guess> findByGameSessionOrderByAttemptNumber(GameSession gameSession);
}