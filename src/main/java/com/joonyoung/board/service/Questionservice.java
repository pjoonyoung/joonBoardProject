package com.joonyoung.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joonyoung.board.dto.QuestionDto;
import com.joonyoung.board.entity.Question;
import com.joonyoung.board.exception.DataNotFoundException;
import com.joonyoung.board.repository.AnswerRepository;
import com.joonyoung.board.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Questionservice {
	
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	
	public List<Question> getQuestionList() {
		
		List<Question> questionList = questionRepository.findAll();

		
		return questionList;
		
	}
	
	public Question getQuestion(Integer id) {
		
		Optional<Question> optQuestion = questionRepository.findById(id);
		if(optQuestion.isPresent()) {
			Question question = optQuestion.get();
			return question;
		} else {
			throw new DataNotFoundException("해당 질문이 없습니다.");
		}
		
		
	}
}
