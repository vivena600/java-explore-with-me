package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.module.Hit;
import ru.practicum.module.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {
    @Query("SELECT new ru.practicum.module.Stat(h.app, h.uri, COUNT(DISTINCT h.ip))" +
            " FROM Hit h " +
            "WHERE h.timestamp >= :start " +
            "AND h.timestamp <= :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<Stat> findHitUniqueIp(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.module.Stat(h.app, h.uri, COUNT(h.ip)) FROM Hit h " +
            "WHERE h.timestamp >= :start " +
            "AND h.timestamp <= :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<Stat> findHitNotUniqueIp(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.module.Stat(h.app, h.uri, COUNT(DISTINCT h.ip))" +
            " FROM Hit h " +
            "WHERE h.timestamp >= :start " +
            "AND h.timestamp <= :end " +
            "AND h.uri IN :uri " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<Stat> findHitUniqueIpBetweenUri(@Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end,
                               @Param("uri") List<String> uri);

    @Query("SELECT new ru.practicum.module.Stat(h.app, h.uri, COUNT(h.ip)) FROM Hit h " +
            "WHERE h.timestamp >= :start " +
            "AND h.timestamp <= :end " +
            "AND h.uri IN :uri " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<Stat> findHitNotUniqueIpBetweenUri(@Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end,
                                 @Param("uri") List<String> uri);
}
