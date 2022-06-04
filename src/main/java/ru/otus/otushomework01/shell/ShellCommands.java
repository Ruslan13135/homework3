package ru.otus.otushomework01.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.otushomework01.config.LocalizationConfig;
import ru.otus.otushomework01.domain.Question;
import ru.otus.otushomework01.service.IOService;
import ru.otus.otushomework01.service.InteractionService;
import ru.otus.otushomework01.service.LocalizationService;
import ru.otus.otushomework01.service.PollService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final InteractionService interactionService;
    private final LocalizationService localizationService;
    private final IOService ioService;
    private final PollService quizService;
    private final LocalizationConfig properties;

    @ShellMethod(value = "Select language command", key = {"language", "lang"})
    public String selectLanguage(String language) {
        if (properties.getAvailableLocales().containsKey(language)) {
            return interactionService.selectLanguage(language);
        } else {
            ioService.printBundledMessage("language-not-exists");
            ioService.print(properties.getGreeting());
            ioService.printSurroundQuotes(properties.getLanguage());
            return "";
        }
    }

    @ShellMethod(value = "Show current language command", key = {"current-language", "c-lang"})
    public String currentLanguage() {
        return String.format(localizationService.getBundledMessage("selected-language")
            , localizationService.getCurrentLocale().getLanguage());
    }

    @ShellMethod(value = "Instructions for test", key = {"instructions", "i"})
    public void instructions() {
        ioService.printBundledMessage("descriptions.select-language");
        ioService.printSurroundQuotes(properties.getLanguage());
        ioService.printBundledMessage("descriptions.or-use-default-lang");
        ioService.printBundledMessage("shell.use-commands");
        ioService.printBundledMessage("descriptions.exit");
        ioService.printBundledMessage("descriptions.exit.commands");
        ioService.printSurroundQuotes(properties.getExit());
    }

    @ShellMethod(value = "Authorization. You have to write name and surname", key = {"login", "l"})
    public void login(@ShellOption("--name") String name, @ShellOption("--surname") String surname) {
        if (name != null && surname != null) {
            interactionService.login(name, surname);
        }
    }

    @ShellMethod(value = "Show current entered user", key = {"current-login", "c-login", "c-l"})
    public String currentLogin() {
        return String.format(localizationService.getBundledMessage("enter-to-system"),
            quizService.getUser().getName(), quizService.getUser().getSurname());
    }

    @ShellMethod(value = "Start to answer on questions", key = {"start", "s"})
    public void startQuiz() {
        ioService.printBundledMessage("descriptions.quiz");
        ioService.printBundledMessage("descriptions.answer");
        ioService.printQuestion(quizService.getNextQuestion());
    }

    @ShellMethod(value = "Show current question", key = {"current-question", "current", "c"})
    public void currentQuestion() {
        ioService.printQuestion(quizService.getCurrentQuestion());
    }

    @ShellMethod(value = "Answer on question", key = {"answer", "a"})
    public void answer(String answer) {
        Question question = quizService.getCurrentQuestion();
        quizService.addNextComment(question, answer);
        if (quizService.isFinished()) {
            ioService.printBundledMessage("shell.unavailable.you-are-finished");
        } else {
            ioService.printQuestion(quizService.getNextQuestion());
        }
    }

    @ShellMethod(value = "Show ended results", key = {"results", "r"})
    public void showResults() {
        List<String> quizResults = quizService.getPollResults();
        ioService.printBundledMessage("results-poll");
        for (String result : quizResults) {
            ioService.print(result);
        }
    }

    private Availability loginAvailability() {
        if (quizService.isUserEntered()) {
            return Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.already-authorized"));
        }
        return Availability.available();
    }


    private Availability handShakeAvailability() {
        if (!quizService.isUserEntered()) {
            return Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.not-authorized"));
        }
        return Availability.available();
    }

    private Availability notStartedOrAlreadyFinished() {
        Availability availability = handShakeAvailability();
        if (quizService.isFinished()) {
            availability = Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.you-are-finished"));
        }
        return availability;
    }

    private Availability currentLoginAvailability() {
        return handShakeAvailability();
    }

    private Availability startQuizAvailability() {
        Availability availability = notStartedOrAlreadyFinished();
        if (quizService.isStarted()) {
            availability = Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.need-restart"));
        }
        return availability;
    }

    private Availability currentQuestionAvailability() {
        Availability availability = notStartedOrAlreadyFinished();
        if (!quizService.isStarted()) {
            String reason = availability.getReason();
            availability = Availability.unavailable((reason == null ? "" : reason)
                + localizationService.getBundledMessage("shell.unavailable.not-started"));
        }
        return availability;
    }

    private Availability answerAvailability() {
        return currentQuestionAvailability();
    }

    private Availability showResultsAvailability() {
        Availability inherit = currentLoginAvailability();
        if (!quizService.isFinished()) {
            String reason = inherit.getReason();
            return Availability.unavailable((reason == null ? "" : reason)
                + localizationService.getBundledMessage("shell.unavailable.not-quizzed"));
        }
        return inherit;
    }
}
