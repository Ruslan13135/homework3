package ru.otus.otushomework01.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.otushomework01.config.LocalizationConfig;
import ru.otus.otushomework01.domain.Question;
import ru.otus.otushomework01.domain.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class InteractionServiceConsole implements InteractionService {
    private static final String NEXT_STEP = "\n.....................................\n";

    private final IOService ioService;
    private final LocalizationService localizationService;
    private final PollService pollService;
    private final String greeting;
    private final List<String> language;
    private final List<String> exit;
    private final Map<String, Locale> availableLocales;

    public InteractionServiceConsole(IOService ioService, LocalizationService localizationService,
                                     PollService pollService, LocalizationConfig properties) {
        this.ioService = ioService;
        this.localizationService = localizationService;
        this.pollService = pollService;
        greeting = properties.getGreeting();
        language = properties.getLanguage();
        availableLocales = properties.getAvailableLocales();
        exit = properties.getExit();
    }

    @Override
    public void startInteraction() {
        selectLanguage();
        descriptions();
        login();
        startQuiz();
        showResults();
    }

    boolean selectLanguage() {
        ioService.print(NEXT_STEP);
        ioService.print(greeting);
        ioService.printSurroundQuotes(language);
        ioService.printSurroundQuotes(exit);
        return !selectLanguage(ioService.getTyped()).isEmpty();
    }

    @Override
    public String selectLanguage(String lang) {

        if (availableLocales.containsKey(lang)) {
            localizationService.setCurrentLocale(availableLocales.get(lang));
            return availableLocales.get(lang).toString();
        } else if (isExit(lang)) {
            return "";
        }
        ioService.print(greeting);
        ioService.printSurroundQuotes(language);
        ioService.printSurroundQuotes(exit);
        return selectLanguage(ioService.getTyped());
    }

    @Override
    public void descriptions() {
        ioService.printBundledMessage("welcome");
        ioService.printBundledMessage("descriptions.quiz");
        ioService.printBundledMessage("descriptions.answer");
        ioService.printBundledMessage("descriptions.exit");
        ioService.printSurroundQuotes(exit);
    }

    boolean login() {
        ioService.print(NEXT_STEP);
        ioService.printBundledMessage("name");
        String name = ioService.getTyped();
        isExit(name);
        ioService.printBundledMessage("surname");
        String surname = ioService.getTyped();
        isExit(name);
        login(name, surname);
        return pollService.isUserEntered();
    }

    @Override
    public String login(String name, String surname) {
        pollService.setUser(new User(name, surname));
        ioService.print(String.format(localizationService.getBundledMessage("enter-to-system"),
            pollService.getUser().getName(), pollService.getUser().getSurname()));
        return "done";
    }

    @Override
    public void startQuiz() {
        ioService.printBundledMessage("start-poll");
        startQuizAgain(ioService.getTyped());
        List<Question> questions = new LinkedList<>(pollService.getQuestions().values());
        for (Question question : questions) {
            String answer = getAnswer(question);
            pollService.addNextComment(question, answer);
        }
    }

    @Override
    public void showResults() {
        ioService.print(NEXT_STEP);
        ioService.printBundledMessage("results-poll");
        for (String result : pollService.getPollResults()) {
            ioService.print(result);
        }
        startNewTest();
    }

    String getAnswer(Question question) {
        ioService.print(question.getQuestion());
        ioService.print(question.getFirstAnswer());
        ioService.print(question.getSecondAnswer());
        ioService.print(question.getThirdAnswer());
        return validateAnswer();
    }

    private String validateAnswer() {
        String answer = ioService.getTyped();
        if (exit.contains(answer)) {
            ioService.printBundledMessage("stop-poll");
            return null;
        } else if (!StringUtils.isNumeric(answer) && answer.length() < 3) {
            ioService.printBundledMessage("descriptions.answer");
            return validateAnswer();
        }
        return answer;
    }

    @Override
    public void startNewTest() {
        ioService.print(NEXT_STEP);
        ioService.print(NEXT_STEP);
        ioService.printBundledMessage("poll-again");
        ioService.printBundledMessage("descriptions.exit");
        ioService.printSurroundQuotes(exit);
        if (startQuizAgain(ioService.getTyped())) {
            pollService.restart();
            startInteraction();
        }
    }

    public boolean isExit(String command) {
        if (exit.contains(command)) {
            System.exit(0);
        }
        return false;
    }

    boolean startQuizAgain(String answer) {
        if (localizationService.getBundledMessage("say-yes").equals(answer)) {
            return true;
        } else if (isExit(answer)) {
            return false;
        }
        ioService.printBundledMessage("start-poll");
        ioService.printBundledMessage("descriptions.exit");
        ioService.printSurroundQuotes(exit);
        return startQuizAgain(ioService.getTyped());
    }
}