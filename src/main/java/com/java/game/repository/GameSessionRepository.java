package com.java.game.repository;

import com.java.game.entity.GameSession;
import com.java.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    List<GameSession> findByPlayerOrderByPlayedAtDesc(Player player);

    @Query("SELECT COUNT(gs) FROM GameSession gs WHERE gs.player = :player")
    Long countByPlayer(@Param("player") Player player);

    @Query("SELECT COUNT(gs) FROM GameSession gs WHERE gs.player = :player AND gs.won = true")
    Long countWonGamesByPlayer(@Param("player") Player player);

    @Query("SELECT AVG(gs.score) FROM GameSession gs WHERE gs.player = :player")
    Double getAverageScoreByPlayer(@Param("player") Player player);

    @Query("SELECT MAX(gs.score) FROM GameSession gs WHERE gs.player = :player")
    Integer getMaxScoreByPlayer(@Param("player") Player player);
}
