package com.pkm.quizzz.service;

import java.util.List;

import com.pkm.quizzz.exceptions.ActionRefusedException;
import com.pkm.quizzz.exceptions.InvalidParametersException;
import com.pkm.quizzz.exceptions.ResourceUnavailableException;
import com.pkm.quizzz.exceptions.UnauthorizedActionException;
import com.pkm.quizzz.model.Quiz;
import com.pkm.quizzz.model.User;
import com.pkm.quizzz.model.support.Response;
import com.pkm.quizzz.model.support.Result;
import com.pkm.quizzz.repository.QuizRepository;
import com.pkm.quizzz.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pkm.quizzz.model.Question;

@Service("QuizService")
@Transactional
public class QuizServiceImpl implements QuizService {

	private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);
	private QuizRepository quizRepository;

	private QuestionService questionService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MailService mailing;

	@Autowired
	public QuizServiceImpl(QuizRepository quizRepository, QuestionService questionService) {
		this.quizRepository = quizRepository;
		this.questionService = questionService;
	}

	@Override
	public Quiz save(Quiz quiz, User user) {
		quiz.setCreatedBy(user);
		quiz.setPlayed(0);
		if(quiz.getQuestions() != null)
			quiz.setQuestionCount(quiz.getQuestions().size());
		return quizRepository.save(quiz);
	}

	@Override
	public Page<Quiz> findAll(Pageable pageable) {
		return quizRepository.findAll(pageable);
	}

	@Override
	public Page<Quiz> findAllPublished(Pageable pageable) {
		return quizRepository.findByIsPublishedTrue(pageable);
	}

	@Override
	public Quiz find(Long id) throws ResourceUnavailableException {
		Quiz quiz = quizRepository.findOne(id);

		if (quiz == null) {
			logger.error("Quiz " + id + " not found");
			throw new ResourceUnavailableException("Quiz " + id + " not found");
		}

		return quiz;
	}

	@Override
	public Quiz update(Quiz newQuiz) throws UnauthorizedActionException, ResourceUnavailableException {
		Quiz currentQuiz = find(newQuiz.getId());

		if(currentQuiz.getQuestions() != null)
			currentQuiz.setQuestionCount(currentQuiz.getQuestions().size());
		mergeQuizzes(currentQuiz, newQuiz);
		return quizRepository.save(currentQuiz);
	}

	@Override
	public void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException {
		quizRepository.delete(quiz);
	}

	private void mergeQuizzes(Quiz currentQuiz, Quiz newQuiz) {
		currentQuiz.setName(newQuiz.getName());
		currentQuiz.setDescription(newQuiz.getDescription());
		if(currentQuiz.getQuestions() != null)
			currentQuiz.setQuestionCount(currentQuiz.getQuestions().size());
	}

	@Override
	public Page<Quiz> search(String query, Pageable pageable) {
		return quizRepository.searchByName(query, pageable);
	}

	@Override
	public Page<Quiz> findQuizzesByUser(User user, Pageable pageable) {
		return quizRepository.findByCreatedBy(user, pageable);
	}

	@Override
	public Result checkAnswers(Quiz quiz, List<Response> answersBundle) {
		Result results = new Result();

		for (Question question : quiz.getQuestions()) {
			boolean isFound = false;

			if (!question.getIsValid()) {
				continue;
			}

			for (Response bundle : answersBundle) {
				if (bundle.getQuestion().equals(question.getId())) {
					isFound = true;
					results.addAnswer(questionService.checkIsCorrectAnswer(question, bundle.getSelectedAnswer()));
					break;
				}
			}

			if (!isFound) {
				throw new InvalidParametersException("No answer found for question: " + question.getText());
			}
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User u = userRepository.findByUsername(username);

		mailing.sendTestResult(u,results,quiz);
		System.out.println("Отправляем письмо");
/*
		quiz.setPlayed(quiz.getPlayed()+1);
		System.out.println("Устанавливаем число пройденных");

		quizRepository.save(quiz);
		System.out.println("Сохраняем");*/
		return results;
	}

	@Override
	public void publishQuiz(Quiz quiz) {
		int count = questionService.countValidQuestionsInQuiz(quiz);

		if (count > 0) {
			quiz.setIsPublished(true);
			quizRepository.save(quiz);
		} else {
			throw new ActionRefusedException("The quiz doesn't have any valid questions");
		}
	}

}
