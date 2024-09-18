package com.pedro.orso.outsera.repository;

import com.pedro.orso.outsera.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(String name);

    @Query(value = """
            WITH ProducerIntervals AS (
                SELECT p.name AS producer_name,
                       (m.release_year - LAG(m.release_year) OVER (PARTITION BY p.id ORDER BY m.release_year)) AS "interval",
                       LAG(m.release_year) OVER (PARTITION BY p.id ORDER BY m.release_year) AS previous_win,
                       m.release_year AS following_win
                FROM producer p
                JOIN movie_producer mp ON p.id = mp.producer_id
                JOIN movie m ON mp.movie_id = m.id
                WHERE m.winner = true
            )
            SELECT producer_name, "interval", previous_win, following_win
            FROM ProducerIntervals
            WHERE "interval" IS NOT NULL
            AND ("interval" = (SELECT MIN("interval") FROM ProducerIntervals WHERE "interval" IS NOT NULL)
                 OR "interval" = (SELECT MAX("interval") FROM ProducerIntervals WHERE "interval" IS NOT NULL));
            """, nativeQuery = true)
    List<Object[]> getProducersWithMinAndMaxIntervals();
}
