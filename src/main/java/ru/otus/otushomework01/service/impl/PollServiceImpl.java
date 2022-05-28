package ru.otus.otushomework01.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.otushomework01.domain.Question;
import ru.otus.otushomework01.domain.User;
import ru.otus.otushomework01.dao.QuestionDao;
import ru.otus.otushomework01.service.LocalizationService;
import ru.otus.otushomework01.service.PollService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    protected final LocalizationService localizationService;
    private final QuestionDao questionDao;
    private User user;
    private int questionSize = 0;
    private int currentQuestionIndex = 0;
    private Question currentQuestion;
    private List<String> quizResults = new LinkedList<>();

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean isUserEntered() {
        return user != null && !StringUtils.isEmpty(user.getName()) && !StringUtils.isEmpty(user.getSurname());
    }

    @Override
    public Map<Integer, Question> getQuestions() {
        return questionDao.getAllQuestion(localizationService.getCurrentLocale().getLanguage());
    }

    @Override
    public List<String> getPollResults() {
        return quizResults;
    }

    @Override
    public void restart() {
        this.user = new User();
        this.questionSize = 0;
        this.currentQuestionIndex = 0;
        this.currentQuestion = null;
        this.quizResults = new LinkedList<>();
    }

    @Override
    public void addNextComment(Question question, String answer) {
        boolean isRight = question.getId() == 5 || question.getRightAnswer().toLowerCase().contains(answer.toLowerCase());
        String givenAnswer;
        if (question.getFirstAnswer().contains(answer) || answer.contains("1")) {
            givenAnswer = question.getFirstAnswer();
        } else if (question.getSecondAnswer().contains(answer) || answer.contains("2")) {
            givenAnswer = question.getSecondAnswer();
        } else if (question.getThirdAnswer().contains(answer) || answer.contains("3")) {
            givenAnswer = question.getThirdAnswer();
        } else givenAnswer = answer;

        quizResults.add(question.getId() + ". " + question.getQuestion() + rightOrWrongComment(isRight) + givenAnswer
            + (question.getId() != 6 ? rightAnswer() : "\n\t") + question.getRightAnswer());
    }

    private String rightOrWrongComment(boolean isRight) {
        return isRight ? localizationService.getBundledMessage("correct-answer") : localizationService.getBundledMessage("not-correct-answer");
    }

    private String rightAnswer() {
        return localizationService.getBundledMessage("rightAnswer");
    }
}