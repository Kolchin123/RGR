package com.pkm.quizzz.service;

import java.util.List;

import com.pkm.quizzz.exceptions.ResourceUnavailableException;
import com.pkm.quizzz.exceptions.UnauthorizedActionException;
import com.pkm.quizzz.model.Answer;
import com.pkm.quizzz.model.Question;
import com.pkm.quizzz.model.Quiz;

public interface QuestionService {
	//сервис вопросов
	Question save(Question question) throws UnauthorizedActionException;

	Question find(Long id) throws ResourceUnavailableException;

	List<Question> findQuestionsByQuiz(Quiz quiz);

	List<Question> findValidQuestionsByQuiz(Quiz quiz);

	Question update(Question question) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Question question) throws ResourceUnavailableException, UnauthorizedActionException;

	Boolean checkIsCorrectAnswer(Question question, Long answer_id);

	void setCorrectAnswer(Question question, Answer answer);

	Answer getCorrectAnswer(Question question);

	Answer addAnswerToQuestion(Answer answer, Question question);

	int countQuestionsInQuiz(Quiz quiz);

	int countValidQuestionsInQuiz(Quiz quiz);
}
