package com.example.demo.repository;

import com.example.demo.Screening;
import com.example.demo.Theater;
import com.example.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s " +
            "JOIN FETCH s.theater " +
            "WHERE s.movie.id = :movieId " +
            "AND s.showDate = :date " +
            "ORDER BY s.theater.name ASC, s.showTime ASC")
    List<Screening> findByMovieAndDate(
            @Param("movieId") Long movieId,
            @Param("date") LocalDate date
    );

    boolean existsByTheaterAndMovieAndShowDateAndShowTime(
            Theater theater,
            Movie movie,
            LocalDate showDate,
            LocalTime showTime
    );
}
