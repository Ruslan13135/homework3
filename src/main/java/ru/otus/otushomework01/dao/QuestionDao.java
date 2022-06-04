package ru.otus.otushomework01.dao;


import ru.otus.otushomework01.domain.Question;

import java.util.Map;

public interface QuestionDao {

    int count();

    Question getById(int id, String localization);

    Map<Integer, Question> getAllQuestion(String localization);
}