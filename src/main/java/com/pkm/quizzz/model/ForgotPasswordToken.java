package com.pkm.quizzz.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "forgot_password_tokens")
public class ForgotPasswordToken extends TokenModel {
 //формирование токена для забытого пароля
}
