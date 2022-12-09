package com.joonyoung.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joonyoung.board.dto.QuestionDto;
import com.joonyoung.board.entity.Answer;
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
	
	List<QuestionDto> questionDtos = new ArrayList<QuestionDto>();
	
	public List<QuestionDto> getQuestionList() {
		
		List<Question> questionList = questionRepository.findAll();

		for(int i=0;i<questionList.size();i++) {
	         Question question = questionList.get(i);
	         
	         QuestionDto questionDto = new QuestionDto();
	         
	         questionDto.setId(question.getId());
	         questionDto.setContent(question.getContent());
	         questionDto.setSubject(question.getSubject());
	         questionDto.setCreateDate(question.getCreateDate());
	         questionDto.setAnswerList(question.getAnswerList());
	         
	         questionDtos.add(questionDto);
	      }
		
		return questionDtos;
		
	}
	
	public QuestionDto getQuestion(Integer id) {
			
			Optional<Question> optQuestion = questionRepository.findById(id);
			
			QuestionDto questionDto = new QuestionDto();
			if(optQuestion.isPresent()) {
				Question question = optQuestion.get();
				questionDto.setId(question.getId());
				questionDto.setContent(question.getContent());
				questionDto.setSubject(question.getSubject());
				questionDto.setAnswerList(question.getAnswerList());
				questionDto.setCreateDate(question.getCreateDate());
				return questionDto;
			} else {
				throw new DataNotFoundException("해당 질문이 없습니다.");
			}
			
	}

	public void questionCreate(String subject, String content) {
		Question question = new Question();
		question.setContent(content);
		question.setSubject(subject);
		question.setCreateDate(LocalDateTime.now());
		
		questionRepository.save(question);
	}
}

