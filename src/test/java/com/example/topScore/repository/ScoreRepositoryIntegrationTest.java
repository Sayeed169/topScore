package com.example.topScore.repository;

import com.example.topScore.model.Score;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ScoreRepositoryIntegrationTest {

	@Autowired
	ScoreRepository scoreRepository;

	@Autowired
	TestEntityManager entityManager;

	private Score score = new Score();

	@BeforeEach
	public void setUp(){
		score.setName("PLAYER");
		score.setTime(new Date());
		score.setScore(100);
	}

	@Test
	void whenCreate_thenReturnScore(){
		scoreRepository.save(score);
		Optional<Score> found = scoreRepository.findById(score.getId());
		assertThat(found.get().getName()).isEqualTo(score.getName());
	}

	@Test
	void whenFindById_thenReturnScore() {
		entityManager.persist(score);
		entityManager.flush();

		Optional<Score> found = scoreRepository.findById(score.getId());
		assertThat(found.get().getName()).isEqualTo(score.getName());
	}
}
