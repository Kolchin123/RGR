package com.pkm.quizzz.service.usermanagement;

import com.pkm.quizzz.model.User;

public interface RegistrationService {
	User startRegistration(User user);

	User continueRegistration(User user, String token);

	boolean isRegistrationCompleted(User user);
}
