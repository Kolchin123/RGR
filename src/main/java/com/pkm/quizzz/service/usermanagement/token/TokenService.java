package com.pkm.quizzz.service.usermanagement.token;

import java.util.Date;

import com.pkm.quizzz.exceptions.InvalidTokenException;
import com.pkm.quizzz.model.TokenModel;
import com.pkm.quizzz.model.User;

public interface TokenService<T extends TokenModel> {
	T generateTokenForUser(User user);

	void validateTokenForUser(User user, String token) throws InvalidTokenException;

	void invalidateToken(String token);

	void invalidateExpiredTokensPreviousTo(Date date);
}
