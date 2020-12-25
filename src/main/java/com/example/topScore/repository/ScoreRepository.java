package com.example.topScore.repository;

import com.example.topScore.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface ScoreRepository extends JpaRepository<Score, Integer> {
	List<Score> findByNameOrderByTimeAsc(String name);
	Score findFirstByNameOrderByScoreAsc(String name);
	Score findFirstByNameOrderByScoreDesc(String name);
	Page<Score> findByNameIn(List<String> names, Pageable pageable);
	Page<Score> findAllByTimeBefore(Date date, Pageable pageable);
	Page<Score> findAllByTimeAfter(Date date, Pageable pageable);
	@Query(value = "SELECT AVG(s.score) FROM Score s")
	Integer averageScore();
}
