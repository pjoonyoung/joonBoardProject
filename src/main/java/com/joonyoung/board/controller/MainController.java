package com.joonyoung.board.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.joonyoung.board.dto.AnswerForm;
import com.joonyoung.board.dto.MemberForm;
import com.joonyoung.board.dto.QuestionDto;
import com.joonyoung.board.dto.QuestionForm;
import com.joonyoung.board.entity.Answer;
import com.joonyoung.board.entity.Question;
import com.joonyoung.board.entity.SiteMember;
import com.joonyoung.board.repository.AnswerRepository;
import com.joonyoung.board.repository.QuestionRepository;
import com.joonyoung.board.service.AnswerService;
import com.joonyoung.board.service.MemberService;
import com.joonyoung.board.service.Questionservice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
//	@Autowired
//	private QuestionRepository questionRepository;
//	
//	@Autowired
//	private AnswerRepository answerRepository;
	
//	private final QuestionRepository questionRepository;
//	private final AnswerRepository answerRepository;
	
	private final Questionservice questionService;
	private final AnswerService answerService; 
	private final MemberService memberService;
	
	@RequestMapping(value = "/")	
	public String home() {
		return "redirect:list";
	}
	
	@RequestMapping(value = "/index")	
	public String index() {
		return "redirect:list";
	}
	
	@RequestMapping(value = "/list")	
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		
//		List<Question> questionList = questionRepository.findAll();
		
//		List<QuestionDto> questionList = questionService.getQuestionList();
		
		Page<Question> paging = questionService.getList(page);
		
		model.addAttribute("pageCount", paging.getTotalElements());//전체 게시물 개수
		
		model.addAttribute("paging", paging);
		
		return "question_list";
	}
	
	@RequestMapping(value = "/questionView/{id}")
	public String questionView(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		
		Question question = questionService.getQuestion(id);
		
		model.addAttribute("question", question);
		
		return "question_View";
	}
	
	@PreAuthorize("isAuthenticated")
	@PostMapping(value = "/answerCreate/{id}")
	public String answerCreate(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		
		Question question = questionService.getQuestion(id);
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("question", question);
			return "question_View";
		}
		
		answerService.answerCreate(answerForm.getContent(), id, principal.getName());
				
		return String.format("redirect:/questionView/%s", id);
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping(value = "/questionCreate")
	public String questionCreate(QuestionForm questionForm) {
				
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated")
	@PostMapping(value = "/questionCreate")
	public String questionCreateOk(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		questionService.questionCreate(questionForm.getSubject(), questionForm.getContent(), principal.getName());
				
		return "redirect:list";
	}
	
	@RequestMapping(value = "/join")
	public String join(MemberForm memberForm) {
				
		return "join_form";
	}
	
	@PostMapping(value = "/joinOk")
	public String joinOk(@Valid MemberForm memberForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "join_form";
		}
		
		
		try {
			memberService.memberCreate(memberForm.getUsername(), memberForm.getPassword(), memberForm.getEmail());
		}catch(Exception e){
			e.printStackTrace();
			bindingResult.reject("joinFail", "이미 등록된 아이디입니다.");
			return "join_form";
		}
		return "redirect:list";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "login_form";
	}
	
	@PreAuthorize("isAuthenticated")
	@RequestMapping(value = "/modify/{id}")
	public String modify(@PathVariable("id") Integer id, QuestionForm questionForm, Principal principal) {
		
		Question question = questionService.getQuestion(id);
		
		if(!question.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		} 
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated")
	@PostMapping(value = "/modify/{id}")
	public String questionModify(@PathVariable("id") Integer id, @Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = questionService.getQuestion(id);
		
		questionService.modify(questionForm.getSubject(), questionForm.getContent(), question);
				
		return String.format("redirect:/questionView/%s", id);
	}
	
	@PreAuthorize("isAuthenticated")
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer id, QuestionForm questionForm, Principal principal) {
		
		Question question = questionService.getQuestion(id);
		
		if(!question.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		} 
		
		questionService.delete(id);
		
		return "redirect:/index";
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping(value = "/answerModify/{id}")
	public String answermodify(@PathVariable("id") Integer id, AnswerForm answerForm, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		
		if(!answer.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		} 
		
		answerForm.setContent(answer.getContent());
		
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated")
	@PostMapping(value = "/answerModify/{id}")
	public String answerModify(@PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		
		Answer answer = answerService.getAnswer(id);
		
		answerService.answerModify(answerForm.getContent(), answer);
				
		return String.format("redirect:/questionView/%s", answer.getQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping(value = "/answerdelete/{id}")
	public String answerdelete(@PathVariable("id") Integer id, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		
		if(!answer.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		} 
		
		answerService.answerDelete(id);
		
		return String.format("redirect:/questionView/%s", answer.getQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping(value = "/questionLike/{id}")
	public String questionLike(@PathVariable("id") Integer id, Principal principal) {
		
		Question question = questionService.getQuestion(id);
		SiteMember siteMember = memberService.getMemberInfo(principal.getName());
		
		questionService.questionLike(question, siteMember);
		return String.format("redirect:/questionView/%s", id);
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping(value = "/answerLike/{id}")
	public String answerLike(@PathVariable("id") Integer id, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		SiteMember siteMember = memberService.getMemberInfo(principal.getName());
		
		answerService.answerLike(answer, siteMember);
		return String.format("redirect:/questionView/%s", answer.getQuestion().getId());
	}
	
}
