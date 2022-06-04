package ru.otus.otushomework01.service;


import ru.otus.otushomework01.domain.Question;
import ru.otus.otushomework01.domain.User;

import java.util.List;
import java.util.Map;

public interface PollService {

    User getUser();

    void setUser(User user);

    boolean isUserEntered();

    Map<Integer, Question> getQuestions();

    List<String> getPollResults();

    void restart();

    void addNextComment(Question question, String answer);
}