package com.pkm.quizzz.service.usermanagement.token;

import com.pkm.quizzz.model.TokenModel;
import com.pkm.quizzz.model.TokenType;
import com.pkm.quizzz.model.User;
import org.springframework.scheduling.annotation.Async;

public interface TokenDeliverySystem {
	@Async
	void sendTokenToUser(TokenModel token, User user, TokenType tokenType);
}
