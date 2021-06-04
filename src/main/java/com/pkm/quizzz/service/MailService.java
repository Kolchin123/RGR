package com.pkm.quizzz.service;

import com.pkm.quizzz.model.Quiz;
import com.pkm.quizzz.model.User;
import com.pkm.quizzz.model.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    public JavaMailSender emailSender;

    public void send(String mailTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }

    public void sendTestResult(User u, Result R, Quiz quiz) {
        send(u.getEmail(), "QuizZz: результаты теста "+quiz.getName(), "Здравствуйте! результаты теста " + R.getCorrectQuestions() + "/" + R.getTotalQuestions());
    }
}
