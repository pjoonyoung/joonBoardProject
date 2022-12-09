package com.joonyoung.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joonyoung.board.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	
}


