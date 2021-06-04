package com.pkm.quizzz.service.usermanagement;

import com.pkm.quizzz.model.User;

public interface UserManagementService {

	void resendPassword(User user);

	void verifyResetPasswordToken(User user, String token);

	void updatePassword(User user, String password);

}
