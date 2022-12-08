package com.joonyoung.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joonyoung.board.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

}


