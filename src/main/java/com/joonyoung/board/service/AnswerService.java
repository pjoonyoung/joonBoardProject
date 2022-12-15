package com.joonyoung.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joonyoung.board.entity.Answer;
import com.joonyoung.board.entity.Question;
import com.joonyoung.board.entity.SiteMember;
import com.joonyoung.board.exception.DataNotFoundException;
import com.joonyoung.board.repository.AnswerRepository;
import com.joonyoung.board.repository.MemberRepository;
import com.joonyoung.board.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;
	private final MemberService memberService;
	
	public void answerCreate(String content, Integer id, String username) {
		
		Optional<Question> optQuestion = questionRepository.findById(id);
		Question question = optQuestion.get();
		
		SiteMember siteMember = memberService.getMemberInfo(username);
		
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateTime(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setWriter(siteMember);
		
		answerRepository.save(answer);
		
	}
	
	public Answer getAnswer(Integer id) {
		
		Optional<Answer> optAnswer = answerRepository.findById(id);
		
		if(optAnswer.isPresent()) {
			return optAnswer.get();
		} else {
			throw new DataNotFoundException("해당 답변이 없습니다.");
		}		
	}
	
	public void answerModify(String content, Answer answer) {
		
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());//수정시간을 지금 현재시간으로 셋팅
		
		answerRepository.save(answer);//새로운 수정
		
	}
	
	public void answerDelete(Integer id) {
		
		answerRepository.deleteById(id);
		
	}
}
