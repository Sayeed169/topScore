package com.example.topScore.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Filter {

	private String filterBy;
	private Date time;
	private String timeCondition;
	private List<String> playerName;
	private Integer page = 0;
	private Integer size = 5;
}
