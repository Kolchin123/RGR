package com.pkm.quizzz.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pkm.quizzz.model.ForgotPasswordToken;

@Repository
public interface ForgotPasswordTokenRepository extends TokenRepository<ForgotPasswordToken> {
	@Modifying
	@Query("delete from ForgotPasswordToken t where t.expirationDate <= ?1")
	void deletePreviousTo(Date date);
}
