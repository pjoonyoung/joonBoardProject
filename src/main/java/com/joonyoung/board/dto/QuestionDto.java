package com.joonyoung.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.joonyoung.board.entity.Answer;

import lombok.Data;

@Data
public class QuestionDto {
	private Integer id;
	private String subject;
	private String content;
	private LocalDateTime createDate;
	
	private List<Answer> answerList;
}
