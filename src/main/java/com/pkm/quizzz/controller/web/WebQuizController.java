package com.pkm.quizzz.controller.web;

import java.util.Map;

import javax.validation.Valid;

import com.pkm.quizzz.model.User;
import com.pkm.quizzz.repository.QuizRepository;
import com.pkm.quizzz.repository.UserRepository;
import com.pkm.quizzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.pkm.quizzz.controller.utils.RestVerifier;
import com.pkm.quizzz.exceptions.ModelVerificationException;
import com.pkm.quizzz.model.AuthenticatedUser;
import com.pkm.quizzz.model.Question;
import com.pkm.quizzz.model.Quiz;
import com.pkm.quizzz.service.QuestionService;
import com.pkm.quizzz.service.QuizService;
import com.pkm.quizzz.service.accesscontrol.AccessControlService;

@Controller
public class WebQuizController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
	QuizService quizService;

	@Autowired
	QuestionService questionService;

	@Autowired
	AccessControlService<Quiz> accessControlServiceQuiz;

	@Autowired
	AccessControlService<Question> accessControlServiceQuestion;

	//контроллер создания и прохождения тестов

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model m) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User u = userRepository.findByUsername(username);
		if(u != null) {
			m.addAttribute("test", u.isTester());
			m.addAttribute("auth", u.isAdmin());
		}
		return "home";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User u = userRepository.findByUsername(username);
        if (u != null) {
            m.addAttribute("auth", u.isAdmin());
            if(u.isAdmin()){
                m.addAttribute("users", userRepository.findAll());
                m.addAttribute("tests", quizRepository.findAll());
                return "admin";
            }else {
                return "redirect:/user/login";
            }
        }else {
            return "redirect:/user/login";
        }
	}

	@RequestMapping(value = "/createQuiz", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(Map<String, Object> model, Model m) {
		Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
		if(!authentication1.getName().equals("anonymousUser")) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			User u = userRepository.findByUsername(username);
			m.addAttribute("auth",u.isAdmin());
			return "createQuiz";
		}else{
			return "redirect:/user/login";
		}
	}

	@RequestMapping(value = "/createQuiz", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(@AuthenticationPrincipal AuthenticatedUser user, @Valid Quiz quiz, BindingResult result,
			Map<String, Object> model) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(!authentication.getName().equals("anonymousUser")) {
			Quiz newQuiz;

			try {
				RestVerifier.verifyModelResult(result);
				newQuiz = quizService.save(quiz, user.getUser());
			} catch (ModelVerificationException e) {
				return "createQuiz";
			}

			return "redirect:/editQuiz/" + newQuiz.getId();
		}else{
			return "redirect:/user/login";
		}
	}

	@RequestMapping(value = "/editQuiz/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editQuiz(@PathVariable long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);
		accessControlServiceQuiz.canCurrentUserUpdateObject(quiz);

		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("editQuiz");

		return mav;
	}

	@RequestMapping(value = "/editAnswer/{question_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editAnswer(@PathVariable long question_id) {
		Question question = questionService.find(question_id);
		accessControlServiceQuestion.canCurrentUserUpdateObject(question);

		ModelAndView mav = new ModelAndView();
		mav.addObject("question", question);
		mav.setViewName("editAnswers");

		return mav;
	}

	@RequestMapping(value = "/quiz/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ModelAndView getQuiz(@PathVariable long quiz_id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!authentication.getName().equals("anonymousUser")) {
			Quiz quiz = quizService.find(quiz_id);

			ModelAndView mav = new ModelAndView();
			mav.addObject("quiz", quiz);
			mav.setViewName("quizView");

			return mav;
		}else{
			ModelAndView modelView =  new ModelAndView("redirect:/user/login");
			return modelView;
		}
	}

	@RequestMapping(value = "/quiz/{quiz_id}/play", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ModelAndView playQuiz(@PathVariable long quiz_id) {
        	ModelAndView mav = new ModelAndView();
			Quiz quiz = quizService.find(quiz_id);
			mav.addObject("quiz", quiz);
			mav.setViewName("playQuiz");
			return mav;
	}

	@RequestMapping(value = "/admin/userTest", method = RequestMethod.POST)
	public String userTest(@RequestParam("idToDelete") String idToDelete) {
		System.out.println("!!!"+Long.parseLong(idToDelete));
		User u = userService.find(Long.parseLong(idToDelete));
		u.setTester(true);
		userRepository.save(u);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin/userAdm", method = RequestMethod.POST)
	public String userAdm(@RequestParam("idToDelete") String idToDelete) {
		System.out.println("!!!"+Long.parseLong(idToDelete));
		User u = userService.find(Long.parseLong(idToDelete));
		u.setAdmin(true);
		userRepository.save(u);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin/userDelete", method = RequestMethod.POST)
	public String userDelete(@RequestParam("idToDelete") String idToDelete) {
		System.out.println("!!!"+Long.parseLong(idToDelete));
		userRepository.delete(Long.parseLong(idToDelete));
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin/quizDelete", method = RequestMethod.POST)
	public String quizDelete(@RequestParam("idToDelete") String idToDelete) {
		System.out.println("!!!"+Long.parseLong(idToDelete));
		quizRepository.delete(Long.parseLong(idToDelete));
		return "redirect:/admin";
	}
}
