package com.pkm.quizzz.service;

import java.util.List;

import com.pkm.quizzz.exceptions.ResourceUnavailableException;
import com.pkm.quizzz.exceptions.UnauthorizedActionException;
import com.pkm.quizzz.model.Quiz;
import com.pkm.quizzz.model.User;
import com.pkm.quizzz.model.support.Response;
import com.pkm.quizzz.model.support.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizService {
	Quiz save(Quiz quiz, User user);

	Page<Quiz> findAll(Pageable pageable);

	Page<Quiz> findAllPublished(Pageable pageable);

	Page<Quiz> findQuizzesByUser(User user, Pageable pageable);

	Quiz find(Long id) throws ResourceUnavailableException;

	Quiz update(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	Page<Quiz> search(String query, Pageable pageable);

	Result checkAnswers(Quiz quiz, List<Response> answersBundle);

	void publishQuiz(Quiz quiz);
}
