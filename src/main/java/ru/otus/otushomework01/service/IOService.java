package ru.otus.otushomework01.service;

import ru.otus.otushomework01.domain.Question;

import java.util.List;

public interface IOService {

    String getTyped();

    void print(String message);

    void printQuestion(Question question);

    void printSurroundQuotes(List<String> source);

    void printBundledMessage(String key);
}
