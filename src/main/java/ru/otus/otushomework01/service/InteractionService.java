package ru.otus.otushomework01.service;

public interface InteractionService {

    void startInteraction();

    String selectLanguage(String language);

    void descriptions();

    String login(String name, String surname);

    void startQuiz();

    void startNewTest();

    void showResults();

    boolean isExit(String command);
}
