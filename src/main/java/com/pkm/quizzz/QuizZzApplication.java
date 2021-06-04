package com.pkm.quizzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.pkm.quizzz")
public class QuizZzApplication {

	//поиск
	//админ доделать
	//самый популярный

	public static void main(String[] args) {
		SpringApplication.run(QuizZzApplication.class, args);
	}
}
