package com.pkm.quizzz.service.usermanagement;

import com.pkm.quizzz.model.User;

public interface UserManagementService {

	//управление пользователями

	void resendPassword(User user);

	void verifyResetPasswordToken(User user, String token);

	void updatePassword(User user, String password);

}
