package com.joonyoung.board.dto;

import java.time.LocalDateTime;

import com.joonyoung.board.entity.Question;

import lombok.Data;

@Data
public class AnswerDto {
	private Integer id;
	private String content;
	private LocalDateTime createTime;
	private Question question;
}
