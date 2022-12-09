package com.joonyoung.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joonyoung.board.entity.Answer;
import com.joonyoung.board.entity.Question;
import com.joonyoung.board.repository.AnswerRepository;
import com.joonyoung.board.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;
	
	public void answerCreate(String content, Integer id) {
		
		Optional<Question> optQuestion = questionRepository.findById(id);
		Question question = optQuestion.get();
		
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateTime(LocalDateTime.now());
		answer.setQuestion(question);
		
		answerRepository.save(answer);
		
	}
}
