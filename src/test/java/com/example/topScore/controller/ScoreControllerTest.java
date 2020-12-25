package com.example.topScore.controller;

import com.example.topScore.model.Filter;
import com.example.topScore.model.Score;
import com.example.topScore.service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScoreController.class)
public class ScoreControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ScoreService scoreService;

	private Score score = new Score();

	@BeforeEach
	void setUp() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date c= sdf.parse("2020-12-31 11:59");
		score.setId(1);
		score.setScore(100);
		score.setTime(c);
		score.setName("RANDOM");
	}

	@Test
	void givenScore_whenCreateScore_thenGetScoreObject() throws Exception {
		Mockito.when(scoreService.createScore(score)).thenReturn(score);

		mvc.perform(post("/score/add")
							.content("{\"score\": 100, \"name\": \"random\", \"time\": \"2020-12-23T10:14\"}")
							.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	void givenWrongContentType_ExpectWrongContentType() throws Exception {
		Mockito.when(scoreService.createScore(new Score())).thenReturn(null);

		mvc.perform(post("/score/add")
							.content("{\"score\": 100, \"time\": \"2020-12-23T10:14\"}"))
				.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	void givenScoreId_whenGetScore_thenScoreObject() throws Exception {
		Mockito.when(scoreService.getScoreById(1)).thenReturn(new ResponseEntity<>(score, HttpStatus.OK));

		mvc.perform(get("/score/get/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(score.getName())));
	}

	@Test
	void givenWrongScoreId_whenGetScore_thenGetErrorResponse() throws Exception {
		Mockito.when(scoreService.getScoreById(2)).thenReturn(new ResponseEntity<>("{\"error\":\"No record found with the ID\"}", HttpStatus.NO_CONTENT));

		mvc.perform(get("/score/get/2"))
				.andExpect(status().isNoContent())
				.andExpect(content().string(containsString("No record found with the ID")));
	}

	@Test
	void whenCallDeleteScoreWithId_getDeletedScore() throws Exception {
		Mockito.when(scoreService.deleteScore(1)).thenReturn(new ResponseEntity<>(score, HttpStatus.ACCEPTED));

		mvc.perform(delete("/score/delete/1"))
				.andExpect(status().isAccepted())
				.andExpect(content().string(containsString("RANDOM")));
	}

	@Test
	void whenCallHistory_WithValidBody() throws Exception {
		Map<String, Object> map = new HashMap<>();
		Mockito.when(scoreService.getScoreHistoryName("RANDOM")).thenReturn(map);

		mvc.perform(get("/score/delete/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("")));
	}
}
