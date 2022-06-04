package ru.otus.otushomework01.dao;


import ru.otus.otushomework01.domain.Question;

import java.util.Map;

public interface QuestionDao {

    Map<Integer, Question> getAllQuestion(String localization);
}