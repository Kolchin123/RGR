package com.pkm.quizzz.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.pkm.quizzz.controller.utils.ErrorInfo;
import com.pkm.quizzz.exceptions.ActionRefusedException;
import com.pkm.quizzz.exceptions.InvalidParametersException;
import com.pkm.quizzz.exceptions.InvalidTokenException;
import com.pkm.quizzz.exceptions.ModelVerificationException;
import com.pkm.quizzz.exceptions.ResourceUnavailableException;
import com.pkm.quizzz.exceptions.UnauthorizedActionException;
import com.pkm.quizzz.exceptions.UserAlreadyExistsException;

@ControllerAdvice("com.pkm.quizzz.controller.web")
public class WebExceptionHandler {

	//контроллер отлова различных ошибок

	@ExceptionHandler(UnauthorizedActionException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView unauthorizedAction(HttpServletRequest req, Exception ex) {
		return setModelAndView(req.getRequestURL().toString(), ex, HttpStatus.UNAUTHORIZED.value());
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ModelAndView userExists(HttpServletRequest req, Exception ex) {
		return setModelAndView(req.getRequestURL().toString(), ex, HttpStatus.CONFLICT.value());
	}

	@ExceptionHandler(ResourceUnavailableException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView resourceUnavailable(HttpServletRequest req, Exception ex) {
		return setModelAndView(req.getRequestURL().toString(), ex, HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(ModelVerificationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView modelVerificationError(HttpServletRequest req, Exception ex) {
		return setModelAndView(req.getRequestURL().toString(), ex, HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView invalidTokenError(HttpServletRequest req, Exception ex) {
		return setModelAndView(req.getRequestURL().toString(), ex, HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(InvalidParametersException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView invalidParametersError(HttpServletRequest req, Exception ex) {
		return setModelAndView(req.getRequestURL().toString(), ex, HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(ActionRefusedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ModelAndView actionRefusedError(HttpServletRequest req, Exception ex) {
		return setModelAndView(req.getRequestURL().toString(), ex, HttpStatus.FORBIDDEN.value());
	}

	private ModelAndView setModelAndView(String url, Exception ex, Integer httpCode) {
		ErrorInfo errorInfo = new ErrorInfo(url, ex, httpCode);
		ModelAndView mav = new ModelAndView();

		mav.addObject("errorInfo", errorInfo);
		mav.setViewName("errorPage");
		return mav;
	}
}
