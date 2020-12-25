package com.example.topScore.controller;

import com.example.topScore.model.Filter;
import com.example.topScore.model.Score;
import com.example.topScore.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/score")
public class ScoreController {

	@Autowired
	ScoreService scoreService;

	@RequestMapping("/add")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Score createNewScore(@RequestBody Score score){
		return scoreService.createScore(score);
	}

	@RequestMapping("/get/{id}")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getScoreById(@PathVariable Integer id){
		return scoreService.getScoreById(id);
	}

	@RequestMapping("/get/list")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getScoreByName(@RequestBody Filter filter){
		return scoreService.filterScoreList(filter);
	}

	@RequestMapping("delete/{id}")
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteScore(@PathVariable Integer id){
		return scoreService.deleteScore(id);
	}

	@RequestMapping("/get/history/{name}")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getPlayerHistory(@PathVariable String name){
		return scoreService.getScoreHistoryName(name);
	}
}
