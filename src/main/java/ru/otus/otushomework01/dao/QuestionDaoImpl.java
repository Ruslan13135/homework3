package ru.otus.otushomework01.dao;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import ru.otus.otushomework01.config.LocalizationConfig;
import ru.otus.otushomework01.domain.Question;
import ru.otus.otushomework01.service.CsvReader;
import ru.otus.otushomework01.service.LocalizationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionDaoImpl implements QuestionDao, InitializingBean {

    private LocalizationConfig localizedQuestionResourceUrls = new LocalizationConfig();
    private CsvReader csvReader = new CsvReader();
    private Map<String, Map<Integer, Question>> localizedQuestionsMap = new HashMap<>();

    public QuestionDaoImpl(LocalizationConfig localizationConfig, LocalizationService localizationService) {
        this.localizedQuestionResourceUrls = localizationConfig;
    }

    @Override
    public Map<Integer, Question> getAllQuestion(String localization) {
        return localizedQuestionsMap.get(localization);
    }

    @Override
    public void afterPropertiesSet() {
        for (Map.Entry<String, String> questionResourceUrls : localizedQuestionResourceUrls.getResourceUrls().entrySet()) {
            Map<Integer, Question> localizedQuestions = new HashMap<>();
            List<Question> questions = csvReader.parseQuestions(questionResourceUrls.getValue());
            for (Question question : questions) {
                localizedQuestions.put(question.getId(), question);
            }
            localizedQuestionsMap.put(questionResourceUrls.getKey(), localizedQuestions);
        }
    }
}