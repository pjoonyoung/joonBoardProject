package com.joonyoung.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//게시판 번호
	
	@Column(length = 100)
	private String subject;//질문게시판 제목
	
	@Column(length = 1000)
	private String content;//게시판 내용
	
	private LocalDateTime createDate;//글 등록일시
}
