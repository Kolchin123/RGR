package com.pkm.quizzz.service.accesscontrol;

import com.pkm.quizzz.exceptions.UnauthorizedActionException;
import com.pkm.quizzz.model.AuthenticatedUser;
import com.pkm.quizzz.model.Quiz;
import org.springframework.stereotype.Service;

@Service("AccessControlQuiz")
public class AccessControlServiceQuiz extends AccessControlServiceUserOwned<Quiz> {

	/*
	 * As long as the user is authenticated, it can create a Quiz.
	 */
	@Override
	public void canUserCreateObject(AuthenticatedUser user, Quiz object) throws UnauthorizedActionException {
		if (user == null) {
			throw new UnauthorizedActionException();
		}
	}

}
