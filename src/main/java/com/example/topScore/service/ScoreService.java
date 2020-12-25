package com.example.topScore.service;

import com.example.topScore.model.Filter;
import com.example.topScore.model.Score;
import com.example.topScore.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ScoreService {

	@Autowired
	ScoreRepository scoreRepository;

	public Score createScore(Score score){
		score.setName(score.getName().toUpperCase());
		return scoreRepository.save(score);
	}

	public ResponseEntity<Object> getScoreById(Integer id){
		Score score = scoreRepository.findById(id).orElse(null);
		if(Objects.nonNull(score)){
			return new ResponseEntity<>(score, HttpStatus.OK);
		}
		return new ResponseEntity<>("{\"error\":\"No record found with the ID\"}", HttpStatus.NO_CONTENT);
	}

	public Map<String, Object> getScoreHistoryName(String name){
		Map<String, Object> result = new HashMap<>();
		result.put("highest", scoreRepository.findFirstByNameOrderByScoreDesc(name));
		result.put("lowest", scoreRepository.findFirstByNameOrderByScoreAsc(name));
		result.put("average", scoreRepository.averageScore());
		result.put("history", scoreRepository.findByNameOrderByTimeAsc(name));
		return result;
	}

	public ResponseEntity<Object> deleteScore(Integer id){
		Score score = scoreRepository.findById(id).orElse(null);
		if(Objects.nonNull(score)){
			scoreRepository.deleteById(id);
			return new ResponseEntity<>(score, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("{\"error\":\"No record found with the ID\"}", HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Object> filterScoreList(Filter filter) {
		Pageable paging = PageRequest.of(filter.getPage(), filter.getSize());
		if(filter.getFilterBy().equals("name")
				&& Objects.nonNull(filter.getPlayerName())){
			return new ResponseEntity<>(scoreRepository.findByNameIn(filter.getPlayerName(), paging), HttpStatus.OK);
		}else if(filter.getFilterBy().equals("time")
				&& Objects.nonNull(filter.getTime()) && Objects.nonNull(filter.getTimeCondition())){
			Page<Score> result = filter.getTimeCondition().equals("before") ?
					scoreRepository.findAllByTimeBefore(filter.getTime(), paging) :
					scoreRepository.findAllByTimeAfter(filter.getTime(), paging);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>("{\"error\":\"Invalid Request\"}", HttpStatus.BAD_REQUEST);
	}
}
