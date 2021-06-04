package com.pkm.quizzz.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pkm.quizzz.service.UserService;

@Controller
@RequestMapping("/user")
public class WebUserController {

	@Autowired
	UserService userService;

	//контроллер тестов создателя

	@RequestMapping(value = "/{user_id}/quizzes", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public String getQuizzesForUser(@PathVariable Long user_id) {
		userService.find(user_id);
		return "error";
	}

	@RequestMapping(value = "/quizzes", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String getQuizzesForAuthenticatedUser() {
		return "myQuizzes";
	}
}
