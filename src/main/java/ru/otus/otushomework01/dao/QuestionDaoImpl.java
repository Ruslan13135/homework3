package ru.otus.otushomework01.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import ru.otus.otushomework01.config.LocalizationConfig;
import ru.otus.otushomework01.domain.Question;
import ru.otus.otushomework01.service.LocalizationService;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionDaoImpl implements QuestionDao, InitializingBean {

    private final Map<String, String> localizedQuestionResourceUrls;
    private Map<String, Map<Integer, Question>> localizedQuestionsMap;

    public QuestionDaoImpl(LocalizationConfig localizationConfig, LocalizationService localizationService) {
        this.localizedQuestionResourceUrls = localizationConfig.getResourceUrls();
    }

    @Override
    public Map<Integer, Question> getAllQuestion(String localization) {
        return localizedQuestionsMap.get(localization);
    }

    @Override
    public void afterPropertiesSet() {
        localizedQuestionsMap = new HashMap<>();
        for (Map.Entry<String, String> questionResourceUrls : localizedQuestionResourceUrls.entrySet()) {
            Map<Integer, Question> localizedQuestions = new HashMap<>();
            List<Question> questions = parseQuestions(questionResourceUrls.getValue());
            for (Question question : questions) {
                localizedQuestions.put(question.getId(), question);
            }
            localizedQuestionsMap.put(questionResourceUrls.getKey(), localizedQuestions);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Question> parseQuestions(String questionResourceUrl) {
        InputStream is = QuestionDao.class.getResourceAsStream(questionResourceUrl);

        CSVReader reader = new CSVReader(new InputStreamReader(is));
        HeaderColumnNameMappingStrategy<Question> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(Question.class);

        CsvToBean<Question> csvToBean = new CsvToBeanBuilder(reader)
            .withMappingStrategy(mappingStrategy)
            .withThrowExceptions(true)
            .build();
        try {
            return csvToBean.parse();
        } catch (RuntimeException e) {
            throw new RuntimeException("Недостаточно данных в файле с вопросами questions.csv", e);
        }
    }
}