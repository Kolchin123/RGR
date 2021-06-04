package com.pkm.quizzz.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "registration_tokens")
public class RegistrationToken extends TokenModel {
    //сущность токена для регистрации
}
