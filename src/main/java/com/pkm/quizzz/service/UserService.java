package com.pkm.quizzz.service;

import com.pkm.quizzz.exceptions.ResourceUnavailableException;
import com.pkm.quizzz.exceptions.UnauthorizedActionException;
import com.pkm.quizzz.exceptions.UserAlreadyExistsException;
import com.pkm.quizzz.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	User saveUser(User user) throws UserAlreadyExistsException;

	User find(Long id) throws ResourceUnavailableException;;

	User findByEmail(String email) throws ResourceUnavailableException;

	User findByUsername(String username) throws ResourceUnavailableException;

	User updatePassword(User user, String password) throws ResourceUnavailableException;

	void delete(Long user_id) throws UnauthorizedActionException, ResourceUnavailableException;

	User setRegistrationCompleted(User user) throws ResourceUnavailableException;

	boolean isRegistrationCompleted(User user) throws ResourceUnavailableException;

}