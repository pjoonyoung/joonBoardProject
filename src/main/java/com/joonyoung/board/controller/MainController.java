package com.joonyoung.board.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joonyoung.board.dto.QuestionDto;
import com.joonyoung.board.entity.Question;
import com.joonyoung.board.repository.AnswerRepository;
import com.joonyoung.board.repository.QuestionRepository;
import com.joonyoung.board.service.AnswerService;
import com.joonyoung.board.service.Questionservice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
//	@Autowired
//	QuestionRepository questionRepository;
//	
//	@Autowired
//	AnswerRepository answerRepository;
	
//	private final QuestionRepository questionRepository;
//	private final AnswerRepository answerRepository;
	
	private final Questionservice questionservice;
	private final AnswerService answerservice;
	
	@RequestMapping(value = "/")
	public String hoom() {
		return "redirect:list";
	}
	
	@RequestMapping(value = "/index")
	public String index() {
		return "redirect:list";
	}
	
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
//		List<Question> questionList = questionRepository.findAll();
		
		List<QuestionDto> questionList = questionservice.getQuestionList();
		
		model.addAttribute("questionList", questionList);
		
		return "question_list";
	}
	
	@RequestMapping(value = "/questionView/{id}")
	public String questionView(Model model, @PathVariable("id") Integer id) {
		
		QuestionDto question = questionservice.getQuestion(id);
		
		model.addAttribute("question", question);
		
		return "question_View";
	}
	
	@RequestMapping(value = "/answercreate/{id}")
	public String answercreate(@RequestParam String content, @PathVariable("id") Integer id) {
		
		answerservice.answerCreate(content, id);
		
		return String.format("redirect:/questionView/%s", id);
	}
}
